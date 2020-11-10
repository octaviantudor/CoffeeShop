package com.endava.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDTO {
    private String ingredientName;
    private Integer ingredientQuantity;

    public IngredientDTO(String ingredientName, Integer quantity) {
        this.ingredientName = ingredientName;
        this.ingredientQuantity = quantity;
    }
}
