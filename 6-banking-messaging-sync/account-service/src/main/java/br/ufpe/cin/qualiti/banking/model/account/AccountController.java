package br.ufpe.cin.qualiti.banking.model.account;

import br.ufpe.cin.qualiti.banking.model.transaction.AccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = "qib-account")
public class AccountController {

    @Autowired private AccountCollection accountCollection;

    public void create(AccountDTO accountDTO) {
        accountCollection.insert(accountDTO.toEntity());
    }

    public Iterable<Account> getAll() {
        return accountCollection.getAll();
    }

    public boolean enoughBalance(Long accountId, Long valueToBeDeducted) {
        return accountCollection.enoughBalance(accountId, valueToBeDeducted);
    }

    @RabbitHandler
    public Boolean receive(AccountRequest request) {
        try {
            switch (request.getType()) {
                case ENOUGH:
                    return this.enoughBalance(request.getIdAccount(), request.getValue());
                default:
                    this.updateBalance(request.getIdAccount(), request.getValue());
                    return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void updateBalance(Long accountId, Long valueToBeAppended) {
        accountCollection.updateBalance(accountId, valueToBeAppended);
    }
}
