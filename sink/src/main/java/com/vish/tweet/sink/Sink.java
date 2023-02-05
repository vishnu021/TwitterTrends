package com.vish.tweet.sink;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vish.tweet.model.Tweet;
import com.vish.tweet.sink.mapper.TweetMapper;
import com.vish.tweet.sink.model.TweetModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Slf4j
@AllArgsConstructor
public class Sink {
    private final KafkaProducer<String, Tweet> kafkaProducer;
    private final String topic;

    public void process(String msg) throws InterruptedException {
        if(msg!=null){
            try {
                ObjectMapper mapper = getObjectMapper();
                mapper.configure(DeserializationFeature. FAIL_ON_UNKNOWN_PROPERTIES, false);
                TweetModel tweetModel = mapper.readValue(msg, TweetModel.class);

                log.info("msg : {}", tweetModel);

                ProducerRecord<String, Tweet> producerRecord
                        = new ProducerRecord<>(topic, TweetMapper.map(tweetModel));
                kafkaProducer.send(producerRecord, (metadata, ex) -> {
                    if(ex!=null){
                        log.error("Publish to kafka failed", ex);
                    }
                });

            } catch (JsonProcessingException e) {
                log.error("Unable to parse : {}", msg);
                throw new RuntimeException(e);
            }
        }
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public void close() {
        kafkaProducer.close();
    }
}
