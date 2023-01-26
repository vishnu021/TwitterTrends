package com.vish.tweet.producer.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("twitter-tokens")
public class TwitterTokenProperties {
    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String secret;
}
