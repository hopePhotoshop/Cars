package com.palenov.lr7;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    private CarDatabaseHelper dbHelper;
    private Spinner bodySpinner, engineSpinner, transmissionSpinner;
    private Button searchButton;
    private TextView databaseContentTextView;

    public SearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new CarDatabaseHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        bodySpinner = view.findViewById(R.id.bodySpinner);
        engineSpinner = view.findViewById(R.id.engineSpinner);
        transmissionSpinner = view.findViewById(R.id.transmissionSpinner);
        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
        databaseContentTextView = view.findViewById(R.id.databaseContentTextView);

        setupSpinners();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDatabase();
            }
        });

        return view;
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> bodyAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.body_types, android.R.layout.simple_spinner_item
        );
        bodyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bodySpinner.setAdapter(bodyAdapter);

        ArrayAdapter<CharSequence> engineAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.engine_types, android.R.layout.simple_spinner_item
        );
        engineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        engineSpinner.setAdapter(engineAdapter);

        ArrayAdapter<CharSequence> transmissionAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.transmission_types, android.R.layout.simple_spinner_item
        );
        transmissionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transmissionSpinner.setAdapter(transmissionAdapter);
    }

    private void searchDatabase() {
        String selectedBody = bodySpinner.getSelectedItem().toString();
        String selectedEngine = engineSpinner.getSelectedItem().toString();
        String selectedTransmission = transmissionSpinner.getSelectedItem().toString();

        Cursor cursor = dbHelper.searchCars(selectedBody, selectedEngine, selectedTransmission);

        StringBuilder databaseContent = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String brand = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_BRAND));
                String model = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_MODEL));
                String bodyType = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_BODY_TYPE));
                String engineType = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_ENGINE_TYPE));
                String transmissionType = cursor.getString(cursor.getColumnIndexOrThrow(CarDatabaseHelper.COLUMN_TRANSMISSION_TYPE));

                String carInfo = "Brand: " + brand + "\n" +
                        "Model: " + model + "\n" +
                        "Body Type: " + bodyType + "\n" +
                        "Engine Type: " + engineType + "\n" +
                        "Transmission Type: " + transmissionType + "\n\n";

                if (!databaseContent.toString().contains(carInfo)) {
                    databaseContent.append(carInfo);
                }
            } while (cursor.moveToNext());

            if (TextUtils.isEmpty(databaseContent)) {
                showDatabaseContentDialog("No matching records found");
            } else {
                showDatabaseContentDialog(databaseContent.toString());
            }

            databaseContentTextView.setVisibility(View.VISIBLE);
        } else {
            showDatabaseContentDialog("No matching records found");
            databaseContentTextView.setVisibility(View.VISIBLE);
        }
    }

    private void showDatabaseContentDialog(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Search Results");
        builder.setMessage(content);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
