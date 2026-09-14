package com.zaakirah.smartpantry;

public class Recipe {

    private int id;
    private String name;
    private String ingredients;
    private String preparationSteps;

    public Recipe(int id, String name, String ingredients, String preparationSteps) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
    }

    public Recipe(String name, String ingredients, String preparationSteps) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getPreparationSteps() {
        return preparationSteps;
    }
}