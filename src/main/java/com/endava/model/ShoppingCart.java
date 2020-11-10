package com.endava.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
@Getter
@Setter
public class ShoppingCart {

    private List<Coffee> shoppingList;
    private BigDecimal totalPrice;


    public ShoppingCart() {
        this.shoppingList = new ArrayList<>();
        totalPrice = new BigDecimal(0);
    }

}
