package com.bankapp.bank.controller;

import com.bankapp.bank.infra.WebTestConfig;
import com.bankapp.bank.infra.WithMockUserContext;
import com.bankapp.bank.model.AccountInfoResponse;
import com.bankapp.bank.service.CustomerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = HomeController.class)
@Import(WebTestConfig.class)
public class HomeControllerTest {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUserContext
    public void testOverview() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/").contentType("application/json"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome username")));

    }


}
