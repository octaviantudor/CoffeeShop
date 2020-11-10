package com.endava.controller;

import com.endava.validation.CardValidation;
import com.endava.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.ParseException;

@ControllerAdvice
@Slf4j
public class ErrorHandler {


    @ResponseBody
    @ExceptionHandler(CardValidation.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidCardHandler(CardValidation e) {
        log.error("Order failed: " + e.getMessage());
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidValidationHandler(ValidationException e) {
        log.error("Order failed: " + e.getMessage());
        return e.getMessage();
    }


}
