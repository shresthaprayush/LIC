package com.example.lic.Main.main;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.lic.R;

import androidx.annotation.Nullable;

public class OnlinePopup extends Activity {

    ImageButton delivered, undelivered, exit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.online_popup);

        delivered = findViewById(R.id.buttonOk1);

        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity2();

            }
        });



        undelivered = findViewById(R.id.buttonOk2);

        undelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity3();

            }
        });

        exit = findViewById(R.id.buttonOk3);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height));

    }

    private void openActivity3() {
        Intent intent = new Intent(this, Online_Undelievered.class);
        startActivity(intent);
    }

    public void openActivity2(){
        Intent intent = new Intent(this, Online_Delievered.class);
        startActivity(intent);

    }
}
