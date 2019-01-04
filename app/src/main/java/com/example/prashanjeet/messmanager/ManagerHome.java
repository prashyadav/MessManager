package com.example.prashanjeet.messmanager;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ManagerHome extends AppCompatActivity {

    FloatingActionButton fabMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        fabMeal = (FloatingActionButton) findViewById(R.id.fab_add);
        fabMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
