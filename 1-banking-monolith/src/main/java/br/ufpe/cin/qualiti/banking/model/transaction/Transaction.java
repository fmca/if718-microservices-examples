package br.ufpe.cin.qualiti.banking.model.transaction;

import br.ufpe.cin.qualiti.banking.model.account.Account;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

    @Id @GeneratedValue private Long id;
    @ManyToOne private Account from;
    @ManyToOne private Account to;
    private Long value;
}
