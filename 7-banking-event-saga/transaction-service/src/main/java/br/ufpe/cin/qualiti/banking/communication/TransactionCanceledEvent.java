package br.ufpe.cin.qualiti.banking.communication;

import br.ufpe.cin.qualiti.banking.model.transaction.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionCanceledEvent implements Event {

    public TransactionCanceledEvent() {}

    private TransactionDTO transactionDTO;
}
