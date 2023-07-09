package com.example.virtualdustbin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;



public class MainActivity extends AppCompatActivity {

//    TextView TV_Bin_Value;
//    int progressValue = 0;

    RecyclerView RV_Drivers_List;
    BinModel binModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RV_Drivers_List = findViewById(R.id.RV_Dustbins_List);
        RV_Drivers_List.setLayoutManager(new LinearLayoutManager(this));

//        FetchData();

        findViewById(R.id.FAB_Add_Dustbin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this, AddDustbin.class));
            }
        });
    }

    private void FetchData() {
        FirebaseRecyclerOptions<BinModel> options =
                new FirebaseRecyclerOptions.Builder<BinModel>()
                        .setQuery(FirebaseInitializtion.firebaseDatabase.getReference().child("CLG_NAME").child("DRIVERS")
                                , BinModel.class)
                        .build();

    }


}