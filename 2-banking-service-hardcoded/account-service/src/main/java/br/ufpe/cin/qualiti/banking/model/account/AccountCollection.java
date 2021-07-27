package br.ufpe.cin.qualiti.banking.model.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountCollection {

    @Autowired private IAccountRepository repository;

    public void insert(Account account) {
        repository.insert(account);
    }

    public void remove(Long id) {
        repository.remove(id);
    }

    public Iterable<Account> getAll() {
        return repository.getAll();
    }

    public Account get(Long id) {
        return repository.get(id);
    }

    public boolean enoughBalance(Long accountId, Long valueToBeDeducted) {
        return this.get(accountId).getBalance() >= valueToBeDeducted;
    }

    public void updateBalance(Long accountId, Long valueToBeAppended) {
        Account account = get(accountId);
        account.setBalance(account.getBalance() + valueToBeAppended);
        this.repository.update(account);
    }
}
