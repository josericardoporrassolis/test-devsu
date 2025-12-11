package com.devsu.config;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("!test")
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @PostConstruct
    public void logKafkaConfig() {
        System.out.println("Kafka bootstrap servers = " + bootstrapServers);
    }

    @Bean
    public NewTopic movTopic() {
        return TopicBuilder.name("mov-topic-alert")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
