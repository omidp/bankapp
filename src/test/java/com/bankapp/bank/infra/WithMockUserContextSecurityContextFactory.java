package com.bankapp.bank.infra;

import com.bankapp.bank.model.SecurityUserContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public final class WithMockUserContextSecurityContextFactory implements WithSecurityContextFactory<WithMockUserContext> {

    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    @Override
    public SecurityContext createSecurityContext(WithMockUserContext annotation) {
        SecurityUserContext principal = new SecurityUserContext(1L, "username", "pass", true, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(principal,
                principal.getPassword(), principal.getAuthorities());
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}