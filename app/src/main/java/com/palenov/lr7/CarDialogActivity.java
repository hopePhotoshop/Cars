package com.palenov.lr7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CarDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_dialog);


        String carInfo = getIntent().getStringExtra("carInfo");


        TextView carInfoTextView = findViewById(R.id.carInfoTextView);
        carInfoTextView.setText(carInfo);


        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}