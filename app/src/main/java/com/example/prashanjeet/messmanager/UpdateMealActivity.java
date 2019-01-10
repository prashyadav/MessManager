package com.example.prashanjeet.messmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateMealActivity extends AppCompatActivity {

    private EditText newCost,newDesc;
    private Button updateBtn;
    String ndesc,mealId;
    String  ncost;
    Meal m;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_meal);
        newCost = (EditText)findViewById(R.id.newCost);
        newDesc = (EditText)findViewById(R.id.newDesc);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        Intent intent = getIntent();
        mealId = intent.getStringExtra("meal_id");
        databaseReference = FirebaseDatabase.getInstance().getReference("meals").child(mealId);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ndesc = newDesc.getText().toString();
                ncost = newCost.getText().toString();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        m = dataSnapshot.getValue(Meal.class);
                        m.setDescription(ndesc);
                        m.setExpectedCost(ncost);
                        databaseReference.setValue(m);
                        Toast.makeText(UpdateMealActivity.this,"Updated",Toast.LENGTH_LONG).show();
                        Intent intent =  new Intent(UpdateMealActivity.this,ManagerHome.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(UpdateMealActivity.this, " data base error " , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
