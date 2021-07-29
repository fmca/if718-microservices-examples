package br.ufpe.cin.qualiti.banking.model.transaction;

public interface ITransactionRepository {

    void insert(Transaction transaction);

    void remove(Long id);

    Iterable<Transaction> getAll();

    Iterable<Transaction> getAllFrom(Long accountId);
}
