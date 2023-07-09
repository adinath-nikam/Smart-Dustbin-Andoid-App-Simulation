package com.example.virtualdustbin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseRecyclerOptions<BinModel> options =
                new FirebaseRecyclerOptions.Builder<BinModel>()
                        .setQuery(FirebaseInitializtion.firebaseDatabase.getReference().child("CLG_NAME").child("DRIVERS")
                                , BinModel.class)
                        .build();

    }
}