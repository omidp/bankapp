package com.bankapp.bank.model;

import com.bankapp.bank.domain.AccountEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(of = "accountNo")
public class AccountRequest {

    private AccountEntity.AccountType accountType;
    private String currency;

}
