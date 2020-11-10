package com.endava.service;

import com.endava.model.Coffee;
import com.endava.model.RecipeIngredient;
import com.endava.model.ingredients.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingService {

    List<Coffee> getShoppingList();
    BigDecimal getTotalPrice();

    void addPrice(BigDecimal price);
    void addCoffe(Coffee c);
    void printShoppingList();
    void clearShoppingCart();

    void printRecipe();
    BigDecimal getRecipePrice();
    List<RecipeIngredient> getAllIngredients();
    void clearContainer();

    Integer getQuantity(RecipeIngredient recipeIngredient);

    void addItemInContainer(RecipeIngredient recipeIngredient);
}
