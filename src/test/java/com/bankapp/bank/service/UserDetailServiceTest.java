package com.bankapp.bank.service;


import com.bankapp.bank.dao.UserDao;
import com.bankapp.bank.domain.UserEntity;
import com.bankapp.bank.model.SecurityUserContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTest {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Mock
    private UserDao userDao;

    @BeforeEach
    void setup() {
        userDetailService = new UserDetailServiceImpl(userDao);
    }

    @AfterEach
    void afterEach() {
        Mockito.reset(userDao);
    }

    @Test
    public void testLoadUserByUsername() {
        UserEntity userEntity = new UserEntity("test", "pass", false);
        userEntity.setId(1L);
        when(userDao.findByUsername("test")).thenReturn(Optional.of(userEntity));
        assertEquals(new SecurityUserContext(1L, "test", "pass", false,
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))),
                userDetailService.loadUserByUsername("test"));
    }

    @Test
    public void testLoadUserByUsernameThrowsUsernameNotFoundException() {
        when(userDao.findByUsername("test")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername("test"));
    }
}
