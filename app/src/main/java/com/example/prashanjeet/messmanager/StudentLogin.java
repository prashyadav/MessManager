package com.example.prashanjeet.messmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentLogin extends AppCompatActivity {
    public Button loginStudent,signUpStudent;
    public EditText emailStudent,passwordStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        loginStudent = (Button)findViewById(R.id.loginStudent);
        signUpStudent = (Button)findViewById(R.id.signUpStudent);
        emailStudent = (EditText)findViewById(R.id.emailStudent);
        passwordStudent = (EditText)findViewById(R.id.passwordStudent);
        loginStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(StudentLogin.this,StudentHome.class);
                startActivity(intent);
            }
        });
        signUpStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(StudentLogin.this,StudentSignUp.class);
                startActivity(intent);
            }
        });
    }
}
