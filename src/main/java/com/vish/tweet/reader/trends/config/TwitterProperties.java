package com.vish.tweet.reader.trends.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("twitter-tokens")
public class TwitterProperties {
    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String secret;
}
