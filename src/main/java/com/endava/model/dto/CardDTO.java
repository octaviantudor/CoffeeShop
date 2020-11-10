package com.endava.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
public class CardDTO {
    private String cardNumber;
    private String cardHolder;
    private String cvv;
    private YearMonth expirationDate;

    public CardDTO(String cardNumber, String cardHolder, String cvv, YearMonth expirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public CardDTO() {
    }
}
