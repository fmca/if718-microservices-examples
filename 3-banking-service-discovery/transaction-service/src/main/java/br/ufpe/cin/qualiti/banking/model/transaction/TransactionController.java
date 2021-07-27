package br.ufpe.cin.qualiti.banking.model.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionController {

    @Autowired private TransactionCollection transactionCollection;
    @Autowired private IAccountService accountService;

    public Iterable<Transaction> getAllFrom(Long accountId) {
        return transactionCollection.getAllFrom(accountId);
    }

    @Transactional
    public void doTransaction(TransactionDTO transactionDTO) throws NotEnoughBalanceException {
        Transaction transaction = transactionDTO.toEntity();
        boolean hasEnoughBalance =
                accountService.enoughBalance(
                        transaction.getFromAccountId(), transaction.getValue());
        if (!hasEnoughBalance) {
            throw new NotEnoughBalanceException();
        }
        transactionCollection.insert(transaction);
        accountService.updateBalance(transaction.getFromAccountId(), -transaction.getValue());
        accountService.updateBalance(transaction.getToAccountId(), transaction.getValue());
    }
}
