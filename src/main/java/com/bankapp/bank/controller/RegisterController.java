package com.bankapp.bank.controller;

import com.bankapp.bank.model.RegisterRequest;
import com.bankapp.bank.model.RegisterResponse;
import com.bankapp.bank.service.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request){
        registerRequest.setIp(request.getRemoteAddr());
        return registerService.register(registerRequest);
    }

}
