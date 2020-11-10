package com.endava.service;


import com.endava.model.Coffee;

import com.endava.model.RecipeIngredient;

import com.endava.model.dto.CoffeeDTO;
import com.endava.model.dto.IngredientDTO;
import com.endava.model.ingredients.Ingredient;
import com.endava.model.ingredients.StockIngredients;

import com.endava.repository.IngredientRepository;
import com.endava.utils.Utils;
import com.endava.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class CustomCoffeeServiceImpl implements CustomCoffeeService {

    @Autowired
    private IngredientRepository ingredientRepository;


    @Override
    public BigDecimal validateAllCoffeesDTO(List<CoffeeDTO> coffeeDTOList) throws ValidationException {

        BigDecimal totalPrice = new BigDecimal(0);
        List<StockIngredients> stockIngredientsList = getAllIngredients();
        for (CoffeeDTO coffeeDTO : coffeeDTOList) {
            totalPrice = totalPrice.add(validateCoffeeDTO(coffeeDTO,stockIngredientsList));
        }
        ingredientRepository.saveAll(stockIngredientsList);
        return totalPrice;
    }



    public BigDecimal validateCoffeeDTO(CoffeeDTO coffeeDTO, List<StockIngredients> stockIngredients) throws ValidationException{

        List<IngredientDTO> filteredIngredientsDTO = coffeeDTO.getIngredients().stream().filter(
                ingredientDTO -> stockIngredients.stream().anyMatch(stockIngredient -> stockIngredient.getIngredient().getName().equals(ingredientDTO.getIngredientName()) &&
                        stockIngredient.getStockQuantity().compareTo(BigDecimal.valueOf(coffeeDTO.getQuantity()*ingredientDTO.getIngredientQuantity()))>=0)
        ).collect(Collectors.toList());


        if (filteredIngredientsDTO.size()!=coffeeDTO.getIngredients().size()){
            throw new ValidationException("WARNING! INSUFFICIENT STOCK!");
        }

        updateStockIngredientsFromDTOS(stockIngredients,filteredIngredientsDTO, coffeeDTO.getQuantity());

        return Utils.calculatePriceOfCoffee(filteredIngredientsDTO,stockIngredients);


    }


    public static void updateStockIngredientsFromDTOS(List<StockIngredients> stockIngredients, List<IngredientDTO> ingredientDTOS,Integer coffeeQuantity){

        stockIngredients.forEach(stockIngredient->{
            ingredientDTOS.forEach(
                    ingredientDTO -> {
                        if (ingredientDTO.getIngredientName().equals(stockIngredient.getIngredient().getName())){
                            stockIngredient.setStockQuantity(stockIngredient.getStockQuantity().subtract(BigDecimal.valueOf(ingredientDTO.getIngredientQuantity()*coffeeQuantity)));
                        }
                    }
            );
        });
    }

    @Override
    public void updateIngredientStock(List<RecipeIngredient> recipeIngredients,List<StockIngredients> stockIngredients) {



        stockIngredients.forEach(stockIngredient -> {
            recipeIngredients.forEach(
                    recipeIngredient -> {
                        if (recipeIngredient.getIngredient().getName().equals(stockIngredient.getIngredient().getName())){
                            stockIngredient.setStockQuantity(stockIngredient.getStockQuantity().subtract(
                                    BigDecimal.valueOf(recipeIngredient.getQuantity())
                            ));
                        }
                    }
            );
        });

        updateIngredientsInDB(stockIngredients);

    }

    @Override
    public void enoughSupplies(RecipeIngredient recipeIngredient, List<Coffee> currentShoppingList,List<StockIngredients> stockIngredients) throws ValidationException {
        List<StockIngredients> validIngredients;


        AtomicReference<Integer> nrOfIngredients = new AtomicReference<>(0);


        currentShoppingList.forEach(
                coffee -> nrOfIngredients.updateAndGet(ingredientNumber -> ingredientNumber + Utils.getNrOfRecipeIngredient(recipeIngredient, coffee.getIngredients()))
        );


        validIngredients = stockIngredients.stream()
                .filter(stockIngredient -> stockIngredient.getIngredient().getName().equals(recipeIngredient.getIngredient().getName()) &&
                        stockIngredient.getStockQuantity().subtract(BigDecimal.valueOf(recipeIngredient.getQuantity()).add(BigDecimal.valueOf(nrOfIngredients.get()))).compareTo(BigDecimal.valueOf(0)) >= 0)
                .collect(Collectors.toList());


        if (validIngredients.size() < 1)
            throw new ValidationException("WARNING! INSUFFICIENT SUPPLIES \n");

    }

    @Override
    public void updateIngredientsInDB(List<StockIngredients> stockIngredients) {
        ingredientRepository.saveAll(stockIngredients);
    }


    @Override
    public List<StockIngredients> getAllIngredients() {
        return ingredientRepository.findAll();
    }


    @Override
    public void stockValidation(List<Coffee> coffees) throws ValidationException {

        List<Ingredient> invalidIngredients = Utils.getMaxAmountForIngredient(coffees).stream()
                .filter(ingredient -> ingredientRepository.findAll().stream()
                        .anyMatch(stockIngredient ->
                                stockIngredient.getIngredient().getName().equals(ingredient.getName()) &&
                                        stockIngredient.getStockQuantity().compareTo(BigDecimal.valueOf(ingredient.getMaxNrInCoffee()).multiply(BigDecimal.valueOf(3))) < 0
                        )
                ).collect(Collectors.toList());

        if (invalidIngredients.size() > 0) {
            throw new ValidationException("WARNING || NOT ENOUGH SUPPLIES FOR 3 MORE COFFEES");

        }


    }


}
