package br.ufpe.cin.qualiti.banking.model.transaction;

import br.ufpe.cin.qualiti.banking.model.account.AccountCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionController {

    @Autowired private AccountCollection accountCollection;
    @Autowired private TransactionCollection transactionCollection;

    public Iterable<Transaction> getAllFrom(Long accountId) {
        return transactionCollection.getAllFrom(accountId);
    }

    @Transactional
    public void doTransaction(TransactionDTO transactionDTO) throws NotEnoughBalanceException {
        Transaction transaction = transactionDTO.toEntity(accountCollection);
        boolean hasEnoughBalance =
                accountCollection.enoughBalance(
                        transaction.getFrom().getId(), transaction.getValue());
        if (!hasEnoughBalance) {
            throw new NotEnoughBalanceException();
        }
        transactionCollection.insert(transaction);
        accountCollection.updateBalance(transaction.getFrom().getId(), -transaction.getValue());
        accountCollection.updateBalance(transaction.getTo().getId(), transaction.getValue());
    }
}
