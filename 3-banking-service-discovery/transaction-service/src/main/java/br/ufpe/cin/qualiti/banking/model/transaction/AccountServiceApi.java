package br.ufpe.cin.qualiti.banking.model.transaction;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccountServiceApi implements IAccountService {

    @Autowired WebClient.Builder wBuilder;

    private WebClient client() {
        return wBuilder.baseUrl("lb://account").build();
    }

    @Override
    public boolean enoughBalance(Long accountId, Long valueToBeDecreased) {
        return (boolean)
                client().get()
                        .uri("/account/{id}/enoughBalance/{value}", accountId, valueToBeDecreased)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block()
                        .get("enoughBalance");
    }

    @Override
    public void updateBalance(Long accountId, Long valueToBeAppended) {
        client().put()
                .uri("/account/{id}/balance", accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(valueToBeAppended))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
