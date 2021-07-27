package br.ufpe.cin.qualiti.banking.model.transaction;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccountServiceApi implements IAccountService {

    @Autowired private DiscoveryClient discoveryClient;

    private WebClient client() {
        ServiceInstance sInstance = discoveryClient.getInstances("account").iterator().next();
        String accountServiceUrl =
                String.format(
                        "%s://%s:%s",
                        sInstance.getScheme(), sInstance.getHost(), sInstance.getPort());
        return WebClient.builder().baseUrl(accountServiceUrl).build();
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
