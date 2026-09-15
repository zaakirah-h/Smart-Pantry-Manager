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
                "eggs:2:pieces|cheese:1:piece|tomatoes:1:piece",
                "Beat the eggs. Chop the tomato. Cook the eggs in a pan, add tomato and cheese, then fold the omelette."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Egg Sandwich",
                "eggs:2:pieces|bread:2:pieces|cheese:1:piece",
                "Cook the eggs. Place eggs and cheese between two slices of bread and serve."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Tomato Toast",
                "bread:2:pieces|tomatoes:2:pieces|cheese:1:piece",
                "Toast the bread. Add sliced tomatoes and cheese. Grill until the cheese melts."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Cheesy Scrambled Eggs",
                "eggs:3:pieces|cheese:1:piece",
                "Beat the eggs and cook them slowly in a pan. Add cheese and stir until melted."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Tomato Egg Stir Fry",
                "eggs:2:pieces|tomatoes:2:pieces|onions:1:piece",
                "Cook chopped onions and tomatoes. Add beaten eggs and stir until cooked."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Grilled Cheese",
                "bread:2:pieces|cheese:2:pieces",
                "Place cheese between bread slices and grill both sides until golden and melted."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Cheese Pasta",
                "pasta:200:g|cheese:2:pieces|milk:100:ml",
                "Cook the pasta. Heat milk and cheese together, then mix with the cooked pasta."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Tomato Pasta",
                "pasta:200:g|tomatoes:3:pieces|onions:1:piece",
                "Cook pasta. Fry onions and tomatoes, then combine with the cooked pasta."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Chicken Pasta",
                "pasta:200:g|chicken:200:g|cheese:1:piece",
                "Cook pasta and chicken separately. Combine them and add cheese before serving."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Chicken Sandwich",
                "bread:2:pieces|chicken:150:g|tomatoes:1:piece",
                "Cook the chicken and slice it. Add chicken and tomatoes between slices of bread."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Chicken Rice",
                "rice:200:g|chicken:200:g|onions:1:piece",
                "Cook the rice. Fry onions and chicken, then combine with the rice."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Vegetable Rice",
                "rice:200:g|carrots:2:pieces|peas:100:g",
                "Cook the rice. Cook the vegetables separately, then mix them with the rice."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Banana Pancakes",
                "bananas:2:pieces|eggs:2:pieces|flour:100:g|milk:100:ml",
                "Mash the bananas. Mix with eggs, flour and milk. Cook small pancakes in a pan."
        ));

        databaseHelper.addRecipe(new Recipe(
                "French Toast",
                "bread:2:pieces|eggs:2:pieces|milk:100:ml",
                "Whisk eggs and milk together. Dip the bread into the mixture and fry until golden."
        ));

        databaseHelper.addRecipe(new Recipe(
                "Cheese Quesadilla",
                "tortilla:2:pieces|cheese:2:pieces|tomatoes:1:piece",
                "Add cheese and chopped tomatoes to a tortilla. Fold and cook in a pan until the cheese melts."
        ));

        databaseHelper.close();
    }
}