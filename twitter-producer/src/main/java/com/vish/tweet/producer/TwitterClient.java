package com.vish.tweet.producer;

import com.twitter.hbc.core.Client;
import com.vish.tweet.sink.Sink;
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
            log.info("Looping through tweets");
            while (!client.isDone()) {
                String msg = msgQueue.poll(5, TimeUnit.SECONDS);
                sink.process(msg);
            }
        } catch (InterruptedException e) {
            log.error("Error while reading from twitter", e);
            client.stop();
        }
        log.info("End of Application");
    }

    private void addShutDownHook(final Client client) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Stopping application");
            client.stop();
            sink.close();
        }));
    }
}
