package com.zaakirah.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private PantryAdapter pantryAdapter;
    private List<PantryItem> pantryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        RecyclerView recyclerPantry = findViewById(R.id.recyclerPantry);
        Button btnAddIngredient = findViewById(R.id.btnAddIngredient);

        pantryItems = new ArrayList<>();

        pantryAdapter = new PantryAdapter(this, pantryItems);

        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));
        recyclerPantry.setAdapter(pantryAdapter);

        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null && pantryAdapter != null) {
            List<PantryItem> items = databaseHelper.getAllPantryItems();
            pantryAdapter.updateItems(items);
        }
    }
}