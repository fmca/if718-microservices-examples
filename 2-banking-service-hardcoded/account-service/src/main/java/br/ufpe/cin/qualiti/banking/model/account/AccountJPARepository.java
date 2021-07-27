package br.ufpe.cin.qualiti.banking.model.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountJPARepository implements IAccountRepository {

    @Autowired private AccountDAO accountDAO;

    @Override
    public void insert(Account account) {
        accountDAO.save(account);
    }

    @Override
    public void remove(Long id) {
        accountDAO.deleteById(id);
    }

    @Override
    public Iterable<Account> getAll() {
        return accountDAO.findAll();
    }

    @Override
    public Account get(Long id) {
        return accountDAO.findById(id).get();
    }

    @Override
    public void update(Account account) {
        accountDAO.save(account);
    }
}
