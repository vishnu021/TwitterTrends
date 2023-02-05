package com.vish.tweet.reader.config;

import com.vish.tweet.model.Tweet;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericData;
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
    public KafkaConsumer<String, Tweet> createConsumer(@Value("${kafka.bootstrap.server}") String bootstrapServers,
                                                             @Value("${kafka.topic}") String topic,
                                                             @Value("${kafka.consumer-group-id}") String consumerGroupId,
                                                             @Value("${kafka.schema.registry.url}") String schemaRegistryUrl) {
        Properties properties = createKafkaConsumerProperties(bootstrapServers, schemaRegistryUrl, consumerGroupId);
        log.info("Creating kafka producer with properties : {}", properties);

        KafkaConsumer<String, Tweet> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(topic));
        return consumer;
    }

    private Properties createKafkaConsumerProperties(String bootstrapServers, String schemaRegistryUrl, String groupId) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.setProperty("schema.registry.url", schemaRegistryUrl);

        // Additional Properties
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // needed for avro schema
        properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        return properties;
    }
}
