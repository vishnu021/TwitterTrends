package com.vish.tweet.reader.trends;

import com.twitter.hbc.core.Client;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
@AllArgsConstructor
public class TwitterClient implements ApplicationRunner {
    private final Client client;
    private final KafkaSink kafkaSink;

    @Override
    public void run(ApplicationArguments args) {
        client.connect();
        addShutDownHook(client);

        try {
            log.info("Looping through tweets");
            while (!client.isDone()) {
                kafkaSink.process();
            }
        } catch (InterruptedException e) {
            log.error("Error while reading from twitter", e);
            client.stop();
        }
        log.info("End of Application");
    }

    private void addShutDownHook(final Client client) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            client.stop();
            log.info("Stopping twitter client");
        }));
    }
}
