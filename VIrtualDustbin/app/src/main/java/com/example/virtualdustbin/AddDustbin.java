package com.example.virtualdustbin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddDustbin extends AppCompatActivity {


    TextView TV_Bin_Value;
    int progressValue = 0;

    String BinValue, BinId;
    EditText ETBinId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dustbin);

        ToolBar();

        TV_Bin_Value = (TextView) findViewById(R.id.TV_Bin_Value_Id);

        ETBinId = findViewById(R.id.ET_Bin_Id);

        SeekBar simpleSeekBar=(SeekBar) findViewById(R.id.SB_Garbage_Id); // initiate the Seekbar
        simpleSeekBar.setMax(100);

        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {



            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressValue = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                TV_Bin_Value.setText(Integer.toString(progressValue)+"%");
                BinValue = Integer.toString(progressValue);
            }
        });


        findViewById(R.id.BT_Select_Loc_Id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BinId = ETBinId.getText().toString();

                Intent intent = new Intent(AddDustbin.this, Maps.class);

                intent.putExtra("BinId", BinId);
                intent.putExtra("BinValue", BinValue);
                startActivity(intent);
            }
        });

    }

    private void ToolBar() {
        Toolbar toolbar = findViewById(R.id.TB_Add_Dustbin_Id);
        toolbar.setTitle("Add Dustbin");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
    }
}