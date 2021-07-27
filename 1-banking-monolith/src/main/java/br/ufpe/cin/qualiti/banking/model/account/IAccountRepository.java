package br.ufpe.cin.qualiti.banking.model.account;

public interface IAccountRepository {

    void insert(Account account);

    void remove(Long id);

    Iterable<Account> getAll();

    Account get(Long id);

    void update(Account account);
}
