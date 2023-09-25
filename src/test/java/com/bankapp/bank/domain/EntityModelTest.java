package com.bankapp.bank.domain;

import com.bankapp.bank.config.CustomerConfiguration;
import com.bankapp.bank.model.AccountRequest;
import com.bankapp.bank.model.AddressRequest;
import com.bankapp.bank.model.RegisterRequest;
import com.bankapp.bank.model.SecurityUserContext;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntityModelTest {

    @Test
    void testAddressEntity() {
        AddressEntity actual = new AddressEntity();
        actual.setPostCode("1034");
        CustomerEntity customer = new CustomerEntity();
        customer.setId(10L);
        customer.setIdentity("123456");
        actual.setCustomer(customer);
        actual.setLine("line1");
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(10L);
        actual.setCountry(countryEntity);
        actual.setHouseNo("110");
        //
        AddressEntity expected = new AddressEntity();
        expected.setPostCode("1034");
        CountryEntity country = new CountryEntity();
        countryEntity.setId(10L);
        expected.setCountry(country);
        expected.setHouseNo("110");
        expected.setLine(actual.getLine());
        expected.setCustomer(customer);
        assertEquals(expected, actual);
        assertTrue(actual.hashCode() == expected.hashCode());
        assertEquals(expected.toString(), "AddressEntity(postCode=1034, houseNo=110)");
    }

    @Test
    void testAccountEntity() {
        AccountEntity actual = new AccountEntity();
        actual.setAccountNo("123");
        CustomerEntity customer = new CustomerEntity();
        customer.setId(10L);
        customer.setIdentity("123456");
        actual.setCustomer(customer);
        //
        AccountEntity expected = new AccountEntity();
        expected.setAccountNo("123");
        expected.setCustomer(customer);
        assertEquals(expected, actual);
        assertTrue(actual.hashCode() == expected.hashCode());
        assertEquals(expected.toString(), "AccountEntity(iban=null, accountNo=123, accountType=null)");
    }

    @Test
    void testUserEntity() {
        UserEntity actual = new UserEntity("test", "pass", true);
        UserEntity expected = new UserEntity("test", "pass", true);
        assertEquals(expected, actual);
        assertTrue(actual.hashCode() == expected.hashCode());
        assertEquals(expected.toString(), "UserEntity(username=test, password=pass, enabled=true)");
    }

    @Test
    void testRegisterRequest() {
        var actual = RegisterRequest.builder().dob("1985-01-01").address(AddressRequest.builder().postcode("1023").build())
                .name("name456").username("ROOT").account(AccountRequest.builder().accountType(AccountEntity.AccountType.DEBIT).build()).build();
        var expected = RegisterRequest.builder().dob("2020-01-01").address(AddressRequest.builder().postcode("1045").build())
                .name("name123").username("ROOT").account(AccountRequest.builder().accountType(AccountEntity.AccountType.DEBIT).build()).build();
        assertEquals(expected, actual);
        assertTrue(actual.hashCode() == expected.hashCode());
        assertEquals(expected.toString(), "RegisterRequest(name=name123, dob=2020-01-01, username=ROOT, ip=null, address=AddressRequest[postcode=1045, country=null, houseNo=null], account=AccountRequest(accountType=DEBIT, currency=null))");
    }

    @Test
    void testCustomerConfiguration() {
        CustomerConfiguration actual = new CustomerConfiguration(emptySet(), Set.of("Iran"), CustomerConfiguration.CountryValidation.ADDRESS);
        CustomerConfiguration expected = new CustomerConfiguration(emptySet(), Set.of("Iran"), CustomerConfiguration.CountryValidation.ADDRESS);
        assertEquals(expected, actual);
        assertTrue(actual.hashCode() == expected.hashCode());
        assertEquals(expected.toString(), "CustomerConfiguration(excludeRateLimitUrls=[], countries=[Iran], countryValidation=ADDRESS)");
    }

    @Test
    void testCustomerEntity() {
        CustomerEntity expected = new CustomerEntity();
        expected.setIdentity("identity");
        expected.setId(10L);
        CustomerEntity actual = new CustomerEntity();
        actual.setIdentity("identity");
        actual.setId(10L);
        assertEquals(expected, actual);
        assertTrue(actual.hashCode() == expected.hashCode());
        assertEquals(expected.toString(), "CustomerEntity(name=null, identity=identity)");
    }

    @Test
    void testSecurityUserContext() {
        SecurityUserContext expected = new SecurityUserContext(10L, "test", "pass", false, emptySet());
        SecurityUserContext actual = new SecurityUserContext(10L, "test", "pass", false, emptySet());
        assertEquals(expected, actual);
        assertTrue(actual.hashCode() == expected.hashCode());
        assertEquals(expected.toString(), "SecurityUserContext(id=10, username=test, password=pass, enabled=false, roles=[])");
    }

}
