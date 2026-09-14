package com.zaakirah.smartpantry;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etExpiryDate;

    private DatabaseHelper databaseHelper;
    private int editingId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        Button btnSaveIngredient = findViewById(R.id.btnSaveIngredient);

        databaseHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("id")) {

            editingId = getIntent().getIntExtra("id", -1);

            etIngredientName.setText(
                    getIntent().getStringExtra("name")
            );

            etQuantity.setText(
                    String.valueOf(
                            getIntent().getDoubleExtra("quantity", 0)
                    )
            );

            etUnit.setText(
                    getIntent().getStringExtra("unit")
            );

            etExpiryDate.setText(
                    getIntent().getStringExtra("expiryDate")
            );

            btnSaveIngredient.setText("Update Ingredient");
        }

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {

        String name = etIngredientName.getText().toString().trim();
        String quantityText = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etIngredientName.setError("Enter an ingredient name");
            etIngredientName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(quantityText)) {
            etQuantity.setError("Enter a quantity");
            etQuantity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(unit)) {
            etUnit.setError("Enter a unit");
            etUnit.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            etQuantity.setError("Enter a valid quantity");
            etQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {
            etQuantity.setError("Quantity must be greater than 0");
            etQuantity.requestFocus();
            return;
        }

        PantryItem item;

        if (editingId != -1) {

            item = new PantryItem(
                    editingId,
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            databaseHelper.updatePantryItem(item);

            Toast.makeText(
                    this,
                    "Ingredient updated",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            item = new PantryItem(
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            databaseHelper.addPantryItem(item);

            Toast.makeText(
                    this,
                    "Ingredient added",
                    Toast.LENGTH_SHORT
            ).show();
        }

        finish();
    }
}