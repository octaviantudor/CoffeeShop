package com.endava.model.beverage;

import com.endava.model.Coffee;
import com.endava.model.RecipeIngredient;
import com.endava.model.ingredients.coffeBase.EspressoShot;
import com.endava.model.ingredients.toppings.MilkFoam;

import javax.persistence.Entity;
import javax.persistence.Table;

public class Macchiato extends Coffee {
    public Macchiato() {
        super("MACCHIATO");
        this.addIngredients(new RecipeIngredient(new EspressoShot(),2));
        this.addIngredients(new RecipeIngredient(new MilkFoam(),1));
    }
}