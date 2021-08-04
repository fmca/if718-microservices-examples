package br.ufpe.cin.qualiti.banking.model.transaction;

import br.ufpe.cin.qualiti.banking.communication.Event;
import br.ufpe.cin.qualiti.banking.communication.TransactionApprovedEvent;
import br.ufpe.cin.qualiti.banking.communication.TransactionCanceledEvent;
import br.ufpe.cin.qualiti.banking.communication.TransactionCompletedEvent;
import br.ufpe.cin.qualiti.banking.communication.TransactionCreatedEvent;
import br.ufpe.cin.qualiti.banking.communication.TransactionSavedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@KafkaListener(id = "transaction", topics = "accounts")
public class TransactionController {

    @Autowired private TransactionCollection transactionCollection;
    @Autowired private KafkaTemplate<String, Event> kafkaTemplate;
    @Autowired private NewTopic topic;

    public Iterable<Transaction> getAllFrom(Long accountId) {
        return transactionCollection.getAllFrom(accountId);
    }

    @Transactional
    public Transaction doTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionCollection.insert(transactionDTO.toEntity());
        kafkaTemplate.send(topic.name(), new TransactionCreatedEvent(transactionDTO));
        return transaction;
    }

    @KafkaHandler
    public void handle(TransactionApprovedEvent event) {
        Transaction transaction = event.getTransactionDTO().toEntity();
        transaction.setStatus(Transaction.Status.READY);
        transactionCollection.update(transaction);
        kafkaTemplate.send(topic.name(), new TransactionSavedEvent(event.getTransactionDTO()));
    }

    @KafkaHandler
    public void handle(TransactionCompletedEvent event) {
        Transaction transaction = event.getTransactionDTO().toEntity();
        transaction.setStatus(Transaction.Status.COMPLETED);
        transactionCollection.update(transaction);
    }

    @KafkaHandler
    public void handle(TransactionCanceledEvent event) {
        Transaction transaction = event.getTransactionDTO().toEntity();
        transaction.setStatus(Transaction.Status.CANCELED);
        transactionCollection.update(transaction);
    }
}
