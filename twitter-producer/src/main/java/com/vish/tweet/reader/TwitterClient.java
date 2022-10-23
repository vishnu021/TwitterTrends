package com.vish.tweet.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.hbc.core.Client;
import com.vish.tweet.reader.model.Tweet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
public class TwitterClient implements ApplicationRunner {
    private final Client client;
    private final Sink sink;

    private final BlockingQueue<String> msgQueue;

    @Override
    public void run(ApplicationArguments args) {
        client.connect();
        addShutDownHook(client);

        try {
            ObjectMapper mapper = getObjectMapper();
            log.info("Looping through tweets");
            while (!client.isDone()) {
                String msg = msgQueue.poll(5, TimeUnit.SECONDS);
                Tweet tweet = mapper.readValue(msg, Tweet.class);
                log.info(tweet.toString());
                sink.process(msg);
            }
        } catch (InterruptedException e) {
            log.error("Error while reading from twitter", e);
            client.stop();
        } catch (JsonProcessingException e) {
            log.error("Error while converting to object", e);
            throw new RuntimeException(e);
        }
        log.info("End of Application");
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    private void addShutDownHook(final Client client) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Stopping application");
            client.stop();
            sink.close();
        }));
    }
}
