package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvidersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MedOrganization> medOrgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers);

        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoadingDialog();





        getSupportActionBar().setTitle("Medical Facilities");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.server_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retInterface = retrofit.create(RetrofitInterface.class);
        Call<List<MedOrganization>> call = retInterface.getDetails();

        call.enqueue(new Callback<List<MedOrganization>>() {
            @Override
            public void onResponse(Call<List<MedOrganization>> call, Response<List<MedOrganization>> response) {
                loadingDialog.dismissDialog();
                if(response.isSuccessful()){
                   medOrgs = response.body();
                    recyclerView = findViewById(R.id.recyclerView);

                    MyAdapter myAdapter = new MyAdapter(ServiceProvidersActivity.this, medOrgs);

                    recyclerView.setAdapter(myAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ServiceProvidersActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<MedOrganization>> call, Throwable t) {
                Toast.makeText(ServiceProvidersActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}