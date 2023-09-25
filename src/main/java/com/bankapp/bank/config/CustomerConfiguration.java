package com.bankapp.bank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@ConfigurationProperties(prefix = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerConfiguration
{

    private Set<String> excludeRateLimitUrls;

    private Set<String> countries;

    private CountryValidation countryValidation;

    public enum CountryValidation{
        GEOIP, ADDRESS, NONE;
    }

}
