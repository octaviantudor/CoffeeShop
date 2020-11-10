package com.endava.console;

import com.endava.model.*;

import com.endava.model.ingredients.StockIngredients;
import com.endava.service.OrderService;
import com.endava.service.ShoppingService;
import com.endava.service.CoffeeService;
import com.endava.service.CustomCoffeeService;
import com.endava.utils.Utils;
import com.endava.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;


public class Console {


    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CustomCoffeeService customCoffeeService;
    @Autowired
    private ShoppingService shoppingService;
    @Autowired
    private OrderService orderService;


    private String customerName;

    private Scanner keyboard;

    private BigDecimal totalProfit;


    public void run() {

        this.keyboard = new Scanner(System.in);
        totalProfit = new BigDecimal(0);

        Integer option;
        customerName = readNameOption();
        do {

            printMenuUi();
            option = readOption();

            switch (option) {
                case 1:
                    printCoffees("CHOOSE YOUR COFFEE");
                    break;
                case 2:
                    printIngredients("CHOOSE YOUR COFFEE INGREDIENTS");
                    break;
                case 3:
                    checkShoppingCart();
                    break;
                case 4:
                    completeOrder();
                    break;
                case 5:
                    checkSupplies();
                    break;
                case 6:
                    break;
                default:
                    invalidOption();
                    break;
            }
        } while (option != 6);

    }


    private void checkSupplies() {
        List<StockIngredients> stockIngredientsList = customCoffeeService.getAllIngredients();
        for (StockIngredients stockIngredients : stockIngredientsList) {

            System.out.println(stockIngredientsList.indexOf(stockIngredients) + 1 +
                    ". " + stockIngredients.getIngredient().getName() +
                    " - QUANTITY: " + stockIngredients.getStockQuantity());
        }

        try {
            customCoffeeService.stockValidation(coffeeService.getAll());
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }

    }

    void checkShoppingCart() {

        System.out.println("CURRENT PRICE: " + shoppingService.getTotalPrice());
        shoppingService.printShoppingList();
    }

    void completeOrder() {

        System.out.println("------------[ COMPLETE ORDER ]------------");

        System.out.println("1. PICK-UP ");
        System.out.println("2. DELIVERY ");
        System.out.println("3. CANCEL ORDER");

        System.out.println("------------[ COMPLETE ORDER ]------------");

        switch (readOption()) {
            case 1:
                finalizeOrder(true);
                break;
            case 2:
                finalizeOrder(false);
                break;
            case 3:
                break;
            default:
                invalidOption();
                break;
        }


    }


    public void finalizeOrder(Boolean pickUp) {

        totalProfit = totalProfit.add(shoppingService.getTotalPrice());

        List<OrderedCoffee> orderedCoffees = Utils.toOrderedCoffeeList(shoppingService.getShoppingList());

        Order order = orderService.createOrder(orderedCoffees, customerName, shoppingService.getTotalPrice(), pickUp);

        System.out.println("ORDER: " + order.toString() + " - TOTAL PROFIT: " + totalProfit);

        List<StockIngredients> stockIngredientsList = customCoffeeService.getAllIngredients();

        shoppingService.getShoppingList().stream()
                .map(Coffee::getIngredients)
                .forEach(recipeIngredients -> {
                    customCoffeeService.updateIngredientStock(recipeIngredients,stockIngredientsList);
                });

        customCoffeeService.updateIngredientsInDB(stockIngredientsList);
        shoppingService.clearShoppingCart();

    }


    public void printCoffees(String title) {
        System.out.println("--------------[" + title + "]--------------");


        List<Coffee> standardCoffees = coffeeService.getAll();

        standardCoffees.stream()
                .map(x -> standardCoffees.indexOf(x) + 1 + ". " + x.getName())
                .forEach(System.out::println);

        System.out.println("--------------[" + title + "]--------------");

        List<StockIngredients> stockIngredientsList = customCoffeeService.getAllIngredients();
        Integer option = readOption();

        while (option < 1 || option > standardCoffees.size()) {
            invalidOption();
            option = readOption();
        }

        try {

            for (RecipeIngredient recipeIngredient : standardCoffees.get(option - 1).getIngredients()) {
                customCoffeeService.enoughSupplies(recipeIngredient,  shoppingService.getShoppingList(),stockIngredientsList);
            }

            shoppingService.addCoffe(standardCoffees.get(option - 1));

            shoppingService.addPrice(standardCoffees.get(option - 1).getPrice());

            System.out.println("CURRENT PRICE: " + shoppingService.getTotalPrice());


            shoppingService.printShoppingList();
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }

    }


    public void printIngredients(String title) {
        System.out.println("--------------[" + title + "]--------------");

        List<StockIngredients> ingredientList = customCoffeeService.getAllIngredients();

        ingredientList.stream()
                .map(ingredientStock -> ingredientList.indexOf(ingredientStock) + 1 + ". " + ingredientStock.getIngredient().getName())
                .forEach(System.out::println);

        System.out.println(ingredientList.size() + 1 + ". CONFIRM RECIPE ");
        System.out.println(ingredientList.size() + 2 + ". EXIT WITHOUT CONFIRMING \n");

        Integer option = readOption();
        while (option >= 1 && option < ingredientList.size() + 1) {
            try {

                RecipeIngredient recipeIngredient = new RecipeIngredient(ingredientList.get(option - 1).getIngredient(), 1);

                recipeIngredient.setQuantity(recipeIngredient.getQuantity() + shoppingService.getQuantity(recipeIngredient));

                customCoffeeService.enoughSupplies(recipeIngredient,  shoppingService.getShoppingList(),ingredientList);

                shoppingService.addItemInContainer(recipeIngredient);


            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
            System.out.println("CURRENT RECIPE PRICE: " + shoppingService.getRecipePrice());

            shoppingService.printRecipe();

            option = readOption();
        }
        if (option == ingredientList.size() + 1) {
            confirmRecipe();
        } else {
            shoppingService.clearContainer();
        }

    }

    public void confirmRecipe() {

        List<RecipeIngredient> ingredients = new ArrayList<>(shoppingService.getAllIngredients());

        Coffee coffee = coffeeService.createCoffee(ingredients);


        coffee.setName(Utils.createName(ingredients));

        shoppingService.addCoffe(coffee);

        shoppingService.addPrice(coffee.getPrice());

        System.out.println("CURRENT PRICE: " + shoppingService.getTotalPrice());
        shoppingService.printShoppingList();

        shoppingService.clearContainer();

    }


    public void printMenuUi() {
        System.out.println("------------[COFFEE SHOP]------------");
        System.out.println("1. ORDER COFFEE");
        System.out.println("2. ORDER CUSTOM COFFEE");
        System.out.println("3. CHECK SHOPPING CART");
        System.out.println("4. COMPLETE ORDER");
        System.out.println("5. CHECK SUPPLIES");
        System.out.println("------------[COFFEE SHOP]------------");
        System.out.println("6. EXIT");
    }

    public String readNameOption() {
        System.out.print("ENTER YOUR NAME: ");
        return keyboard.nextLine();
    }


    public Integer readOption() {
        Integer option;
        System.out.print("ENTER OPTION: ");
        option = Integer.parseInt(keyboard.nextLine());

        return option;

    }

    public void invalidOption() {
        System.out.println("INVALID OPTION! PLEASE SELECT A VALID OPTION!");
    }
}
