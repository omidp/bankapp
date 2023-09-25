package com.bankapp.bank.controller;

import com.bankapp.bank.exception.NotAuthorizedByAgeException;
import com.bankapp.bank.exception.NotAuthorizedCountryException;
import com.bankapp.bank.exception.UserAlreadyExistsException;
import com.bankapp.bank.infra.WebTestConfig;
import com.bankapp.bank.model.ErrorMessage;
import com.bankapp.bank.model.RegisterResponse;
import com.bankapp.bank.service.RegisterService;
import com.bankapp.bank.web.RateLimiterServlet;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RegisterController.class)
@Import(WebTestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegisterControllerTest {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterService service;

    @Test
    public void testRegisterResponse() throws Exception {
        var registerRsp = RegisterResponse.builder().username("hi").passwd("123").build();
        when(service.register(any())).thenReturn(registerRsp);
        this.mockMvc.perform(post("/register").contentType("application/json").content("{}")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(MAPPER.writeValueAsString(registerRsp))));
    }

    @Test
    public void testRegisterUserAlreadyExistsException() throws Exception {
        when(service.register(any())).thenThrow(new UserAlreadyExistsException("user exists"));
        this.mockMvc.perform(post("/register").contentType("application/json").content("{}")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MAPPER.writeValueAsString(ErrorMessage.builder().message("user exists").status(400).build()))));
    }

    @Test
    public void testRegisterNotAuthorizedByAgeException() throws Exception {
        when(service.register(any())).thenThrow(new NotAuthorizedByAgeException("age not permitted"));
        this.mockMvc.perform(post("/register").contentType("application/json").content("{}")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MAPPER.writeValueAsString(ErrorMessage.builder().message("age not permitted").status(400).build()))));
    }

    @Test
    public void testRegisterNotAuthorizedCountryException() throws Exception {
        when(service.register(any())).thenThrow(new NotAuthorizedCountryException("country not allowed"));
        this.mockMvc.perform(post("/register").contentType("application/json").content("{}")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MAPPER.writeValueAsString(ErrorMessage.builder().message("country not allowed").status(400).build()))));
    }

    @Test
    public void testRegisterEmptyUsername() throws Exception {
        ConstraintViolationException exception = mock();
        ConstraintViolation violation = mock();
        when(violation.getMessage()).thenReturn("must not be empty");
        when(violation.getPropertyPath()).thenReturn(new Path() {
            @NotNull
            @Override
            public Iterator<Node> iterator() {
                return new ArrayList<Node>().iterator();
            }

            @Override
            public String toString() {
                return "username";
            }
        });
        when(exception.getConstraintViolations()).thenReturn(Set.of(violation));
        when(service.register(any())).thenThrow(exception);
        this.mockMvc.perform(post("/register").contentType("application/json").content("{}")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MAPPER.writeValueAsString(ErrorMessage.builder().message("username must not be empty").status(400).build()))));
    }

}
