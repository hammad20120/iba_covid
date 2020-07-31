package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    private CardView medicalCard;
    private CardView supplierCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().hide();

        medicalCard = (CardView) findViewById(R.id.medicalCard);
        medicalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServiceProviderActivity(true);
            }
        });

        supplierCard = (CardView) findViewById(R.id.supplierCard);
        supplierCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServiceProviderActivity(false);
            }
        });

    }

    public void openServiceProviderActivity(boolean isMedical){
        Intent intent =  new Intent(this, ServiceProvidersActivity.class);
        startActivity(intent);
    }
}