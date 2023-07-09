package com.example.virtualdustbin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    Button Bt_Done;

    String BinValue, BinId;
    String latitude, longitude;
    BinModel binModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        BinValue = getIntent().getStringExtra("BinValue");
        BinId = getIntent().getStringExtra("BinId");

        ToolBar();

        Bt_Done = findViewById(R.id.BT_Select_Loc_Id2);


        MapInit();





    }



    private void ToolBar() {
        Toolbar toolbar = findViewById(R.id.TB_Maps_Id);
        toolbar.setTitle("Select Location");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setTiltGesturesEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.bin);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);

                map.clear();

                MarkerOptions marker = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("New Marker");
                map.addMarker(marker).setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


                latitude = Double.toString(latLng.latitude);
                longitude = Double.toString(latLng.longitude);

                binModel = new BinModel(BinId, BinValue, latitude, longitude);

                Bt_Done.setVisibility(View.VISIBLE);



                Toast.makeText(Maps.this, latLng.latitude+" "+latLng.longitude, Toast.LENGTH_SHORT).show();
            }
        });



        findViewById(R.id.BT_Select_Loc_Id2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("DustBins").child(BinId);
                databaseReference.setValue(binModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(Maps.this, MainActivity.class));
                    }
                });
            }
        });





    }

    private void MapInit() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_mapview_id);
        supportMapFragment.getMapAsync(this);
    }





}

