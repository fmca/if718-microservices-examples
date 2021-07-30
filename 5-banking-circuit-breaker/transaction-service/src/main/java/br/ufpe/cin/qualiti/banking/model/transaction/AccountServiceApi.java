package br.ufpe.cin.qualiti.banking.model.transaction;

import java.util.Map;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccountServiceApi implements IAccountService {

    private ReactiveCircuitBreaker circuitBreaker;
    private WebClient webClient;

    public AccountServiceApi(WebClient.Builder wBuilder, ReactiveCircuitBreakerFactory cbFactory) {
        this.webClient = wBuilder.baseUrl("lb://account").build();
        this.circuitBreaker = cbFactory.create("account");
    }

    @Override
    public boolean enoughBalance(Long accountId, Long valueToBeDecreased) {
        return (boolean)
                circuitBreaker
                        .run(
                                webClient
                                        .get()
                                        .uri(
                                                "/account/{id}/enoughBalance/{value}",
                                                accountId,
                                                valueToBeDecreased)
                                        .retrieve()
                                        .bodyToMono(Map.class))
                        .block()
                        .get("enoughBalance");
    }

    @Override
    public void updateBalance(Long accountId, Long valueToBeAppended) {
        circuitBreaker
                .run(
                        webClient
                                .put()
                                .uri("/account/{id}/balance", accountId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(valueToBeAppended))
                                .retrieve()
                                .bodyToMono(Void.class))
                .block();
    }
}
