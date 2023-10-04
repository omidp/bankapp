package com.bankapp.bank.controller;

import com.bankapp.bank.dao.*;
import com.bankapp.bank.domain.AccountEntity;
import com.bankapp.bank.infra.PostgreSQLProperties;
import com.bankapp.bank.infra.TestConfigContext;
import com.bankapp.bank.model.AccountRequest;
import com.bankapp.bank.model.AddressRequest;
import com.bankapp.bank.model.RegisterRequest;
import com.bankapp.bank.model.RegisterResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class RegisterControllerIntegrationTest {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Container
    static PostgreSQLContainer<?> pgsql = new PostgreSQLContainer<>(PostgreSQLProperties.getInstance().getDefaultDockerImage());

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    AddressDao addressDao;

    @Autowired
    CountryDao countryDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AccountDao accountDao;

    @AfterEach
    void tearDown() {
        accountDao.deleteAll();
        addressDao.deleteAll();
        customerDao.deleteAll();
        userDao.deleteAll();
        countryDao.deleteAll();
    }

    @DynamicPropertySource
    static void pgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", pgsql::getJdbcUrl);
        registry.add("spring.datasource.username", pgsql::getUsername);
        registry.add("spring.datasource.password", pgsql::getPassword);
    }

    @Test
    void testRegister() throws Exception {
        var username = RandomStringUtils.randomAlphanumeric(12);
        var passwd = Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));
        var body = RegisterRequest.builder()
                .username(username)
                .name(RandomStringUtils.randomAlphanumeric(20))
                .dob("1987-01-02")
                .address(AddressRequest.builder()
                        .country("Netherlands")
                        .postcode("1050 KL").build())
                .account(AccountRequest.builder()
                        .accountType(AccountEntity.AccountType.DEBIT)
                        .currency("EURO").build()).build();
        restTemplate.headForHeaders("http://localhost:%s/register".formatted(port)).add("Content-Type", "application/json");
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity httpEntity = new HttpEntity(MAPPER.writeValueAsString(body), headers);
        assertThat(restTemplate.exchange("http://localhost:%s/register".formatted(port), HttpMethod.POST, httpEntity, String.class).getBody())
                .isEqualTo(MAPPER.writeValueAsString(RegisterResponse.builder().username(username).passwd(passwd).build()));
    }

}
