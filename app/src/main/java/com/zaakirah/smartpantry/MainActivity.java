package com.zaakirah.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private PantryAdapter pantryAdapter;
    private List<PantryItem> pantryItems;
    private TextView tvEmptyPantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Smart Pantry Manager");
        }

        databaseHelper = new DatabaseHelper(this);
        RecipeSeeder.seedRecipes(this);

        RecyclerView recyclerPantry =
                findViewById(R.id.recyclerPantry);

        tvEmptyPantry =
                findViewById(R.id.tvEmptyPantry);

        Button btnAddIngredient =
                findViewById(R.id.btnAddIngredient);

        pantryItems = new ArrayList<>();

        pantryAdapter =
                new PantryAdapter(this, pantryItems);

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerPantry.setAdapter(pantryAdapter);

        btnAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null
                && pantryAdapter != null) {

            List<PantryItem> items =
                    databaseHelper.getAllPantryItems();

            pantryAdapter.updateItems(items);

            if (items.isEmpty()) {

                tvEmptyPantry.setVisibility(
                        android.view.View.VISIBLE
                );

            } else {

                tvEmptyPantry.setVisibility(
                        android.view.View.GONE
                );
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_suggested_recipes) {

            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.menu_settings) {

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}