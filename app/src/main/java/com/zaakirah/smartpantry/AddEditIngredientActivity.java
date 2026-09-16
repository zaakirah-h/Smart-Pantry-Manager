package com.zaakirah.smartpantry;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private Spinner spinnerUnit;
    private EditText etExpiryDate;

    private DatabaseHelper databaseHelper;
    private int editingId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Ingredient");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        String[] units = {
                "Pieces",
                "Grams",
                "Kilograms",
                "Millilitres",
                "Litres"
        };

        ArrayAdapter<String> unitAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        units
                );

        unitAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerUnit.setAdapter(unitAdapter);

        etExpiryDate.setFocusable(false);
        etExpiryDate.setOnClickListener(v -> showDatePicker());

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

            String savedUnit =
                    getIntent().getStringExtra("unit");

            for (int i = 0; i < units.length; i++) {

                if (units[i].equalsIgnoreCase(savedUnit)) {
                    spinnerUnit.setSelection(i);
                    break;
                }
            }

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
        String unit =
                spinnerUnit.getSelectedItem().toString();
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
    private void showDatePicker() {

        java.util.Calendar calendar =
                java.util.Calendar.getInstance();

        android.app.DatePickerDialog datePickerDialog =
                new android.app.DatePickerDialog(
                        this,
                        (view, year, month, dayOfMonth) -> {

                            String selectedDate =
                                    String.format(
                                            java.util.Locale.getDefault(),
                                            "%02d/%02d/%04d",
                                            dayOfMonth,
                                            month + 1,
                                            year
                                    );

                            etExpiryDate.setText(selectedDate);
                        },
                        calendar.get(
                                java.util.Calendar.YEAR
                        ),
                        calendar.get(
                                java.util.Calendar.MONTH
                        ),
                        calendar.get(
                                java.util.Calendar.DAY_OF_MONTH
                        )
                );

        datePickerDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}