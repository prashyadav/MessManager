package com.example.prashanjeet.messmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentLogin extends AppCompatActivity {
    public Button loginStudent,signUpStudent;
    public EditText emailStudent,passwordStudent;
   private  FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        loginStudent = (Button)findViewById(R.id.loginStudent);
        signUpStudent = (Button)findViewById(R.id.signUpStudent);
        emailStudent = (EditText)findViewById(R.id.emailStudent);
        passwordStudent = (EditText)findViewById(R.id.passwordStudent);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(StudentLogin.this);
        loginStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate1()){
                    validate(emailStudent.getText().toString(),passwordStudent.getText().toString());
                }
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
    public Boolean validate1(){
        Boolean result = false;
        String name = emailStudent.getText().toString();
        String passWord = passwordStudent.getText().toString();
        // String userEmail = user_name.getText().toString();
        if(name.isEmpty()||passWord.isEmpty())
        {
            Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }
    private void validate(String userEmail,String userPassword){

        progressDialog.setMessage("Checking your credintials!!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    //Toast.makeText(MainActivity.this, "Login Successful   !!", Toast.LENGTH_SHORT).show();
                    // Intent intent = new Intent(MainActivity.this,Second.class);
                    //   startActivity(intent);
                    checkEmailVerification();

                }
                else{

                    Toast.makeText(StudentLogin.this, "Login failed !!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }


    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if(emailflag)
        {

            Toast.makeText(StudentLogin.this, "Login Successful   !!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentLogin.this,StudentHome.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(StudentLogin.this,"Please verify email!!",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }


    }
}
