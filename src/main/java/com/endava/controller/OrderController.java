package com.endava.controller;

import com.endava.model.Card;

import com.endava.model.Coffee;
import com.endava.model.OrderedCoffee;
import com.endava.model.dto.CardDTO;
import com.endava.model.dto.OrderDTO;
import com.endava.service.CustomCoffeeService;
import com.endava.service.OrderService;

import com.endava.utils.Utils;
import com.endava.validation.CardValidation;
import com.endava.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomCoffeeService customCoffeeService;


    @PostMapping(value = "/pay")
    public ResponseEntity<HttpStatus> pay(@RequestBody OrderDTO orderDTO) throws CardValidation, ValidationException {

        log.info("New order: {}", orderDTO);

        orderService.validateCard(orderDTO.getCard());

        List<OrderedCoffee> orderedCoffees = Utils.toOrderedCoffeeListFromDTO(orderDTO.getBeverages());

        orderService.createOrder(orderedCoffees, orderDTO.getCustomerName(), customCoffeeService.validateAllCoffeesDTO(orderDTO.getBeverages()), orderDTO.getPickUp());

        log.info("Order completed: {}", orderDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @GetMapping(value = "")
    public List<OrderDTO> getOrders() {
        return orderService.getAllDTO();
    }
}
