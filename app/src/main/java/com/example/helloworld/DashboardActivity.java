package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    private CardView medicalCard;
    private CardView supplierCard;
    private CardView orgCard;
    private CardView logoutCard;
    private SessionManagement sessionManagement;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sessionManagement = new SessionManagement(this);
        user = sessionManagement.getSession();


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

        logoutCard = (CardView) findViewById(R.id.logoutCard);
        logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManagement.removeSession();
                openLandingPage();
            }
        });

        orgCard = (CardView) findViewById((R.id.manageOrgsCard));
        orgCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openManageOrgActivity();
            }
        });

    }

    public void openManageOrgActivity(){
        Intent intent =  new Intent(this, ManageOrganiztionActivity.class);
        startActivity(intent);
    }

    public void openLandingPage(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openServiceProviderActivity(boolean isMedical){
        Intent intent =  new Intent(this, ServiceProvidersActivity.class);
        startActivity(intent);
    }
}