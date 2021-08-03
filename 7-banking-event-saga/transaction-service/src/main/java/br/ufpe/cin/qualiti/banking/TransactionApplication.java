package br.ufpe.cin.qualiti.banking;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class TransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("transactions").partitions(10).replicas(1).build();
    }
}
