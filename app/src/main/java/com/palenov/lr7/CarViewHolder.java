package com.palenov.lr7;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

class CarViewHolder extends RecyclerView.ViewHolder {
    TextView brandTextView, modelTextView, bodyTextView, engineTextView, transmissionTextView;

    CarViewHolder(View itemView) {
        super(itemView);
        brandTextView = itemView.findViewById(R.id.brandTextView);
        modelTextView = itemView.findViewById(R.id.modelTextView);
        bodyTextView = itemView.findViewById(R.id.bodyTextView);
        engineTextView = itemView.findViewById(R.id.engineTextView);
        transmissionTextView = itemView.findViewById(R.id.transmissionTextView);
    }
}
