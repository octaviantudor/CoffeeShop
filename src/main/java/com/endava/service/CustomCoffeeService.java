package com.endava.service;

import com.endava.model.Coffee;

import com.endava.model.RecipeIngredient;
import com.endava.model.dto.CoffeeDTO;
import com.endava.model.dto.OrderDTO;

import com.endava.model.ingredients.StockIngredients;
import com.endava.validation.ValidationException;


import java.math.BigDecimal;
import java.util.List;

public interface CustomCoffeeService {



    void updateIngredientStock(List<RecipeIngredient> recipeIngredients,List<StockIngredients> stockIngredients);

    void enoughSupplies(RecipeIngredient recipeIngredient,List<Coffee> currentShoppingList,List<StockIngredients> stockIngredients) throws ValidationException;

    void updateIngredientsInDB(List<StockIngredients> stockIngredients);

    List<StockIngredients> getAllIngredients();

    BigDecimal validateAllCoffeesDTO(List<CoffeeDTO> coffeeDTOList) throws ValidationException;

    void stockValidation(List<Coffee> coffees) throws ValidationException;



}
