package br.ufpe.cin.qualiti.banking.model.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionCollection {

    @Autowired private ITransactionRepository repository;

    public void insert(Transaction transaction) {
        repository.insert(transaction);
    }

    public void remove(Long id) {
        repository.remove(id);
    }

    public Iterable<Transaction> getAllFrom(Long accountId) {
        return repository.getAllFrom(accountId);
    }
}
