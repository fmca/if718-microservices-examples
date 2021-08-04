package br.ufpe.cin.qualiti.banking;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@EnableEurekaClient
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("accounts").partitions(10).replicas(1).build();
    }
}
