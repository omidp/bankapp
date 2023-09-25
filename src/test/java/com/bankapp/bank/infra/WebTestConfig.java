package com.bankapp.bank.infra;

import com.bankapp.bank.config.RateLimiterConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = RateLimiterConfig.class)
public class WebTestConfig {
}
