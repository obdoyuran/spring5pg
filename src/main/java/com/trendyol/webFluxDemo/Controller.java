package com.trendyol.webFluxDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@RestController
public class Controller {

    @GetMapping
    public Flux<BitbucketResponse> asyncCalls() {
        return Flux.range(0, 3)
                .flatMap(i -> {
                    log.info("Started {}", i);
                    return WebClient.create("https://bqlf8qjztdtr.statuspage.io/api/v2/status.json")
                            .get()
                            .retrieve()
                            .bodyToMono(BitbucketResponse.class)
                            .delayElement(Duration.ofSeconds(3))
                            .log()
                            .doOnSuccess(r -> log.info("Finished {}", i));
                });
    }
}
