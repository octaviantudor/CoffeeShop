package com.endava.service;

import com.endava.model.Coffee;
import com.endava.model.RecipeIngredient;

import java.util.List;

public interface CoffeeService {

    Coffee createCoffee(List<RecipeIngredient> ingredients);

    List<Coffee> getAll();

    void addCoffee(Coffee coffee);



}
