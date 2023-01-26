package com.vish.tweet.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.vish.tweet"})
public class TwitterTrendsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterTrendsApplication.class, args);
    }
}
