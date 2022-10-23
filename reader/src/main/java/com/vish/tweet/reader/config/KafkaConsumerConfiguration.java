package com.vish.tweet.reader.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

@Slf4j
@Configuration
public class KafkaConsumerConfiguration {
    @Bean
    public KafkaConsumer<String, String> createConsumer(@Value("${kafka.bootstrap.server}") String bootstrapServers,
                                                        @Value("${kafka.topic}") String topic,
                                                        @Value("${kafka.consumer-group-id}") String consumerGroupId) {
        Properties properties = createKafkaConsumerProperties(bootstrapServers, consumerGroupId);
        log.info("Creating kafka producer with properties : {}", properties);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(topic));
        return consumer;
    }

    private Properties createKafkaConsumerProperties(String bootstrapServers, String groupId) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Additional Properties
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return properties;
    }
}
