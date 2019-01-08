package com.example.prashanjeet.messmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentActivities extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public TextView totalMealsText,balanceText,chooseDate,breakFast,lunch,snacks,dinner;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String uid;
    UserMeal meal;
    String formattedDate;
    String value;
    public Button showInfo;
    int date,mon;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_activities);
        totalMealsText = (TextView)findViewById(R.id.totalMeals);
        balanceText = (TextView)findViewById(R.id.balance);
        chooseDate = (TextView)findViewById(R.id.choosedate);
        breakFast = (TextView)findViewById(R.id.breakFast);
        lunch = (TextView)findViewById(R.id.lunch);
        snacks = (TextView)findViewById(R.id.snacks);
        dinner = (TextView)findViewById(R.id.dinner);
        showInfo = (Button)findViewById(R.id.showActivity);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        //uid = firebaseUser.getUid();
        Intent intent = getIntent();
        uid = intent.getStringExtra("mealId");
         FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("userMeals").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                meal = dataSnapshot.getValue(UserMeal.class);
                int v =meal.getBalance();
                value = String.valueOf(v);
                balanceText.setText("Total balance is ::  " + value);
                v = meal.getTotalMeals();
                value = String.valueOf(v);
                totalMealsText.setText("Total Meals taken ::  " + value);

                //Toast.makeText(StudentActivities.this,value,Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker ");

            }
        });
        showInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 date = Integer.parseInt(formattedDate.substring(0,2));
                 mon = Integer.parseInt(formattedDate.substring(3,5));
                date = 31*(mon-1)+date;
                 value = String.valueOf(date);
                Toast.makeText(StudentActivities.this,value,Toast.LENGTH_LONG).show();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        meal = dataSnapshot.getValue(UserMeal.class);
                        int v =meal.list.get(date);
                        value = String.valueOf(v);
                        Toast.makeText(StudentActivities.this,value,Toast.LENGTH_LONG).show();
                        int b=0,l=0,s=0,d=0;
                        b = v&1;
                        l=v&2;
                        s=v&4;
                        d=v&8;
                        if(b!=0){
                            breakFast.setText("Breakfast :: Taken");
                        }
                        else {
                            breakFast.setText("Breakfast :: Not Taken");
                        }
                        if(l!=0){
                            lunch.setText("Lunch :: Taken");
                        }
                        else {
                            lunch.setText("Lunch :: Not Taken");
                        }
                        if(s!=0){
                            snacks.setText("Snacks  ::  Taken");
                        }
                        else {
                            snacks.setText("Snacks ::  Not Taken");
                        }
                        if(d!=0){
                            dinner.setText("Dinner  ::  Taken");
                        }
                        else {
                            dinner.setText("Dinner  ::  Not Taken");
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        chooseDate.setText(currentDate);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
         formattedDate = df.format(c.getTime());

    }
}
