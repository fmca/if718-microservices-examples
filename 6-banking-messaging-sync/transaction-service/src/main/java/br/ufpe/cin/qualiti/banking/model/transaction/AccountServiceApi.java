package br.ufpe.cin.qualiti.banking.model.transaction;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceApi implements IAccountService {

    @Autowired RabbitTemplate rabbitTemplate;

    @Autowired Queue queue;

    @Override
    public boolean enoughBalance(Long accountId, Long valueToBeDecreased) {
        return (boolean)
                rabbitTemplate.convertSendAndReceive(
                        queue.getName(),
                        new AccountRequest(
                                AccountRequest.RequestType.ENOUGH, accountId, valueToBeDecreased));
    }

    @Override
    public void updateBalance(Long accountId, Long valueToBeAppended) {
        rabbitTemplate.convertSendAndReceive(
                queue.getName(),
                new AccountRequest(
                        AccountRequest.RequestType.UPDATE, accountId, valueToBeAppended));
    }
}
