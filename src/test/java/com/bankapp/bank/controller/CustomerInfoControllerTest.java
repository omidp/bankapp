package com.bankapp.bank.controller;

import com.bankapp.bank.exception.NotAuthorizedByAgeException;
import com.bankapp.bank.exception.NotAuthorizedCountryException;
import com.bankapp.bank.exception.UserAlreadyExistsException;
import com.bankapp.bank.infra.WebTestConfig;
import com.bankapp.bank.infra.WithMockUserContext;
import com.bankapp.bank.model.AccountInfoResponse;
import com.bankapp.bank.model.ErrorMessage;
import com.bankapp.bank.model.RegisterResponse;
import com.bankapp.bank.service.CustomerService;
import com.bankapp.bank.service.RegisterService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CustomerInfoController.class)
@Import(WebTestConfig.class)
public class CustomerInfoControllerTest {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @Test
    @WithMockUser
    public void testOverviewForbiddenInvalidSecurityContext() throws Exception {
        var account = AccountInfoResponse.builder().accountNo(RandomStringUtils.randomNumeric(10)).build();
        when(service.getCustomerAccountInfo(any())).thenReturn(List.of(account));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/overview").contentType("application/json"))
                .andDo(print()).andExpect(status().isForbidden());

    }

    @Test
    @WithMockUserContext
    public void testOverview() throws Exception {
        var account = AccountInfoResponse.builder().accountNo(RandomStringUtils.randomNumeric(10)).build();
        when(service.getCustomerAccountInfo(any())).thenReturn(List.of(account));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/overview").contentType("application/json"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(MAPPER.writeValueAsString(account))));
    }


}
