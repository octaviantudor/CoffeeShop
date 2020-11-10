package com.endava.service;

import com.endava.model.Card;
import com.endava.model.Coffee;
import com.endava.model.Order;
import com.endava.model.OrderedCoffee;
import com.endava.model.dto.CardDTO;
import com.endava.model.dto.OrderDTO;
import com.endava.validation.CardValidation;
import com.endava.validation.ValidationException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface OrderService {
    Order createOrder(List<OrderedCoffee> coffeeList, String name, BigDecimal totalPrice, Boolean pickUp);
    List<OrderDTO> getAllDTO();
    void validateCard(CardDTO card) throws CardValidation;
}
