package br.ufpe.cin.qualiti.banking.model.transaction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    public enum Status {
        PENDING,
        APPROVED,
        READY,
        CANCELED,
        COMPLETED
    }

    @Id @GeneratedValue private Long id;
    private Long fromAccountId;
    private Long toAccountId;
    private Long value;
    @Builder.Default private Status status = Status.PENDING;
}
