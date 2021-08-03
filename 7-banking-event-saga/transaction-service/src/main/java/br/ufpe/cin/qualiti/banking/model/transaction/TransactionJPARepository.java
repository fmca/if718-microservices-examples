package br.ufpe.cin.qualiti.banking.model.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionJPARepository implements ITransactionRepository {

    @Autowired private TransactionDAO transactionDAO;

    @Override
    public Transaction insert(Transaction transfer) {
        return transactionDAO.save(transfer);
    }

    @Override
    public Transaction update(Transaction transfer) {
        return transactionDAO.save(transfer);
    }

    @Override
    public void remove(Long id) {
        transactionDAO.deleteById(id);
    }

    @Override
    public Iterable<Transaction> getAll() {
        return transactionDAO.findAll();
    }

    @Override
    public Iterable<Transaction> getAllFrom(Long accountId) {
        return transactionDAO.findAllByAccountId(accountId);
    }
}
