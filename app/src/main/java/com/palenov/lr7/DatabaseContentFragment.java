package com.palenov.lr7;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DatabaseContentFragment extends Fragment {

    private CarDatabaseHelper dbHelper;
    private TextView databaseContentTextView;

    public DatabaseContentFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new CarDatabaseHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_database_content, container, false);

        databaseContentTextView = view.findViewById(R.id.databaseContentTextView);

        Button displayButton = view.findViewById(R.id.displayButton);
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatabaseContent();
            }
        });


        displayButton.setBackgroundColor(getResources().getColor(R.color.blue));

        return view;
    }

    private void displayDatabaseContent() {
        Cursor cursor = dbHelper.getAllCars();
        StringBuilder databaseContent = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String make = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_BRAND));
                String model = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_MODEL));
                String bodyType = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_BODY_TYPE));
                String engineType = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_ENGINE_TYPE));
                String transmissionType = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_TRANSMISSION_TYPE));

                databaseContent.append("Brand: ").append(make).append("\n");
                databaseContent.append("Model: ").append(model).append("\n");
                databaseContent.append("Body Type: ").append(bodyType).append("\n");
                databaseContent.append("Engine Type: ").append(engineType).append("\n");
                databaseContent.append("Transmission Type: ").append(transmissionType).append("\n\n");

            } while (cursor.moveToNext());

            databaseContentTextView.setText(databaseContent.toString());
            databaseContentTextView.setVisibility(View.VISIBLE);
        } else {
            databaseContentTextView.setText("Database is empty");
            databaseContentTextView.setVisibility(View.VISIBLE);
        }
    }
}