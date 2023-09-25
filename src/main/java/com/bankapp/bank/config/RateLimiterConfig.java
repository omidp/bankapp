package com.bankapp.bank.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfig {

    @Bean
    public RateLimiter rateLimiter(){
        //2 request per second
        io.github.resilience4j.ratelimiter.RateLimiterConfig config = io.github.resilience4j.ratelimiter.RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(0))
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(2)
                .build();
        RateLimiter rateLimiter = RateLimiter.of("backendName", config);
        return rateLimiter;
    }
}
