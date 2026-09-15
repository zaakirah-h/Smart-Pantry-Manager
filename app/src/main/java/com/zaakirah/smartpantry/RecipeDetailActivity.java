package com.zaakirah.smartpantry;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView tvRecipeDetailName =
                findViewById(R.id.tvRecipeDetailName);

        TextView tvRecipeDetailIngredients =
                findViewById(R.id.tvRecipeDetailIngredients);

        TextView tvRecipeDetailSteps =
                findViewById(R.id.tvRecipeDetailSteps);

        String recipeName =
                getIntent().getStringExtra("recipeName");

        String recipeIngredients =
                getIntent().getStringExtra("recipeIngredients");

        String recipeSteps =
                getIntent().getStringExtra("recipeSteps");

        tvRecipeDetailName.setText(recipeName);

        tvRecipeDetailIngredients.setText(
                formatIngredients(recipeIngredients)
        );

        tvRecipeDetailSteps.setText(recipeSteps);
    }

    private String formatIngredients(String ingredients) {

        if (ingredients == null || ingredients.isEmpty()) {
            return "";
        }

        String[] ingredientList = ingredients.split("\\|");

        StringBuilder formatted = new StringBuilder();

        for (String ingredient : ingredientList) {

            String[] parts = ingredient.split(":");

            if (parts.length == 3) {

                String name = parts[0];
                String quantity = parts[1];
                String unit = parts[2];

                formatted.append("• ")
                        .append(name)
                        .append(" - ")
                        .append(quantity)
                        .append(" ")
                        .append(unit)
                        .append("\n");

            } else {

                formatted.append("• ")
                        .append(ingredient)
                        .append("\n");
            }
        }

        return formatted.toString().trim();
    }
}