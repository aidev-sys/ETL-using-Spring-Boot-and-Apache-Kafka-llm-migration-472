package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@SpringBootApplication
@EnableRabbit
public class ExtractDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtractDataApplication.class, args);
    }
}