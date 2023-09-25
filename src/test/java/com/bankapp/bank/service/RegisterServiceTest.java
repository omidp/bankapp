package com.bankapp.bank.service;


import com.bankapp.bank.config.CustomerConfiguration;
import com.bankapp.bank.dao.*;
import com.bankapp.bank.domain.UserEntity;
import com.bankapp.bank.exception.NotAuthorizedByAgeException;
import com.bankapp.bank.exception.NotAuthorizedCountryException;
import com.bankapp.bank.exception.UserAlreadyExistsException;
import com.bankapp.bank.model.AddressRequest;
import com.bankapp.bank.model.RegisterRequest;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @Mock
    private UserDao userDao;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private AccountDao accountDao;

    @Mock
    CustomerConfiguration customerConfiguration;

    @Mock
    LocationService locationService;

    @Mock
    IbanService ibanService;

    @Mock
    DateTimeService dateTimeService;

    @Mock
    CountryDao countryDao;

    @Mock
    AddressDao addressDao;

    @BeforeEach
    void setup() {
        registerService = new RegisterService(customerDao, userDao, accountDao, customerConfiguration, locationService, ibanService, dateTimeService, countryDao, addressDao);
    }

    @AfterEach
    void afterEach() {
        Mockito.reset(userDao, customerDao, accountDao, customerConfiguration, locationService, ibanService);
    }

    @Test
    public void testRegisterUserAlreadyExistsException() {
        when(userDao.findByUsername(any())).thenReturn(Optional.of(new UserEntity()));
        assertThrows(UserAlreadyExistsException.class, () -> registerService.register(RegisterRequest.builder().build()));
    }

    @Test
    public void testRegisterNotAuthorizedCountryException() {
        when(userDao.findByUsername(any())).thenReturn(Optional.empty());
        when(customerConfiguration.getCountryValidation()).thenReturn(CustomerConfiguration.CountryValidation.ADDRESS);
        when(locationService.allowedLocation(any())).thenReturn(false);
        assertThrows(NotAuthorizedCountryException.class, () -> registerService.register(RegisterRequest.builder()
                .address(AddressRequest.builder().build()).build()));
    }

    @Test
    public void testRegisterNotAuthorizedByAgeException() {
        when(userDao.findByUsername(any())).thenReturn(Optional.empty());
        when(customerConfiguration.getCountryValidation()).thenReturn(CustomerConfiguration.CountryValidation.GEOIP);
        when(locationService.allowedLocation(any())).thenReturn(true);
        when(dateTimeService.getLocalDateNow()).thenReturn(LocalDate.of(1990, 1,1));
        assertThrows(NotAuthorizedByAgeException.class, () -> registerService.register(RegisterRequest.builder() .address(AddressRequest.builder().build()).dob("1980-01-01").build()));
    }

}
