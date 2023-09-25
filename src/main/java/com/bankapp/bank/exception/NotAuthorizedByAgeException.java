package com.bankapp.bank.exception;

public class NotAuthorizedByAgeException extends RuntimeException{

    public NotAuthorizedByAgeException(String message) {
        super(message);
    }
}
