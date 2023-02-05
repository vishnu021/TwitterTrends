package com.vish.tweet.reader;

import com.vish.tweet.model.Tweet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@AllArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final KafkaConsumer<String, Tweet> consumer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while(true) {
            ConsumerRecords<String, Tweet> consumerRecords =
                    consumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord<String, Tweet> record: consumerRecords) {
                log.info("Partition : {}, Offset : {}, Tweet : {}", record.partition(), record.offset(), record.value().toString());
                Thread.sleep(100);
            }
        }
    }
}
