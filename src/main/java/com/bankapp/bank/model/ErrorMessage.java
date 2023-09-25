package com.bankapp.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ErrorMessage(@JsonProperty("status") int status,@JsonProperty("message") String message) {
}
