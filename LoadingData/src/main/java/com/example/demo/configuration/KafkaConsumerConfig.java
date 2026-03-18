package com.example.demo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public TopicExchange demoExchange() {
        return new TopicExchange("demo.exchange");
    }

    @Bean
    public Queue demoQueue() {
        return new Queue("demo.queue", true);
    }

    @Bean
    public Binding demoBinding(Queue demoQueue, TopicExchange demoExchange) {
        return BindingBuilder.bind(demoQueue).to(demoExchange).with("demo.routingkey");
    }
}