package com.example.helloworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class LoadingDialog {
    Activity activity;
    AlertDialog dialog;

    LoadingDialog(Activity myactivity){
        activity = myactivity;
    }

    void startLoadingDialog(String prompt){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View customView = inflater.inflate(R.layout.loading_dialog,null);
        TextView tv =  (TextView)customView.findViewById(R.id.loading_dialog_text);
        tv.setText(prompt);
        builder.setView(customView);
        builder.setCancelable(false);


        dialog = builder.create();
        dialog.show();
    }

    void dismissDialog(){
        dialog.dismiss();
    }

}
