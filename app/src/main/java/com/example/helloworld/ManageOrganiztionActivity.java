package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageOrganiztionActivity extends AppCompatActivity {
    private Button addRowBtn;
    private Button submitBtn;
    private int rowCount;
    private LinearLayout linearLayout;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_organiztion);
        linearLayout = findViewById(R.id.itemInsertLayout);
        loadingDialog = new LoadingDialog(this);

        rowCount = 0;

        addRowBtn = (Button) findViewById(R.id.item_add_row);
        addRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout mLinearLayout = (LinearLayout) getLayoutInflater()
                        .inflate(R.layout.item_input_row, null);

                linearLayout.addView(mLinearLayout);

                mLinearLayout.findViewById(R.id.item_name).setTag("ItemNameInput" + rowCount);
                mLinearLayout.findViewById(R.id.item_count).setTag("ItemCountInput" + rowCount);
                mLinearLayout.findViewById(R.id.item_min_count).setTag("ItemMinCountInput" + rowCount);
                rowCount++;
            }
        });

        submitBtn = (Button) findViewById(R.id.btn_submit_item);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addOrganization();
            }
        });
    }


    public void addOrganization() {
        loadingDialog.startLoadingDialog("Adding Organization...");
        MedOrganization medOrganization = null;
        try {
            medOrganization = createOrganization();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.server_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retInterface = retrofit.create(RetrofitInterface.class);
        Call<ResponseBody> call = retInterface.addOrganization(medOrganization);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        Toast.makeText(getBaseContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                        openDashboardActivity();
                    } catch (Exception ignore) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loadingDialog.dismissDialog();
            }
        });

    }

    public MedOrganization createOrganization() throws IOException {
        EditText orgName_field = findViewById(R.id.org_name);
        String orgName = orgName_field.getText().toString();

        Items[] items = new Items[rowCount];
        for (int i = 0; i < rowCount; i++) {
            EditText itemName_field = linearLayout.findViewWithTag("ItemNameInput" + i);
            EditText itemCount_field = linearLayout.findViewWithTag("ItemCountInput" + i);
            EditText itemMinCount_field = linearLayout.findViewWithTag("ItemMinCountInput" + i);

            String itemName = itemName_field.getText().toString();
            int itemCount = Integer.parseInt(itemCount_field.getText().toString());
            int itemMinCount = Integer.parseInt(itemMinCount_field.getText().toString());

            items[i] = new Items(itemName, itemCount, itemMinCount);
        }

        Geocoder geocoder = new Geocoder(this, Locale.US);

        List<Address> address = geocoder.getFromLocationName(orgName, 1);
        double lat, lng;
        if(address.size() == 0){
            lat = 25.1921465;
            lng= 66.5949955;
        }else{
            lat = address.get(0).getLatitude();
            lng = address.get(0).getLongitude();
        }


        MedOrganization medOrg = new MedOrganization(orgName, items,
                new Cordinates(lat, lng));

        return medOrg;
    }

    public void openDashboardActivity() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}