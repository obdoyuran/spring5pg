package com.trendyol.webFluxDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@RestController
public class Controller {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public Flux<BitbucketResponse> asyncCalls() {
        return Flux.range(0, 3)
                .flatMap(i -> {
                    log.info("Started {}", i);
                    return WebClient.create("https://bqlf8qjztdtr.statuspage.io/api/v2/status.json")
                            .get()
                            .retrieve()
                            .bodyToMono(BitbucketResponse.class)
                            .doOnNext(r -> log.info("Finished {}", i));
                });
    }

    @GetMapping("/sync")
    public List<BitbucketResponse> syncCalls() {
        return IntStream.range(0, 3)
                .boxed()
                .map(i -> {
                    log.info("Started {}", i);
                    BitbucketResponse response = restTemplate.getForObject("https://bqlf8qjztdtr.statuspage.io/api/v2/status.json", BitbucketResponse.class);
                    log.info("Finished {}", i);
                    return response;
                })
                .collect(Collectors.toList());
    }
}
