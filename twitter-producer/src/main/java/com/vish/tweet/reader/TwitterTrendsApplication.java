package com.vish.tweet.reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.vish.tweet.reader"})
public class TwitterTrendsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterTrendsApplication.class, args);
    }
}
