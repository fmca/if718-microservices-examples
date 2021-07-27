package br.ufpe.cin.qualiti.banking.model.transaction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TransactionDAO extends CrudRepository<Transaction, Long> {

    @Query("from Transaction t where t.from.id=:accountId or t.to.id=:accountId")
    Iterable<Transaction> findAllByAccountId(Long accountId);
}
