package com.endava.model.dto;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter

public class OrderDTO {

    private CardDTO card;

    private List<CoffeeDTO> beverages;

    private String customerName;

    private Boolean pickUp;

    private BigDecimal totalPrice;

    public OrderDTO(CardDTO card, List<CoffeeDTO> beverages, String customerName, Boolean pickUp) {
        this.card = card;
        this.beverages = beverages;
        this.customerName = customerName;
        this.pickUp = pickUp;
    }

    public OrderDTO() {

    }
    public OrderDTO( List<CoffeeDTO> beverages, String customerName, Boolean pickUp, BigDecimal totalPrice) {
        this.beverages = beverages;
        this.customerName = customerName;
        this.pickUp = pickUp;
        this.totalPrice=totalPrice;
    }

}