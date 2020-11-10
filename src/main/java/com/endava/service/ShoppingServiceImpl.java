package com.endava.service;

import com.endava.model.Coffee;
import com.endava.model.RecipeIngredient;
import com.endava.model.RecipeContainer;
import com.endava.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private ShoppingCart shoppingCart;

    @Autowired
    private RecipeContainer recipeContainer;


    public void printRecipe() {
        System.out.print("RECIPE: ");

        recipeContainer.getRecipeIngredients().stream().map(x -> x.getIngredient().getName() + "x" + x.getQuantity() + " ").forEach(System.out::print);
        System.out.println("\n");
    }


    public BigDecimal getRecipePrice() {

        return recipeContainer.getRecipeIngredients().stream().map(RecipeIngredient::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<RecipeIngredient> getAllIngredients() {
        return recipeContainer.getRecipeIngredients();
    }

    public void clearContainer() {
        recipeContainer.getRecipeIngredients().clear();
    }


    @Override
    public Integer getQuantity(RecipeIngredient recipeIngredient) {

        for (RecipeIngredient toFind : recipeContainer.getRecipeIngredients()) {
            if (toFind.getIngredient().equals(recipeIngredient.getIngredient())) {
                return toFind.getQuantity();
            }
        }
        return 0;
    }

    @Override
    public void addItemInContainer(RecipeIngredient recipeIngredient) {

        boolean found = false;
        for (RecipeIngredient ri : recipeContainer.getRecipeIngredients()) {
            if (ri.getIngredient().equals(recipeIngredient.getIngredient())) {
                ri.setQuantity(recipeIngredient.getQuantity());
                ri.setPrice(ri.getPrice().add(recipeIngredient.getPrice()));
                found = true;
            }
        }
        if (!found) {
            List<RecipeIngredient> recipeIngredients = recipeContainer.getRecipeIngredients();
            recipeIngredients.add(recipeIngredient);
            recipeContainer.setRecipeIngredients(recipeIngredients);
        }

    }

    public List<Coffee> getShoppingList() {
        return shoppingCart.getShoppingList();
    }

    public void clearShoppingCart() {
        shoppingCart.getShoppingList().clear();
        shoppingCart.setTotalPrice(new BigDecimal(0));

    }


    public BigDecimal getTotalPrice() {
        return shoppingCart.getTotalPrice();
    }


    public void addPrice(BigDecimal price) {
        BigDecimal shoppingCartPrice = shoppingCart.getTotalPrice();
        shoppingCartPrice = shoppingCartPrice.add(price);
        shoppingCart.setTotalPrice(shoppingCartPrice);
    }

    public void addCoffe(Coffee c) {

        List<Coffee> coffees = shoppingCart.getShoppingList();
        coffees.add(c);
        shoppingCart.setShoppingList(coffees);

    }

    public void printShoppingList() {
        System.out.print("CURRENT SHOPPING LIST: [ ");

        List<Coffee> shoppingList = shoppingCart.getShoppingList();

        StringJoiner stringJoiner = new StringJoiner(" || ");

        shoppingList.forEach(coffee -> {
            stringJoiner.add(coffee.getName());
        });

        System.out.print(stringJoiner.toString());
        System.out.print("]");
        System.out.println("\n");
    }


}
