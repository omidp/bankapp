package com.bankapp.bank.service;

import com.bankapp.bank.config.CustomerConfiguration;
import com.bankapp.bank.dao.*;
import com.bankapp.bank.domain.CountryEntity;
import com.bankapp.bank.domain.CustomerEntity;
import com.bankapp.bank.domain.UserEntity;
import com.bankapp.bank.exception.NotAuthorizedByAgeException;
import com.bankapp.bank.exception.NotAuthorizedCountryException;
import com.bankapp.bank.exception.UserAlreadyExistsException;
import com.bankapp.bank.model.AccountIbanInfo;
import com.bankapp.bank.model.EntityMapper;
import com.bankapp.bank.model.RegisterRequest;
import com.bankapp.bank.model.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final CustomerDao customerDao;
    private final UserDao userDao;
    private final AccountDao accountDao;
    private final CustomerConfiguration customerConfiguration;
    private final LocationService locationService;
    private final IbanService ibanService;
    private final DateTimeService dateTimeService;
    private final CountryDao countryDao;
    private final AddressDao addressDao;


    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        if(userDao.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("User already exist");
        }
        if (!isCountryAllowed(registerRequest.getAddress().country(), registerRequest.getIp())){
            throw new NotAuthorizedCountryException("Country is not authorized");
        }
        if(!isOver18YearsOld(registerRequest.getDob())){
            throw new NotAuthorizedByAgeException("You must be above 18 years old");
        }
        CountryEntity countryEntity = countryDao.findByName(registerRequest.getAddress().country()).orElseThrow(() -> new IllegalArgumentException("country not found"));
        UserEntity userEntity = userDao.save(EntityMapper.toUserEntity(registerRequest));
        CustomerEntity customerEntity = customerDao.save(EntityMapper.toCustomerEntity(registerRequest, userEntity));
        AccountIbanInfo acctIbanInfo = ibanService.getIban(() -> RandomStringUtils.randomNumeric(10));
        accountDao.save(EntityMapper.toAccountEntity(customerEntity, acctIbanInfo, registerRequest.getAccount()));
        addressDao.save(EntityMapper.toAddressEntity(registerRequest.getAddress(), countryEntity, customerEntity));
        return new RegisterResponse(userEntity.getUsername(), userEntity.getPassword());
    }

    private boolean isOver18YearsOld(String dob) {
        return Period.between(LocalDate.parse(dob), dateTimeService.getLocalDateNow()).getYears() >= 18;
    }

    private boolean isCountryAllowed(String country, String ip){
        return switch (customerConfiguration.getCountryValidation()){
            case ADDRESS -> locationService.allowedLocation(country);
            case GEOIP -> locationService.allowedLocation(ip);
            case NONE -> true;
        };
    }

}
