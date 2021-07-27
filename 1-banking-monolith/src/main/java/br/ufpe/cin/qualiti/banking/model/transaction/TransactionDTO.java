package br.ufpe.cin.qualiti.banking.model.transaction;

import br.ufpe.cin.qualiti.banking.model.account.AccountCollection;
import lombok.Data;

@Data
public class TransactionDTO {

    private Long fromAccountId;
    private Long toAccountId;
    private Long value;

    public Transaction toEntity(AccountCollection accountCollection) {
        return Transaction.builder()
                .from(accountCollection.get(fromAccountId))
                .to(accountCollection.get(toAccountId))
                .value(value)
                .build();
    }
}
