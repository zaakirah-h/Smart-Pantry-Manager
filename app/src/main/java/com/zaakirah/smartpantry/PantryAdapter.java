package com.zaakirah.smartpantry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryItems;
    private DatabaseHelper databaseHelper;

    public PantryAdapter(Context context, List<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {

        PantryItem item = pantryItems.get(position);

        holder.tvIngredientName.setText(item.getName());

        String details = item.getQuantity() + " " + item.getUnit();

        if (item.getExpiryDate() != null && !item.getExpiryDate().isEmpty()) {
            details += " • Expires: " + item.getExpiryDate();
        }

        holder.tvIngredientDetails.setText(details);

        SharedPreferences preferences =
                holder.itemView.getContext().getSharedPreferences(
                        "smart_pantry_settings",
                        Context.MODE_PRIVATE
                );

        boolean expiryAlertsEnabled =
                preferences.getBoolean(
                        "expiry_alerts",
                        true
                );

        if (expiryAlertsEnabled
                && isExpiringSoon(item.getExpiryDate())) {

            holder.tvExpiryWarning.setText(
                    "Expires soon"
            );

            holder.tvExpiryWarning.setTextColor(
                    android.graphics.Color.RED
            );

            holder.tvExpiryWarning.setVisibility(
                    View.VISIBLE
            );

        } else {

            holder.tvExpiryWarning.setVisibility(
                    View.GONE
            );
        }

        holder.btnEdit.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    AddEditIngredientActivity.class
            );

            intent.putExtra("id", item.getId());
            intent.putExtra("name", item.getName());
            intent.putExtra("quantity", item.getQuantity());
            intent.putExtra("unit", item.getUnit());
            intent.putExtra("expiryDate", item.getExpiryDate());

            v.getContext().startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {

            databaseHelper.deletePantryItem(item.getId());

            pantryItems.remove(holder.getAdapterPosition());

            notifyItemRemoved(holder.getAdapterPosition());

            Toast.makeText(
                    v.getContext(),
                    "Ingredient deleted",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public void updateItems(List<PantryItem> newItems) {
        pantryItems = newItems;
        notifyDataSetChanged();
    }

    private boolean isExpiringSoon(String expiryDate) {

        if (expiryDate == null || expiryDate.isEmpty()) {
            return false;
        }

        try {

            SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            dateFormat.setLenient(false);

            Date expiry = dateFormat.parse(expiryDate);

            if (expiry == null) {
                return false;
            }

            long currentTime = System.currentTimeMillis();

            long threeDaysFromNow =
                    currentTime + (3L * 24 * 60 * 60 * 1000);

            return expiry.getTime() >= currentTime
                    && expiry.getTime() <= threeDaysFromNow;

        } catch (ParseException e) {

            return false;
        }
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView tvIngredientName;
        TextView tvIngredientDetails;
        TextView tvExpiryWarning;
        Button btnEdit;
        Button btnDelete;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIngredientName = itemView.findViewById(R.id.tvIngredientName);
            tvIngredientDetails = itemView.findViewById(R.id.tvIngredientDetails);
            tvExpiryWarning = itemView.findViewById(R.id.tvExpiryWarning);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}