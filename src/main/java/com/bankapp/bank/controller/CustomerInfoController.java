package com.bankapp.bank.controller;

import com.bankapp.bank.model.AccountInfoResponse;
import com.bankapp.bank.model.SecurityUserContext;
import com.bankapp.bank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CustomerInfoController {

    private final CustomerService customerService;

    @GetMapping("/overview")
    public ResponseEntity<List<AccountInfoResponse>> overview(@AuthenticationPrincipal UserDetails userDetails){
        if(userDetails instanceof SecurityUserContext userContext){
            return ResponseEntity.ok(customerService.getCustomerAccountInfo(userContext.getId()));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
