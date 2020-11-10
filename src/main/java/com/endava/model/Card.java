package com.endava.model;

import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;


@Getter
@Setter
public class Card {

    private String cardNumber;
    private String cardHolder;
    private String cvv;
    private YearMonth expirationDate;


    public Card(String cardNumber, String cardHolder, String cvv, YearMonth expirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public Card() {

    }

}