package com.endava.model.beverage;

import com.endava.model.Coffee;
import com.endava.model.RecipeIngredient;
import com.endava.model.ingredients.coffeBase.EspressoShot;
import com.endava.model.ingredients.toppings.MilkFoam;
import com.endava.model.ingredients.toppings.SteamedMilk;

import javax.persistence.Entity;

public class Cappuccino extends Coffee {

    public Cappuccino() {
        super("CAPPUCCINO");
        this.addIngredients(new RecipeIngredient(new EspressoShot(),1));
        this.addIngredients(new RecipeIngredient(new SteamedMilk(),1));
        this.addIngredients(new RecipeIngredient(new MilkFoam(),2));
    }
}
