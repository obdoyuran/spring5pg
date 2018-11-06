package com.trendyol.webFluxDemo;

public class BitbucketResponse {

    public Status status;
    public Page page;

    public class Status {
        public String indicator;
        public String description;
    }

    public class Page {
        public String id;
        public String name;
        public String url;
        public String time_zone;
        public String updated_at;
    }
}
