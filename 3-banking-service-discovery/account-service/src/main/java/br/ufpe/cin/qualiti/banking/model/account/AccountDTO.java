package br.ufpe.cin.qualiti.banking.model.account;

import lombok.Value;

@Value
public class AccountDTO {
    private String name;
    private Long balance;

    public Account toEntity() {
        return Account.builder().name(name).balance(balance).build();
    }
}
