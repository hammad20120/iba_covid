package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_framelayout, new LoginFragment());

        ft.commit();

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