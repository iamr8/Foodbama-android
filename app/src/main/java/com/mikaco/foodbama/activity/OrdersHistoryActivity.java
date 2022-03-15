package com.mikaco.foodbama.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikaco.foodbama.R;

public class OrdersHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);


        String title = getIntent().getStringExtra("Title");
        TextView txtTitle = OrdersHistoryActivity.this.findViewById(R.id.txtPageTitle);
        txtTitle.setText(String.valueOf(title));

        Button btnBack = OrdersHistoryActivity.this.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> finish());
    }
}
