package com.bankapp.bank.config;

import com.bankapp.bank.dao.AddressDao;
import com.bankapp.bank.dao.CountryDao;
import com.bankapp.bank.dao.CustomerDao;
import com.bankapp.bank.domain.CountryEntity;
import com.bankapp.bank.domain.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EntityScan(basePackageClasses = {CustomerEntity.class})
@EnableJpaRepositories(basePackageClasses = {CustomerDao.class})
@Slf4j
public class JpaConfig {

    @Bean
    JdbcTemplate jdbcTemplate(DataSource datasource) {
        return new JdbcTemplate(datasource);
    }

    @Bean
    public CommandLineRunner runner(CountryDao countryDao, CustomerConfiguration customerConfiguration, AddressDao addressDao) {
        return args -> {
            if (countryDao.findAll(Pageable.ofSize(1)).getContent().isEmpty()) {
                log.info("populating country table");
                customerConfiguration.getCountries().forEach(country -> {
                    CountryEntity ce = new CountryEntity(country);
                    countryDao.save(ce);
                });
            }
        };
    }

}
