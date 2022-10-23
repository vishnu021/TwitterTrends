package com.vish.tweet.reader;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Slf4j
@AllArgsConstructor
public class Sink {
    private final KafkaProducer<String, String> kafkaProducer;
    private final String topic;

    public void process(String msg) throws InterruptedException {
        if(msg!=null){
            kafkaProducer.send(new ProducerRecord<>(topic, null, msg), (metadata, e) -> {
                if(e!=null){
                    log.error("Publish to kafka failed", e);
                }
            });
        }
    }

    public void close() {
        kafkaProducer.close();
    }
}
