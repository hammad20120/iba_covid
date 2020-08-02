package com.example.helloworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {
    private AutoCompleteTextView autoCompleteTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        autoCompleteTextView = getView().findViewById(R.id.register_role_list);

        String roles[] = {"Hospital", "Supplier"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.role_list,roles);

       autoCompleteTextView.setAdapter(adapter);
    }
}
