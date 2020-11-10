package com.endava.model.ingredients;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "maxNrInCoffee")
    private Integer maxNrInCoffee;


    public Ingredient(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        maxNrInCoffee=0;
    }

    public Ingredient() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }


    public void setMaxNrInCoffee(Integer maxNrInCoffee) {
        this.maxNrInCoffee = maxNrInCoffee;
    }

    public Integer getMaxNrInCoffee() {
        return maxNrInCoffee;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name);
    }

}
