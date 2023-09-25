package com.bankapp.bank.exception;

public class NotAuthorizedCountryException extends RuntimeException{

    public NotAuthorizedCountryException(String message) {
        super(message);
    }
}
