package com.example.prashanjeet.messmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class FeedBack extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {
    //Firebase firebase;
    public TextView ratingvalue, feedbackmeal, feedbacktext;
    RatingBar rating;
    public TextView mcurrentdate;
    public Button feedbacksubmit;
    public FirebaseUser firebaseAuth;
    private DatabaseReference databaseUserMeals;
    public DatabaseReference databaseReference;
    //int day, month, year;
    String feedbackMeal;
    String feedbackText;
    String currentDate;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("FEEDBACK");
        mcurrentdate = (TextView) findViewById(R.id.FeedbackDate_TextView);
        feedbackmeal = (EditText) findViewById(R.id.feedbackmeal_EditText);
        feedbacktext = (EditText) findViewById(R.id.Feedback_EditText);
        feedbacksubmit = (Button) findViewById(R.id.Feedback_submit_button);
        rating=(RatingBar)findViewById(R.id.Feedback_ratingBar);
        ratingvalue=(TextView)findViewById(R.id.Rating_Value);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingvalue.setText("rating value :"+v);
            }
        });
        mcurrentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"date picker ");
            }
        });
        feedbacksubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    feedbackMeal = feedbackmeal.getText().toString();
                    feedbackText = feedbacktext.getText().toString();
                    value=ratingvalue.getText().toString();
                    Toast.makeText(FeedBack.this, "your data", Toast.LENGTH_SHORT).show();
                    sendUserData();
                    Toast.makeText(FeedBack.this, "your data sended to the server", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("feedback");
        String id = myRef.push().getKey();
        feedbackprofile feedbackpro = new feedbackprofile(feedbackMeal, feedbackText,currentDate,value);
        myRef.child(id).setValue(feedbackpro);
        Intent intent = new Intent(FeedBack.this,ManagerHome.class);
        startActivity(intent);
        finish();

    }
    private Boolean validate(){
        Boolean result = false;

        feedbackMeal = feedbackmeal.getText().toString();
        //password = userPassword.getText().toString();
        //email = userEmail.getText().toString();
        feedbackText = feedbacktext.getText().toString();


        if(feedbackMeal.isEmpty() || feedbackText.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        mcurrentdate.setText(currentDate);
       // SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        //formattedDate = df.format(c.getTime());

    }



}