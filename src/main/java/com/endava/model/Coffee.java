package com.endava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Coffee")
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "coffee_id")
    private List<RecipeIngredient> ingredients;


    public Coffee() {
        this.price = new BigDecimal(0);
        ingredients = new ArrayList<>();

    }

    public Coffee(String name) {
        this.name = name;
        this.price = new BigDecimal(0);
        ingredients = new ArrayList<>();
    }


    public void addIngredients(RecipeIngredient ingredient) {

        ingredients.add(ingredient);
        price = price.add(ingredient.getPrice());
    }

    public void setIngredients(List<RecipeIngredient> recipeIngredients){

        BigDecimal price = new BigDecimal(0);

        for (RecipeIngredient recipeIngredient: recipeIngredients){
            ingredients.add(recipeIngredient);
            price = price.add(recipeIngredient.getPrice());
        }

        setPrice(price);
    }

    @Override
    public String toString() {
        return "Coffe{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
