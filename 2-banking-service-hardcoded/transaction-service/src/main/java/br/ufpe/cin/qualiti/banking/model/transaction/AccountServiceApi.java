package br.ufpe.cin.qualiti.banking.model.transaction;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccountServiceApi implements IAccountService {

    @Value("${account_service_url}")
    private String accountServiceUrl;

    private WebClient client(){
        return WebClient.builder().baseUrl(accountServiceUrl).build();
    }

    @Override
    public boolean enoughBalance(Long accountId, Long valueToBeDecreased) {
        return (boolean) client()
                .get()
                .uri("/{id}/enoughBalance/{value}", accountId, valueToBeDecreased)
                .retrieve()
                .bodyToMono(Map.class)
                .block().get("enoughBalance");
    }

    @Override
    public void updateBalance(Long accountId, Long valueToBeAppended) {
        client()
                .put()
                .uri("/{id}/balance", accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(valueToBeAppended))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
