package com.palenov.lr7;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class CarDialog {

    public static void showCarInfoDialog(Context context, Cursor cursor, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        View dialogView = inflater.inflate(R.layout.car_info_dialog, null);
        builder.setView(dialogView);

        TextView carInfoTextView = dialogView.findViewById(R.id.carInfoTextView);
        Button closeButton = dialogView.findViewById(R.id.closeButton);
        ScrollView scrollView = dialogView.findViewById(R.id.scrollView);

        if (cursor != null && cursor.moveToPosition(position)) {
            String brand = cursor.getString(cursor.getColumnIndexOrThrow("brand"));
            String model = cursor.getString(cursor.getColumnIndexOrThrow("model"));
            String bodyType = cursor.getString(cursor.getColumnIndexOrThrow("body_type"));
            String engineType = cursor.getString(cursor.getColumnIndexOrThrow("engine_type"));
            String transmissionType = cursor.getString(cursor.getColumnIndexOrThrow("transmission_type"));

            String carInfo = "Brand: " + brand + "\n" +
                    "Model: " + model + "\n" +
                    "Body Type: " + bodyType + "\n" +
                    "Engine Type: " + engineType + "\n" +
                    "Transmission Type: " + transmissionType;

            carInfoTextView.setText(carInfo);
        }

        AlertDialog dialog = builder.create();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });

        dialog.show();
    }
}
