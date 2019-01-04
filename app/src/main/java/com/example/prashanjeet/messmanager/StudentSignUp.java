package com.example.prashanjeet.messmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentSignUp extends AppCompatActivity {
    private Button studentSignUp;
    public EditText studentName,studentEmail,studentPassword,studentHostel,studentRoom,studentMob,studentReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        studentSignUp = (Button)findViewById(R.id.studentSignUp);
        studentName = (EditText)findViewById(R.id.studentName);
        studentEmail = (EditText)findViewById(R.id.studentEmail);
        studentPassword = (EditText)findViewById(R.id.studentPassword);
        studentHostel = (EditText)findViewById(R.id.studentHostel);
        studentRoom = (EditText)findViewById(R.id.studentRoom);
        studentMob = (EditText)findViewById(R.id.studentMob);
        studentReg = (EditText)findViewById(R.id.studentRegNo);
        studentSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}