package com.endava.repository;

import com.endava.model.ingredients.StockIngredients;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface IngredientRepository extends CrudRepository<StockIngredients, Integer> {

    List<StockIngredients> findAll();

}
