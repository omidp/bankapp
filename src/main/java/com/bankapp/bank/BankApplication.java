package com.bankapp.bank;

import com.bankapp.bank.config.CustomerConfiguration;
import com.bankapp.bank.dao.CountryDao;
import com.bankapp.bank.domain.CountryEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({CustomerConfiguration.class})
public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }


}
