package com.bankapp.bank.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "address")
@Data
@ToString(of = {"postCode", "houseNo"})
public class AddressEntity extends AbstractEntity {

    private String line;
    @NotBlank
    private String postCode;
    private String houseNo;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerEntity customer;

    @JoinColumn(name = "country_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CountryEntity country;


}
