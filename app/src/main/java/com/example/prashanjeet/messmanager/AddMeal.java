package com.example.prashanjeet.messmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMeal extends AppCompatActivity {

    Button add;
    EditText title,cost,des,date;
    public DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        add = (Button) findViewById(R.id.add_meal);
        databaseUsers = FirebaseDatabase.getInstance().getReference("meals");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = databaseUsers.push().getKey();
                String tit = title.getText().toString();
                String desc = des.getText().toString();
                String co = cost.getText().toString();
                String d = date.getText().toString();
                Meal meal = new Meal(tit,desc,co,d);

                try {
                    databaseUsers.child(id).setValue(meal);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AddMeal.this,"Network error please try later",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
g