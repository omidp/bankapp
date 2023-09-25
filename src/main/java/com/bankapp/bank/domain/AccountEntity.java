package com.bankapp.bank.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Table(name = "account")
@Entity
@Data
@ToString(of = {"iban", "accountNo", "accountType"})
public class AccountEntity extends PO {

    @Column(name = "iban", unique = true)
    @NotBlank
    private String iban;

    @Column(name = "account_no", unique = true)
    @NotBlank
    private String accountNo;

    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerEntity customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "currency")
    private String currency;

    public enum AccountType {
        DEBIT("Debit"), CREDIT("Credit");
        @Getter
        private String label;

        AccountType(String label) {
            this.label = label;
        }
    }

}
