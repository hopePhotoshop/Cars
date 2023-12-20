package com.palenov.lr7;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Cursor cursor;

    public CarAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public String getCarInfoAtPosition(int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            String brand = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_BRAND));
            String model = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_MODEL));
            String bodyType = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_BODY_TYPE));
            String engineType = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_ENGINE_TYPE));
            String transmissionType = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_TRANSMISSION_TYPE));

            return String.format("Brand: %s\nModel: %s\nBody: %s\nEngine: %s\nTransmission: %s",
                    brand, model, bodyType, engineType, transmissionType);
        }
        return "";
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            String brand = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_BRAND));
            String model = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_MODEL));
            holder.textView.setText(String.format("%s %s", brand, model));
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.carTextView);
        }
    }
}
