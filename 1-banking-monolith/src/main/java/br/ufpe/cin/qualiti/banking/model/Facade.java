package br.ufpe.cin.qualiti.banking.model;

import br.ufpe.cin.qualiti.banking.model.account.Account;
import br.ufpe.cin.qualiti.banking.model.account.AccountController;
import br.ufpe.cin.qualiti.banking.model.account.AccountDTO;
import br.ufpe.cin.qualiti.banking.model.transaction.NotEnoughBalanceException;
import br.ufpe.cin.qualiti.banking.model.transaction.Transaction;
import br.ufpe.cin.qualiti.banking.model.transaction.TransactionController;
import br.ufpe.cin.qualiti.banking.model.transaction.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Facade {

    @Autowired private AccountController accountController;
    @Autowired private TransactionController transactionController;

    public Iterable<Transaction> getTransactionsFrom(Long accountId) {
        return transactionController.getAllFrom(accountId);
    }

    public void doTransaction(TransactionDTO transactionDTO) throws NotEnoughBalanceException {
        transactionController.doTransaction(transactionDTO);
    }

    public void createAccount(AccountDTO accountDTO) {
        accountController.create(accountDTO);
    }

    public Iterable<Account> getAccounts() {
        return accountController.getAll();
    }
}
