package com.bankapp.bank.model;

import com.bankapp.bank.domain.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EntityMapper {

    private EntityMapper() {
    }


    @SneakyThrows
    public static CustomerEntity toCustomerEntity(RegisterRequest registerRequest, UserEntity userEntity) {
        CustomerEntity customer = new CustomerEntity();
        customer.setDob(registerRequest.getDob());
        customer.setUser(userEntity);
        customer.setName(registerRequest.getName());
        customer.setIdentity(RandomStringUtils.randomAlphanumeric(10));
        return customer;
    }

    public static UserEntity toUserEntity(RegisterRequest registerRequest) {
        String passwd = Base64.getEncoder().encodeToString(registerRequest.getUsername().getBytes(StandardCharsets.UTF_8));
        return new UserEntity(registerRequest.getUsername(), passwd, true);
    }

    public static AccountInfoResponse toAccountInfoResponse(AccountEntity account) {
        return AccountInfoResponse.builder()
                .accountNo(account.getAccountNo())
                .iban(account.getIban())
                .accountType(account.getAccountType().getLabel())
                .currency(account.getCurrency())
                .balance(account.getBalance()).build();
    }

    public static AccountEntity toAccountEntity(CustomerEntity customerEntity, AccountIbanInfo acctIbanInfo, AccountRequest accountRequest) {
        AccountEntity account = new AccountEntity();
        account.setCustomer(customerEntity);
        account.setAccountNo(acctIbanInfo.accountNo());
        account.setIban(acctIbanInfo.iban());
        account.setAccountType(accountRequest.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(accountRequest.getCurrency());
        return account;
    }

    public static AddressEntity toAddressEntity(AddressRequest addressRequest, CountryEntity country, CustomerEntity customer) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setHouseNo(addressRequest.houseNo());
        addressEntity.setPostCode(addressRequest.postcode());
        addressEntity.setCountry(country);
        addressEntity.setCustomer(customer);
        return addressEntity;
    }

}
