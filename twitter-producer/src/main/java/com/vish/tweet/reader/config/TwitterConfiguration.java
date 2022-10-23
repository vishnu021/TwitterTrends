package com.vish.tweet.reader.config;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.vish.tweet.reader.Sink;
import com.vish.tweet.reader.TwitterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableConfigurationProperties(TwitterTokenProperties.class)
public class TwitterConfiguration {

    @Autowired
    private TwitterTokenProperties twitterProperties;

    @Bean
    public Hosts hoseBirdHosts() {
        return new HttpHosts(Constants.STREAM_HOST);
    }

    @Bean
    public StatusesFilterEndpoint hoseBirdEndpoint(@Value("${twitter.search-terms}") String searchTerms) {
        StatusesFilterEndpoint hoseBirdEndpoint =  new StatusesFilterEndpoint();

        List<String> terms = Arrays.stream(searchTerms.split(",")).collect(Collectors.toList());
        if(terms.size()==0) {
            log.error("Please provide at least 1 term");
            throw new RuntimeException("No search terms provided");
        }

        log.info("Creating filter with following terms : {}", searchTerms);
        return hoseBirdEndpoint.trackTerms(terms);
    }

    @Bean
    public BlockingQueue<String> blockingQueue(@Value("${twitter.queue-size}") int queueSize) {
        return new LinkedBlockingQueue<>(queueSize);
    }

    @Bean
    public Client createTwitterClient(BlockingQueue<String> msgQueue,
                                      Hosts hoseBirdHosts,
                                      StatusesFilterEndpoint hoseBirdEndpoint) {
        return createClientBuilder(msgQueue, hoseBirdHosts, hoseBirdEndpoint).build();
    }

    private ClientBuilder createClientBuilder(BlockingQueue<String> msgQueue, Hosts hoseBirdHosts, StatusesFilterEndpoint hoseBirdEndpoint) {
        return new ClientBuilder()
                .name("Client-01")
                .hosts(hoseBirdHosts)
                .authentication(getAuth())
                .endpoint(hoseBirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));
    }

    private Authentication getAuth() {
        return new OAuth1(twitterProperties.getConsumerKey(), twitterProperties.getConsumerSecret(),
                twitterProperties.getToken(), twitterProperties.getSecret());
    }

    @Bean
    public TwitterClient twitterProducer(Client twitterClient, Sink processor, BlockingQueue<String> blockingQueue) {
        return new TwitterClient(twitterClient, processor, blockingQueue);
    }
}
