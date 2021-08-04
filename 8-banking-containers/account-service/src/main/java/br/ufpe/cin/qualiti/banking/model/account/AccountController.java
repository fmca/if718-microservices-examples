package br.ufpe.cin.qualiti.banking.model.account;

import br.ufpe.cin.qualiti.banking.communication.Event;
import br.ufpe.cin.qualiti.banking.communication.TransactionApprovedEvent;
import br.ufpe.cin.qualiti.banking.communication.TransactionCanceledEvent;
import br.ufpe.cin.qualiti.banking.communication.TransactionCompletedEvent;
import br.ufpe.cin.qualiti.banking.communication.TransactionCreatedEvent;
import br.ufpe.cin.qualiti.banking.communication.TransactionSavedEvent;
import br.ufpe.cin.qualiti.banking.model.transaction.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@KafkaListener(id = "account", topics = "transactions")
public class AccountController {

    @Autowired private AccountCollection accountCollection;
    @Autowired private KafkaTemplate<String, Event> kafkaTemplate;
    @Autowired private NewTopic topic;

    public void create(AccountDTO accountDTO) {
        accountCollection.insert(accountDTO.toEntity());
    }

    public Iterable<Account> getAll() {
        return accountCollection.getAll();
    }

    public boolean enoughBalance(Long accountId, Long valueToBeDeducted) {
        return accountCollection.enoughBalance(accountId, valueToBeDeducted);
    }

    public void updateBalance(Long accountId, Long valueToBeAppended) {
        accountCollection.updateBalance(accountId, valueToBeAppended);
    }

    @KafkaHandler
    public void handle(TransactionCreatedEvent event) {
        TransactionDTO transactionDTO = event.getTransactionDTO();
        Event newEvent = null;
        log.info(
                "------------- ENOUGH: "
                        + enoughBalance(
                                transactionDTO.getFromAccountId(), transactionDTO.getValue()));
        if (enoughBalance(transactionDTO.getFromAccountId(), transactionDTO.getValue())) {
            newEvent = new TransactionApprovedEvent(transactionDTO);
        } else {
            newEvent = new TransactionCanceledEvent(transactionDTO);
        }
        kafkaTemplate.send(topic.name(), newEvent);
    }

    @KafkaHandler
    public void handle(TransactionSavedEvent event) {
        TransactionDTO transactionDTO = event.getTransactionDTO();
        updateBalance(transactionDTO.getFromAccountId(), -transactionDTO.getValue());
        updateBalance(transactionDTO.getToAccountId(), transactionDTO.getValue());
        kafkaTemplate.send(topic.name(), new TransactionCompletedEvent(transactionDTO));
    }
}
