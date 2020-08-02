package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
    private Button btnLst;
    private Button btnDash;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLst = (Button)findViewById(R.id.button);
        btnLst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServiceProviderActivity();
            }
        });

        btnDash = (Button)findViewById(R.id.button2);
        btnDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDashboardActivity();
            }
        });

        btnLogin = (Button)findViewById(R.id.btn_login);
        btnDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDashboardActivity();
            }
        });

    }

    public void openDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public void openServiceProviderActivity(){
        Intent intent =  new Intent(this, ServiceProvidersActivity.class);
        startActivity(intent);
    }
}