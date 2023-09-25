package com.bankapp.bank.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(of = "accountNo")
public class AccountInfoResponse {

    private String accountNo;
    private String accountType;
    private BigDecimal balance;
    private String currency;
    private String iban;

}
