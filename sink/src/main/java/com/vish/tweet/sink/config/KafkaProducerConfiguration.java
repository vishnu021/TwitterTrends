package com.vish.tweet.sink.config;

import com.vish.tweet.model.Tweet;
import com.vish.tweet.sink.Sink;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Slf4j
@Configuration
public class KafkaProducerConfiguration {
    @Bean
    public Sink createKafkaSink(KafkaProducer<String, Tweet> producer, @Value("${kafka.topic}") String topic) {
        return new Sink(producer, topic);
    }

    @Bean
    public KafkaProducer<String, Tweet> createKafkaProducer(@Value("${kafka.bootstrap.server}") String bootstrapServers,
                                                            @Value("${kafka.schema.registry.url}") String schemaRegistryUrl) {
        Properties properties = createKafkaProducerProperties(bootstrapServers, schemaRegistryUrl);
        log.info("Creating kafka producer with properties : {}", properties);
        return new KafkaProducer<>(properties);
    }

    private Properties createKafkaProducerProperties(String bootstrapServers, String schemaRegistryUrl) {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", schemaRegistryUrl);

        // Additional Properties
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "5");
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        // Increasing Producer throughput
        // Compression
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        // Batching
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "500");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024)); //32 KB

        return properties;
    }
}
