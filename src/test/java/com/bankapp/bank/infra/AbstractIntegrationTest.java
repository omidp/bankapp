package com.bankapp.bank.infra;

import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {"customer.country-validation=ADDRESS", "customer.countries=Netherlands, Germany, Belgium"})
public abstract class AbstractIntegrationTest {
}
