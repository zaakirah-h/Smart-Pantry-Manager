package com.zaakirah.smartpantry;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecipeAdapter recipeAdapter;
    private TextView tvNoMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        databaseHelper = new DatabaseHelper(this);

        RecyclerView recyclerRecipes =
                findViewById(R.id.recyclerRecipes);

        tvNoMatches =
                findViewById(R.id.tvNoMatches);

        recipeAdapter =
                new RecipeAdapter(new ArrayList<>());

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerRecipes.setAdapter(recipeAdapter);

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        List<Recipe> allRecipes =
                databaseHelper.getAllRecipes();

        List<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        List<Recipe> matchingRecipes =
                new ArrayList<>();

        for (Recipe recipe : allRecipes) {

            if (RecipeMatcher.matches(
                    recipe,
                    pantryItems)) {

                matchingRecipes.add(recipe);
            }
        }

        recipeAdapter.updateRecipes(matchingRecipes);

        if (matchingRecipes.isEmpty()) {

            tvNoMatches.setVisibility(
                    TextView.VISIBLE
            );

        } else {

            tvNoMatches.setVisibility(
                    TextView.GONE
            );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null
                && recipeAdapter != null) {

            loadSuggestedRecipes();
        }
    }
}