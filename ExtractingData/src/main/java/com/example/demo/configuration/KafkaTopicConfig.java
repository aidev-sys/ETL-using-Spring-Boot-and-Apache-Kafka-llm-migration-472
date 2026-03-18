package com.example.demo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.rabbitmq.host:localhost}")
    private String rabbitHost;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("source_topic");
    }

    @Bean
    public Queue sourceQueue() {
        return new Queue("source_topic");
    }

    @Bean
    public Binding binding(Queue sourceQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(sourceQueue).to(topicExchange).with("#");
    }
}