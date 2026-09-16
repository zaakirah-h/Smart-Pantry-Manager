package com.zaakirah.smartpantry;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Suggested Recipes");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}