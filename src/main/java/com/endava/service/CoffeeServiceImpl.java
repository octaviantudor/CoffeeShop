package com.endava.service;

import com.endava.model.Coffee;
import com.endava.model.RecipeIngredient;

import com.endava.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public Coffee createCoffee(List<RecipeIngredient> ingredients) {

        Coffee coffee = new Coffee();

        coffee.setIngredients(ingredients);

        return coffee;
    }

    @Override
    public List<Coffee> getAll() {

        return coffeeRepository.findAll();

    }

    @Override
    public void addCoffee(Coffee coffee) {
        coffeeRepository.save(coffee);
    }
}
