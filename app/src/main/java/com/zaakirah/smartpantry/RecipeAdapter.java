package com.zaakirah.smartpantry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position) {

        Recipe recipe = recipes.get(position);

        holder.tvRecipeName.setText(recipe.getName());

        holder.tvRecipeIngredients.setText(
                formatIngredients(recipe.getIngredients())
        );

        holder.btnViewRecipe.setOnClickListener(v -> {

            Context context = v.getContext();

            Intent intent = new Intent(
                    context,
                    RecipeDetailActivity.class
            );

            intent.putExtra("recipeId", recipe.getId());
            intent.putExtra("recipeName", recipe.getName());
            intent.putExtra("recipeIngredients", recipe.getIngredients());
            intent.putExtra("recipeSteps", recipe.getPreparationSteps());

            context.startActivity(intent);
        });
    }

    private String formatIngredients(String ingredients) {

        if (ingredients == null || ingredients.isEmpty()) {
            return "";
        }

        String[] ingredientList =
                ingredients.split("\\|");

        StringBuilder formatted =
                new StringBuilder("Required ingredients:\n");

        for (String ingredient : ingredientList) {

            String[] parts =
                    ingredient.split(":");

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

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void updateRecipes(List<Recipe> newRecipes) {
        recipes = newRecipes;
        notifyDataSetChanged();
    }

    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvRecipeName;
        TextView tvRecipeIngredients;
        Button btnViewRecipe;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecipeName = itemView.findViewById(
                    R.id.tvRecipeName
            );

            tvRecipeIngredients = itemView.findViewById(
                    R.id.tvRecipeIngredients
            );

            btnViewRecipe = itemView.findViewById(
                    R.id.btnViewRecipe
            );
        }
    }
}