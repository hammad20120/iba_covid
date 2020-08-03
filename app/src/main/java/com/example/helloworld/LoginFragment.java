package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    private Button loginButton;

    @Override
    public void onStart() {
        super.onStart();

        SessionManagement sessionManagement = new SessionManagement(getContext());
        User user = sessionManagement.getSession();

        if(user != null){
            openDashboardActivity();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_layout, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView registerText = (TextView) view.findViewById(R.id.registerText);
        loginButton = view.findViewById(R.id.loginbtn);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_framelayout, new RegisterFragment());

                ft.commit();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser(){
        EditText email = getView().findViewById(R.id.login_email);
        EditText password = getView().findViewById(R.id.login_password);

        User user = new User(null,email.getText().toString(), password.getText().toString(), -1);

        loginServerRequest(user);


    }

    public void loginServerRequest(User user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.server_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retInterface = retrofit.create(RetrofitInterface.class);
        Call<ResponseBody> call = retInterface.login(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                   serverSuccessResponse(response);

                }else{
                    try{
                        JSONObject json = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), json.get("message").toString(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ignore){

                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("LoginF", t.toString());
            }
        });
    }

    public void serverSuccessResponse( Response<ResponseBody> response){
        try{
            JSONObject json = new JSONObject(response.body().string());
            JSONObject userObj = json.getJSONObject("user");
            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();

            User user = new User(userObj.get("id").toString(),userObj.get("name").toString(),
                    userObj.getInt("role"), json.getString("token"));



            SessionManagement sessionManagement = new SessionManagement(getContext());
            sessionManagement.saveSession(user);
            openDashboardActivity();

        }catch (Exception ignore){

        }
    }


    public void openDashboardActivity() {
        Intent intent = new Intent(getContext(), DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
