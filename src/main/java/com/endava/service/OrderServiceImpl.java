package com.endava.service;

import com.endava.model.*;

import com.endava.model.dto.CardDTO;

import com.endava.model.dto.OrderDTO;
import com.endava.repository.OrderRepository;

import com.endava.utils.Utils;
import com.endava.validation.CardValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


import java.time.YearMonth;
import java.util.ArrayList;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Order createOrder(List<OrderedCoffee> coffeeList, String name, BigDecimal totalPrice, Boolean pickUp) {

        Customer customer = new Customer(name);

        Order orderedCoffees = new Order(customer, totalPrice);

        orderedCoffees.setPickUp(pickUp);


        orderedCoffees.setCoffees(coffeeList);

        orderedCoffees.setCard(new Card());

        orderRepository.save(orderedCoffees);

        return orderedCoffees;

    }


    @Override
    public List<OrderDTO> getAllDTO() {

        List<OrderDTO> orderDTOS = new ArrayList<>();

        orderRepository.findAll().forEach(
                order -> {
                    OrderDTO orderDTO = new OrderDTO(Utils.getCoffeesDTO(order.getCoffees()), order.getCustomer().getName(), order.getPickUp(), order.getTotalPrice());
                    orderDTOS.add(orderDTO);
                }

        );
        return orderDTOS;

    }


    public void luhnValidation(String cardNumber) throws CardValidation {
        int nDigits = cardNumber.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = cardNumber.charAt(i) - '0';

            if (isSecond)
                d = d * 2;

            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }

        if (nSum % 10 != 0) {
            throw new CardValidation("WRONG CARD NUMBER!");
        }


    }

    @Override
    public void validateCard(CardDTO card) throws CardValidation {

        luhnValidation(card.getCardNumber());
        cvvValidation(card.getCvv());
        validateCardHolder(card.getCardHolder());
        validateExpirationDate(card.getExpirationDate());

    }


    private void cvvValidation(String cvv) throws CardValidation {
        if (!cvv.matches("^[0-9]{3}$")) {
            throw new CardValidation("INVALID CVV!");
        }
    }

    private void validateExpirationDate(YearMonth expiryDate) throws CardValidation {
        if (expiryDate == null)
            throw new CardValidation("EXPIRY DATE WAS NOT PROVIDED!");

        if (YearMonth.now().isAfter(expiryDate))
            throw new CardValidation("CARD EXPIRED!");
    }

    private void validateCardHolder(String cardHolder) throws CardValidation {

        if (!cardHolder.matches("(^[A-Z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
            throw new CardValidation("INVALID NAME!");
        }
    }
}
