package com.vish.tweet.reader.trends.config;

import com.vish.tweet.reader.trends.KafkaSink;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Configuration
public class KafkaConfiguration {
    @Bean
    public KafkaSink createKafkaSink(KafkaProducer<String, String> producer,
                                     BlockingQueue<String> blockingQueue,
                                     @Value("${kafka.topic}") String topic) {
        return new KafkaSink(producer, blockingQueue, topic);
    }

    @Bean
    public KafkaProducer<String, String> createKafkaProducer(@Value("${kafka.bootstrap.server}") String bootstrapServers) {
        Properties properties = createKafkaProducerProperties(bootstrapServers);
        log.info("Creating kafka producer with properties : {}", properties);
        return new KafkaProducer<>(properties);
    }

    private Properties createKafkaProducerProperties(String bootstrapServers) {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
