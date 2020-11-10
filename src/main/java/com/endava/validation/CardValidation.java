package com.endava.validation;

public class CardValidation extends Exception{

    public CardValidation(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
