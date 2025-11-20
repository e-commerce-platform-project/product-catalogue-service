package ru.ivanov.ecommerceplatformproject.productservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final Environment environment;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getRequiredProperty("spring.kafka.bootstrap-servers"));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        config.put(ProducerConfig.ACKS_CONFIG, environment.getRequiredProperty("spring.kafka.producer.acks"));
//        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, environment.getRequiredProperty("spring.kafka.producer.delivery.timeout.ms"));
//        config.put(ProducerConfig.LINGER_MS_CONFIG, environment.getRequiredProperty("spring.kafka.producer.linger.ms"));
//        config.put(ProducerConfig.BATCH_SIZE_CONFIG, environment.getRequiredProperty("spring.kafka.producer.batch.size"));
//        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
//        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
//        config.put(ProducerConfig.RETRIES_CONFIG, environment.getRequiredProperty("spring.kafka.producer.retries"));
//        config.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, environment.getRequiredProperty("spring.kafka.producer.retry-backoff.ms"));
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getRequiredProperty("spring.kafka.bootstrap-servers"));
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//        config.put(JsonDeserializer.TRUSTED_PACKAGES, environment.getRequiredProperty("spring.kafka.consumer.properties.spring.json.trusted.packages"));
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getRequiredProperty("spring.kafka.consumer.group-id"));
//        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, environment.getRequiredProperty("spring.kafka.consumer.auto-offset-reset"));
//        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, environment.getRequiredProperty("spring.kafka.consumer.enable-auto-commit"));
//        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, environment.getRequiredProperty("spring.kafka.consumer.max-poll-records"));
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(kafkaTemplate),
                new FixedBackOff(1000L, 5)
        );

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }
}