package com.endava.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class RecipeContainer {

    public List<RecipeIngredient> recipeIngredients;


    public RecipeContainer() {
        this.recipeIngredients = new ArrayList<>();
    }


}
