package com.endava.model.beverage;

import com.endava.model.Coffee;
import com.endava.model.RecipeIngredient;
import com.endava.model.ingredients.coffeBase.EspressoShot;

import javax.persistence.Entity;
import javax.persistence.Table;

public class Espresso extends Coffee {
    public Espresso(){
        super("ESPRESSO");
        this.addIngredients(new RecipeIngredient(new EspressoShot(),2));
    }
}
