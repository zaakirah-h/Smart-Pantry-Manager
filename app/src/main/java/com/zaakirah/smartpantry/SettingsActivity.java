package com.zaakirah.smartpantry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "smart_pantry_settings";
    private static final String EXPIRY_ALERTS = "expiry_alerts";
    private static final String UNIT_PREFERENCE = "unit_preference";

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Switch switchExpiryAlerts =
                findViewById(R.id.switchExpiryAlerts);

        Spinner spinnerUnit =
                findViewById(R.id.spinnerUnit);

        preferences = getSharedPreferences(
                PREFS_NAME,
                MODE_PRIVATE
        );

        String[] units = {
                "Pieces",
                "Grams",
                "Kilograms",
                "Millilitres",
                "Litres"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        units
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerUnit.setAdapter(adapter);

        boolean expiryAlertsEnabled =
                preferences.getBoolean(
                        EXPIRY_ALERTS,
                        true
                );

        switchExpiryAlerts.setChecked(
                expiryAlertsEnabled
        );

        String savedUnit =
                preferences.getString(
                        UNIT_PREFERENCE,
                        "Pieces"
                );

        for (int i = 0; i < units.length; i++) {

            if (units[i].equals(savedUnit)) {
                spinnerUnit.setSelection(i);
                break;
            }
        }

        switchExpiryAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences.edit()
                            .putBoolean(
                                    EXPIRY_ALERTS,
                                    isChecked
                            )
                            .apply();
                }
        );

        spinnerUnit.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            android.view.View view,
                            int position,
                            long id) {

                        preferences.edit()
                                .putString(
                                        UNIT_PREFERENCE,
                                        units[position]
                                )
                                .apply();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}