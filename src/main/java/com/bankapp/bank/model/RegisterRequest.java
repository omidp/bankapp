package com.bankapp.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(of = {"username"})
public class RegisterRequest {

    private String name;
    private String dob;
    private String username;
    private String ip;
    private AddressRequest address;
    private AccountRequest account;


}
