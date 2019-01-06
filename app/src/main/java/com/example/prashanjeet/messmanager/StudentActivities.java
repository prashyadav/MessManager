package com.example.prashanjeet.messmanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class StudentActivities extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public TextView totalMealsText,balanceText,chooseDate;
    public Button showInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_activities);
        totalMealsText = (TextView)findViewById(R.id.totalMeals);
        balanceText = (TextView)findViewById(R.id.balance);
        chooseDate = (TextView)findViewById(R.id.choosedate);
        showInfo = (Button)findViewById(R.id.showActivity);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker ");

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
    }
}
