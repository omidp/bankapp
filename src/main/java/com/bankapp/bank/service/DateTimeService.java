package com.bankapp.bank.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateTimeService {

    public long getDateTime(){
        return System.currentTimeMillis();
    }

    public LocalDate getLocalDateNow(){
        return LocalDate.now();
    }

}
