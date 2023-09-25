package com.bankapp.bank.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "country")
@Data
@NoArgsConstructor
@AllArgsConstructor()
@ToString
public class CountryEntity extends AbstractEntity{

    @Column(name = "name", unique = true)
    private String name;

}
