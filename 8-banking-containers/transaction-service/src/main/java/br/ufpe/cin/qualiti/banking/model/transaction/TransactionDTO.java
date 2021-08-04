package br.ufpe.cin.qualiti.banking.model.transaction;

import lombok.Data;

@Data
public class TransactionDTO {

    private Long fromAccountId;
    private Long toAccountId;
    private Long value;

    public Transaction toEntity() {
        return Transaction.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .value(value)
                .build();
    }
}
