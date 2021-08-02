package br.ufpe.cin.qualiti.banking;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
@EnableRabbit
public class AccountApplication {

    @Bean
    Queue getQueue() {
        return new Queue("qib-account", false);
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
