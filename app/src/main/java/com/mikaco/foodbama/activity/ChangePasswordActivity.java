package com.mikaco.foodbama.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikaco.foodbama.R;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        String title = getIntent().getStringExtra("Title");
        TextView txtTitle = ChangePasswordActivity.this.findViewById(R.id.txtPageTitle);
        txtTitle.setText(String.valueOf(title));

        Button btnBack = ChangePasswordActivity.this.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> finish());

        try {
        } catch (java.lang.Exception exception) {
            exception.printStackTrace();
        }
    }
}
