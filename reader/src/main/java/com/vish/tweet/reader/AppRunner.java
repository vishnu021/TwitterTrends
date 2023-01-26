package com.vish.tweet.reader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vish.tweet.sink.model.Tweet;
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

    private final KafkaConsumer<String, String> consumer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ObjectMapper mapper = getObjectMapper();
        while(true) {
            ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord<String, String> record: consumerRecords) {
                Tweet tweet = mapper.readValue(record.value(), Tweet.class);
                log.info("Partition : {}, Offset : {}, Tweet : {}", record.partition(), record.offset(), tweet);
                Thread.sleep(100);
            }
        }
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
