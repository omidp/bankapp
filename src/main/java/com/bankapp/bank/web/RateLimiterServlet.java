package com.bankapp.bank.web;

import com.bankapp.bank.config.CustomerConfiguration;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.vavr.control.Try;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class RateLimiterServlet implements Filter {

    private final RateLimiter rateLimiter;
    private final CustomerConfiguration customerConfiguration;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest request) {
            if(customerConfiguration.getExcludeRateLimitUrls().stream().anyMatch(f -> request.getRequestURI().startsWith(f))){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            try {
                Try.runRunnable(() -> RateLimiter.decorateConsumer(rateLimiter, 1, unused -> chain(servletRequest, servletResponse, filterChain)).accept(null)).get();
            } catch (RequestNotPermitted rnp) {
                log.info("2 requests per second is allowed");
                ((HttpServletResponse) servletResponse).setStatus(429);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @SneakyThrows
    private void chain(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
