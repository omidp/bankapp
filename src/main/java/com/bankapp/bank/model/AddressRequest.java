package com.bankapp.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AddressRequest(@JsonProperty("postcode") String postcode,@JsonProperty("country")  String country, @JsonProperty("houseNo")  String houseNo) {
}
