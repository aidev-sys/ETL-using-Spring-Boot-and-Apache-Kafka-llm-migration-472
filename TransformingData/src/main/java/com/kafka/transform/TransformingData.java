package com.kafka.transform;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class TransformingData {

    private final RabbitTemplate rabbitTemplate;

    public TransformingData(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitConfig.INPUT_QUEUE_NAME)
    public void receiveAndTransform(String message) {
        // Simple transformation: convert payload to upper case
        String transformed = message.toUpperCase();
        rabbitTemplate.convertAndSend(RabbitConfig.OUTPUT_EXCHANGE_NAME,
                RabbitConfig.OUTPUT_ROUTING_KEY, transformed);
    }

    @PostConstruct
    public void init() {
        // optional initialization logic
    }
}

@Configuration
class RabbitConfig {

    static final String INPUT_QUEUE_NAME = "input.queue";
    static final String OUTPUT_QUEUE_NAME = "output.queue";
    static final String INPUT_EXCHANGE_NAME = "input.exchange";
    static final String OUTPUT_EXCHANGE_NAME = "output.exchange";
    static final String INPUT_ROUTING_KEY = "input.key";
    static final String OUTPUT_ROUTING_KEY = "output.key";

    @Bean
    public Queue inputQueue() {
        return new Queue(INPUT_QUEUE_NAME, true);
    }

    @Bean
    public Queue outputQueue() {
        return new Queue(OUTPUT_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange inputExchange() {
        return new DirectExchange(INPUT_EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange outputExchange() {
        return new DirectExchange(OUTPUT_EXCHANGE_NAME);
    }

    @Bean
    public Binding inputBinding(Queue inputQueue, DirectExchange inputExchange) {
        return BindingBuilder.bind(inputQueue).to(inputExchange).with(INPUT_ROUTING_KEY);
    }

    @Bean
    public Binding outputBinding(Queue outputQueue, DirectExchange outputExchange) {
        return BindingBuilder.bind(outputQueue).to(outputExchange).with(OUTPUT_ROUTING_KEY);
    }
}