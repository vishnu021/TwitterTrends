package com.vish.tweet.reader.trends;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vish.tweet.reader.trends.model.Tweet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
public class KafkaSink {
    private final KafkaProducer<String, String> kafkaProducer;
    private final BlockingQueue<String> msgQueue;
    private final String topic;

    public void process() throws InterruptedException {
        ObjectMapper mapper = getObjectMapper();
        String msg = msgQueue.poll(5, TimeUnit.SECONDS);
        if(msg!=null){
            try {
                Tweet tweet = mapper.readValue(msg, Tweet.class);
                log.info(tweet.toString());
                kafkaProducer.send(new ProducerRecord<>(topic, null, msg), (metadata, e) -> {
                    if(e!=null){
                        log.error("Publish to kafka failed", e);
                    }
                });
            } catch (JsonProcessingException e) {
                log.error("Error while converting to object", e);
            }
        }
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
