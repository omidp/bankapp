package com.bankapp.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record RegisterResponse(@JsonProperty("username") String username, @JsonProperty("passwd") String passwd) {

}
