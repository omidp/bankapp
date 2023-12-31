package com.bankapp.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("")
    public String get(@AuthenticationPrincipal UserDetails userDetails) {
        return "Welcome " + userDetails.getUsername();
    }

}
