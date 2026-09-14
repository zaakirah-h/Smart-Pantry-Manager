package com.zaakirah.smartpantry;

import android.content.Context;

import java.util.List;

public class RecipeSeeder {

    public static void seedRecipes(Context context) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        List<Recipe> existingRecipes = databaseHelper.getAllRecipes();

        if (!existingRecipes.isEmpty()) {
            databaseHelper.close();
            return;
        }

        databaseHelper.addRecipe(new Recipe(
                "Cheese Omelette",
                "eggs:2|cheese:1|tomatoes:1",
                "Beat the eggs. Chop the tomato. Cook the eggs in a pan, add tomato and cheese, then fold the omelette."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Egg Sandwich",
                "eggs:2|bread:2|cheese:1",
                "Cook the eggs. Place eggs and cheese between two slices of bread and serve."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Tomato Toast",
                "bread:2|tomatoes:2|cheese:1",
                "Toast the bread. Add sliced tomatoes and cheese. Grill until the cheese melts."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Cheesy Scrambled Eggs",
                "eggs:3|cheese:1",
                "Beat the eggs and cook them slowly in a pan. Add cheese and stir until melted."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Tomato Egg Stir Fry",
                "eggs:2|tomatoes:2|onions:1",
                "Cook chopped onions and tomatoes. Add beaten eggs and stir until cooked."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Grilled Cheese",
                "bread:2|cheese:2",
                "Place cheese between bread slices and grill both sides until golden and melted."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Cheese Pasta",
                "pasta:200|cheese:2|milk:100",
                "Cook the pasta. Heat milk and cheese together, then mix with the cooked pasta."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Tomato Pasta",
                "pasta:200|tomatoes:3|onions:1",
                "Cook pasta. Fry onions and tomatoes, then combine with the cooked pasta."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Chicken Pasta",
                "pasta:200|chicken:200|cheese:1",
                "Cook pasta and chicken separately. Combine them and add cheese before serving."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Chicken Sandwich",
                "bread:2|chicken:150|tomatoes:1",
                "Cook the chicken and slice it. Add chicken and tomatoes between slices of bread."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Chicken Rice",
                "rice:200|chicken:200|onions:1",
                "Cook the rice. Fry onions and chicken, then combine with the rice."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Vegetable Rice",
                "rice:200|carrots:2|peas:100",
                "Cook the rice. Cook the vegetables separately, then mix them with the rice."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Banana Pancakes",
                "bananas:2|eggs:2|flour:100|milk:100",
                "Mash the bananas. Mix with eggs, flour and milk. Cook small pancakes in a pan."
        ));

        databaseHelper.addRecipe(new Recipe(
                "French Toast",
                "bread:2|eggs:2|milk:100",
                "Whisk eggs and milk together. Dip the bread into the mixture and fry until golden."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Cheese Quesadilla",
                "tortilla:2|cheese:2|tomatoes:1",
                "Add cheese and chopped tomatoes to a tortilla. Fold and cook in a pan until the cheese melts."
        ));

        databaseHelper.close();
    }
}