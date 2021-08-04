package br.ufpe.cin.qualiti.banking.communication;

import br.ufpe.cin.qualiti.banking.model.transaction.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionCreatedEvent implements Event {

    public TransactionCreatedEvent() {}

    private TransactionDTO transactionDTO;
}
