package com.endava.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "OrderedCoffees")
public class OrderedCoffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "beverageName")
    private String beverageName;

    @Column(name = "quantity")
    private Integer quantity;

    public OrderedCoffee() {
    }

    public OrderedCoffee(String beverageName,Integer quantity){
        this.beverageName = beverageName;
        this.quantity=quantity;
    }

}
