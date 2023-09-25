package com.bankapp.bank.controller;

import com.bankapp.bank.exception.NotAuthorizedByAgeException;
import com.bankapp.bank.exception.NotAuthorizedCountryException;
import com.bankapp.bank.exception.UserAlreadyExistsException;
import com.bankapp.bank.model.ErrorMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<ErrorMessage> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return ex.getConstraintViolations().stream().map(constraintViolation -> new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage())).toList();
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class, NotAuthorizedCountryException.class, NotAuthorizedByAgeException.class,
            DateTimeParseException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleException(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

}
