package com.bankapp.bank.config;

import com.bankapp.bank.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import(LocationConfig.class)
@TestPropertySource(properties = "customer.country-validation=NONE")
@EnableConfigurationProperties(CustomerConfiguration.class)
public class LocationNoOpTest {

    @Autowired
    LocationService locationService;

    @Test

    void testLocationNoOp(){
        assertTrue(locationService.allowedLocation(""));
    }


}
