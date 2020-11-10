package com.endava.model.beverage;

import com.endava.model.Coffee;
import com.endava.model.RecipeIngredient;
import com.endava.model.ingredients.coffeBase.BlackCoffee;
import com.endava.model.ingredients.toppings.Cinnamon;
import com.endava.model.ingredients.toppings.Honey;
import com.endava.model.ingredients.toppings.SteamedMilk;

import javax.persistence.Entity;
import javax.persistence.Table;

public class CoffeeMiel extends Coffee {
    public CoffeeMiel() {
        super("COFFEE MIEL");
        this.addIngredients(new RecipeIngredient(new BlackCoffee(), 2));
        this.addIngredients(new RecipeIngredient(new Honey(), 1));
        this.addIngredients(new RecipeIngredient(new Cinnamon(), 1));
        this.addIngredients(new RecipeIngredient(new SteamedMilk(), 1));
    }
}