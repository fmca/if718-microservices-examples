package br.ufpe.cin.qualiti.banking.model.transaction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TransactionDAO extends CrudRepository<Transaction, Long> {

    @Query("from Transaction t where t.fromAccountId=:accountId or t.toAccountId=:accountId")
    Iterable<Transaction> findAllByAccountId(Long accountId);
}
