package com.endava.utils;

import com.endava.model.Coffee;
import com.endava.model.OrderedCoffee;
import com.endava.model.RecipeIngredient;
import com.endava.model.dto.CoffeeDTO;
import com.endava.model.dto.IngredientDTO;
import com.endava.model.ingredients.Ingredient;
import com.endava.model.ingredients.StockIngredients;
import com.endava.model.ingredients.coffeBase.BlackCoffee;
import com.endava.model.ingredients.coffeBase.EspressoShot;
import com.endava.model.ingredients.sweetener.BlackSugar;
import com.endava.model.ingredients.sweetener.Sugar;
import com.endava.model.ingredients.toppings.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicReference;

public class Utils {

    public Utils() {
    }

    public static void setIngredientMaxAmount(Ingredient ingredient, List<Coffee> coffees) {

        coffees.forEach(coffee -> {

            coffee.getIngredients()
                    .stream()
                    .filter(x -> x.getIngredient().getName().equals(ingredient.getName())
                            && x.getQuantity().compareTo(ingredient.getMaxNrInCoffee()) > 0)
                    .findAny().ifPresent(recipeIngredient -> {
                ingredient.setMaxNrInCoffee(recipeIngredient.getQuantity());
                ;

            });
        });
    }

    public static Integer getCoffeeNumberFromList(List<Coffee> coffees, Coffee coffee) {

        AtomicReference<Integer> numberOfCoffees = new AtomicReference<>(0);

        coffees.forEach(
                coffee1 -> {
                    if (coffee1.getName().equals(coffee.getName()))
                        numberOfCoffees.updateAndGet(coffeeNumber -> coffeeNumber + 1);
                }
        );
        return numberOfCoffees.get();
    }


    public static String createName(List<RecipeIngredient> recipeIngredients) {
        String coffeeName = "COFFEE WITH ";

        StringJoiner stringJoiner = new StringJoiner(", ");

        recipeIngredients.forEach(
                recipeIngredient -> stringJoiner.add(recipeIngredient.getIngredient().getName() + " X" + recipeIngredient.getQuantity().toString()));

        return coffeeName + stringJoiner.toString();
    }

    public static BigDecimal calculatePriceOfCoffee(List<IngredientDTO> ingredientDTOS, List<StockIngredients> stockIngredients){
        final BigDecimal[] priceOfCoffee = {new BigDecimal(0)};


        stockIngredients.stream().map(StockIngredients::getIngredient).forEach(ingredient -> {
            ingredientDTOS.forEach(ingredientDTO -> {
                if (ingredientDTO.getIngredientName().equals(ingredient.getName())){
                    priceOfCoffee[0] = priceOfCoffee[0].add(ingredient.getPrice().multiply(BigDecimal.valueOf(ingredientDTO.getIngredientQuantity())));
                }
            });
        });
        return priceOfCoffee[0];
    }

    public static List<OrderedCoffee> toOrderedCoffeeList(List<Coffee> coffees) {
        List<OrderedCoffee> orderedCoffeeList = new ArrayList<>();
        coffees.forEach(
                coffee -> {
                    OrderedCoffee orderedCoffee = new OrderedCoffee(coffee.getName(), getCoffeeNumberFromList(coffees, coffee));
                    orderedCoffeeList.add(orderedCoffee);
                }
        );
        return orderedCoffeeList;
    }

    public static List<OrderedCoffee> toOrderedCoffeeListFromDTO(List<CoffeeDTO> coffeeDTOS){

        List<OrderedCoffee> orderedCoffeeList = new ArrayList<>();

        coffeeDTOS.forEach(
                coffeeDTO -> {
                    OrderedCoffee orderedCoffee = new OrderedCoffee(coffeeDTO.getBeverageName(),coffeeDTO.getQuantity());
                    orderedCoffeeList.add(orderedCoffee);
                }
        );
        return orderedCoffeeList;
    }

    public static List<Ingredient> getMaxAmountForIngredient(List<Coffee> coffees) {

        List<Ingredient> ingredients = Arrays.asList(new EspressoShot(), new Honey(), new BlackCoffee(),
                new Sugar(), new BlackSugar(), new Cinnamon(),
                new MilkFoam(), new SteamedMilk(), new HotWater());

        for (Ingredient ingredient : ingredients) {
            Utils.setIngredientMaxAmount(ingredient, coffees);
        }

        return ingredients;

    }

    public static List<CoffeeDTO> getCoffeesDTO(List<OrderedCoffee> coffees) {
        List<CoffeeDTO> coffeeDTOS = new ArrayList<>();

        coffees.forEach(orderedCoffee -> {
            CoffeeDTO coffeeDTO = new CoffeeDTO();
            coffeeDTO.setBeverageName(orderedCoffee.getBeverageName());
            coffeeDTO.setQuantity(orderedCoffee.getQuantity());
            coffeeDTO.setIngredients(new ArrayList<>());

            if (!coffeeDTOS.contains(coffeeDTO)) {
                coffeeDTOS.add(coffeeDTO);
            }
        });
        return coffeeDTOS;
    }

    public static Integer getNrOfRecipeIngredient(RecipeIngredient recipeIngredient, List<RecipeIngredient> recipe) {

        AtomicReference<Integer> toSubstract = new AtomicReference<>(0);

        recipe.forEach(recipeIngredient1 -> {
            if (recipeIngredient1.getIngredient().equals(recipeIngredient.getIngredient())) {
                toSubstract.updateAndGet(numberOfRecipeIngredients -> numberOfRecipeIngredients + recipeIngredient1.getQuantity());
            }

        });
        return toSubstract.get();
    }

}
