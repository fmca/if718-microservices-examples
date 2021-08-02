package br.ufpe.cin.qualiti.banking.model.transaction;

import java.io.Serializable;

import lombok.Value;

@Value
public class AccountRequest implements Serializable {

    public static enum RequestType {
        ENOUGH,
        UPDATE
    }

    private RequestType type;
    private Long idAccount;
    private Long value;
}
