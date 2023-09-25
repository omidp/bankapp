package com.bankapp.bank.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "customer")
@Data
@ToString(of = {"id", "name", "identity"})
public class CustomerEntity extends AbstractEntity {

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "dob")
    @NotBlank
    private String dob;

    @Column(name = "identity")
    //ID document
    private String identity;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;


}
