package com.zaakirah.smartpantry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeMatcher {

    public static boolean matches(Recipe recipe, List<PantryItem> pantryItems) {

        String[] requiredIngredients = recipe.getIngredients().split("\\|");

        Map<String, PantryItem> pantryMap = new HashMap<>();

        for (PantryItem item : pantryItems) {
            String key = normalizeIngredientName(item.getName());
            pantryMap.put(key, item);
        }

        for (String required : requiredIngredients) {

            String[] parts = required.split(":");

            if (parts.length != 3) {
                return false;
            }

            String requiredName = normalizeIngredientName(parts[0]);
            double requiredQuantity = Double.parseDouble(parts[1]);
            String requiredUnit = normalizeUnit(parts[2]);

            PantryItem pantryItem = pantryMap.get(requiredName);

            if (pantryItem == null) {
                return false;
            }

            double availableQuantity = convertToBaseUnit(
                    pantryItem.getQuantity(),
                    normalizeUnit(pantryItem.getUnit())
            );

            double neededQuantity = convertToBaseUnit(
                    requiredQuantity,
                    requiredUnit
            );

            if (!unitsAreCompatible(
                    normalizeUnit(pantryItem.getUnit()),
                    requiredUnit
            )) {
                return false;
            }

            if (availableQuantity < neededQuantity) {
                return false;
            }
        }

        return true;
    }

    private static String normalizeIngredientName(String name) {

        String normalized = name.trim().toLowerCase();

        if (normalized.endsWith("ies")) {
            normalized = normalized.substring(
                    0, normalized.length() - 3
            ) + "y";
        } else if (normalized.endsWith("oes")) {
            normalized = normalized.substring(
                    0, normalized.length() - 2
            );
        } else if (normalized.endsWith("s")
                && !normalized.endsWith("ss")) {
            normalized = normalized.substring(
                    0, normalized.length() - 1
            );
        }

        return normalized;
    }

    private static String normalizeUnit(String unit) {

        String normalized = unit.trim().toLowerCase();

        switch (normalized) {
            case "piece":
            case "pieces":
            case "pc":
            case "pcs":
                return "piece";

            case "kilogram":
            case "kilograms":
            case "kg":
                return "kg";

            case "gram":
            case "grams":
            case "g":
                return "g";

            case "litre":
            case "litres":
            case "liter":
            case "liters":
            case "l":
                return "l";

            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
            case "ml":
                return "ml";

            default:
                return normalized;
        }
    }

    private static boolean unitsAreCompatible(
            String pantryUnit,
            String recipeUnit) {

        if (pantryUnit.equals(recipeUnit)) {
            return true;
        }

        if ((pantryUnit.equals("kg") && recipeUnit.equals("g"))
                || (pantryUnit.equals("g") && recipeUnit.equals("kg"))) {
            return true;
        }

        if ((pantryUnit.equals("l") && recipeUnit.equals("ml"))
                || (pantryUnit.equals("ml") && recipeUnit.equals("l"))) {
            return true;
        }

        return false;
    }

    private static double convertToBaseUnit(
            double quantity,
            String unit) {

        switch (unit) {

            case "kg":
                return quantity * 1000;

            case "l":
                return quantity * 1000;

            default:
                return quantity;
        }
    }
}
