package com.endava.model.beverage;

import com.endava.model.Coffee;
import com.endava.model.RecipeIngredient;
import com.endava.model.ingredients.coffeBase.EspressoShot;
import com.endava.model.ingredients.toppings.MilkFoam;
import com.endava.model.ingredients.toppings.SteamedMilk;

import javax.persistence.Entity;
import javax.persistence.Table;

public class CoffeeLatte extends Coffee {
    public CoffeeLatte() {
        super("COFFEE LATTE");
        this.addIngredients(new RecipeIngredient(new EspressoShot(),1));
        this.addIngredients(new RecipeIngredient(new SteamedMilk(),2));
        this.addIngredients(new RecipeIngredient(new MilkFoam(),1));
    }
}
