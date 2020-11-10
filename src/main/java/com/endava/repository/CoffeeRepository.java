package com.endava.repository;

import com.endava.model.Coffee;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CoffeeRepository extends CrudRepository<Coffee, Integer> {

    List<Coffee> findAll();

}
