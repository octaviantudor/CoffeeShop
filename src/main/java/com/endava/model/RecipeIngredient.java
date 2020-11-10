package com.endava.model;

import com.endava.model.ingredients.Ingredient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Table(name = "RecipeIngredient")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne()
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;


    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;


    public RecipeIngredient(Ingredient ingredient, Integer quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.price=ingredient.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public RecipeIngredient() {


    }
}
