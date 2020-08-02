package com.example.helloworld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {
    private AutoCompleteTextView autoCompleteTextView;
    private Button registerBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView loginText = (TextView) view.findViewById(R.id.loginText);
        autoCompleteTextView = getView().findViewById(R.id.register_role_list);
        registerBtn = (Button) getView().findViewById(R.id.register_btn);

        String[] roles = {"Hospital", "Supplier"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.role_list,roles);

        autoCompleteTextView.setAdapter(adapter);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToLogin();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser(){
        EditText name = getView().findViewById(R.id.register_name);
        EditText email = getView().findViewById(R.id.register_email);
        EditText password = getView().findViewById(R.id.register_password);
        AutoCompleteTextView role = getView().findViewById(R.id.register_role_list);

        String req_name = name.getText().toString();
        String req_email = email.getText().toString();
        String req_password = password.getText().toString();
        int req_role;

        if(role.getText().equals("Hospital")){
            req_role = 1;
        }else{
            req_role = 2;
        }

        User user = new User(req_name,req_email,req_password,req_role);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.server_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retInterface = retrofit.create(RetrofitInterface.class);
        Call<ResponseBody> call = retInterface.register(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        Toast.makeText(getContext(), json.get("message").toString(),Toast.LENGTH_SHORT).show();
                        switchToLogin();
                        name.setText("");
                        email.setText("");
                        password.setText("");
                        role.setListSelection(-1);
                    }catch (Exception ignored){

                    }
                }else{
                    try {
                        JSONObject json = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), json.get("message").toString(),Toast.LENGTH_SHORT).show();
                    }catch (Exception ignored){

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Registerfragment", t.getMessage());
            }
        });

    }

    private void switchToLogin(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_framelayout, new LoginFragment());
        ft.commit();
    }
}
