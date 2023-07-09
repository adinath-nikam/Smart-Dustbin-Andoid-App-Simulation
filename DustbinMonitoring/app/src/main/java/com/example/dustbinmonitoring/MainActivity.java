package com.example.dustbinmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private BinModel marker_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapInit();
    }

    private void MapInit() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_mapview_id);
        supportMapFragment.getMapAsync(this);
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



        // Fetch Data From Firebase

        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().getReference()
                .child("DustBins")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                        for (DataSnapshot s : dataSnapshot.getChildren()) {
                            marker_data = s.getValue(BinModel.class);
                            final LatLng location = new LatLng(Double.parseDouble(marker_data.Lat), Double.parseDouble(marker_data.Lon));
                            //Toast.makeText(MainActivity.this, location.longitude+" "+location.longitude, Toast.LENGTH_SHORT).show();

                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.dustbin);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);

                            MarkerOptions marker = new MarkerOptions().position(new LatLng(location.latitude, location.longitude))
                                    .snippet("Dustbin id: "+marker_data.getBinId())
                                    .title(marker_data.getBinValue()+"%"+" Filled");
                            map.addMarker(marker).setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                                    return false;
                                }
                            });



//                            map.addMarker(new MarkerOptions().position(location).snippet(marker_data.BinId).title("\u20B9"+marker_data.BinValue)).showInfoWindow();

                            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {

                                }
                            });



                        }

                        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {

                                Toast.makeText(MainActivity.this, marker_data.BinValue, Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });





    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}