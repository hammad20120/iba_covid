package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OrganizationDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;
    private TableLayout mTableLayout;
    private LinearLayout mLinearLayout;
    private MedOrganization medOrg;
    private TextView orgName;
    private Cordinates cordinates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_detail);

        Intent intent = getIntent();
        medOrg = intent.getParcelableExtra("org_details");
        Items medItems[] = medOrg.getItems();
        cordinates= medOrg.getCordinates();

        orgName = (TextView) findViewById(R.id.organizationName);
        orgName.setText(medOrg.getName());

        mMapView=findViewById(R.id.mapView3);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mLinearLayout = (LinearLayout) findViewById(R.id.scrollVewLinearLayout);
        for(int i = 0; i <medItems.length; i++){
            TableRow mTableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_org_detail,null);

            TextView tv;
            tv = (TextView) mTableRow.findViewById(R.id.itemName);
            tv.setText(medItems[i].getName());

            tv = (TextView) mTableRow.findViewById(R.id.itemQty);
            tv.setText(String.valueOf(medItems[i].getQty()));

            tv = (TextView) mTableRow.findViewById(R.id.itemReq);
            if(medItems[i].getQty() > medItems[i].getBorderLineQty()) {
                tv.setText("No");
                tv.setTextColor(Color.GREEN);
            }else{
                tv.setText("Yes");
                tv.setTextColor(getResources().getColor(R.color.red));
            }


            mLinearLayout.addView(mTableRow);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng myLatLng= new LatLng(cordinates.getLatitude(), cordinates.getLongitude());
        MarkerOptions mOpts = new MarkerOptions();
        mOpts.position(myLatLng);

        googleMap.addMarker(mOpts);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng,16) );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}