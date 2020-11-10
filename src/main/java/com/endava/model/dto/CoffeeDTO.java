package com.endava.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter

public class CoffeeDTO {

    private String beverageName;

    private Integer quantity;

    private List<IngredientDTO> ingredients;

    public CoffeeDTO(String beverageName, Integer quantity,List<IngredientDTO> ingredientDTOS) {
        this.beverageName = beverageName;
        this.quantity = quantity;
        this.ingredients=ingredientDTOS;
    }

    public CoffeeDTO(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoffeeDTO coffeeDTO = (CoffeeDTO) o;
        return Objects.equals(beverageName, coffeeDTO.beverageName) &&
                Objects.equals(quantity, coffeeDTO.quantity);
    }


}
