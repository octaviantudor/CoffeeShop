package com.endava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


@Getter
@Setter
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<OrderedCoffee> coffees;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "pickUp")
    private Boolean pickUp;

    @Column(name = "totalPrice")
    private BigDecimal totalPrice;

    @Transient
    private Card card;

    public Order(Customer customer, BigDecimal totalPrice) {
        this.coffees = new ArrayList<>();
        this.customer = customer;
        this.totalPrice = totalPrice;
    }


    public Order() {

    }

    @Override
    public String toString() {

        StringJoiner stringJoiner = new StringJoiner(" || ");
        coffees.forEach(coffee->{
            stringJoiner.add(coffee.getBeverageName());
        });

        return "[" +
                "Coffees: " + stringJoiner.toString() +
                ", CustomerName: '" + customer.getName() + '\'' +
                ", pickUp=" + pickUp + ']';
    }

}
