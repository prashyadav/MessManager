package com.example.prashanjeet.messmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentSignUp extends AppCompatActivity {
    private Button studentSignUp;
    public ProgressDialog progressDialog;
    public FirebaseAuth firebaseAuth;
    private DatabaseReference databaseUserMeals;
    public DatabaseReference databaseUsers;
    public EditText studentName,studentEmail,studentPassword,studentHostel,studentRoom,studentMob,studentReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUserMeals = FirebaseDatabase.getInstance().getReference("userMeals");
        studentSignUp = (Button)findViewById(R.id.studentSignUp);
        studentName = (EditText)findViewById(R.id.studentNam);
        studentEmail = (EditText)findViewById(R.id.studentEmail);
        studentPassword = (EditText)findViewById(R.id.studentPassword);
        studentHostel = (EditText)findViewById(R.id.studentHost);
        studentRoom = (EditText)findViewById(R.id.studentRoom);
        studentMob = (EditText)findViewById(R.id.studentMob);
        studentReg = (EditText)findViewById(R.id.studentRegNo);
        progressDialog = new ProgressDialog(StudentSignUp.this);
        studentSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    final  String studentN = studentName.getText().toString();
                    final String studentP = studentPassword.getText().toString().trim();
                    final String studentE = studentEmail.getText().toString().trim();
                    final String studentR = studentRoom.getText().toString().trim();
                    final String studentM = studentMob.getText().toString().trim();
                    final String studentRegn = studentReg.getText().toString();
                    final String studentH = studentHostel.getText().toString();
                    firebaseAuth.createUserWithEmailAndPassword(studentE,studentP).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                   //progressDialog.dismiss();
                                    String id = firebaseAuth.getCurrentUser().getUid();
                                    String id1 = databaseUserMeals.push().getKey();
                                    UserMeal userMeal = new UserMeal(studentN,studentRegn);
                                    Student user =new Student(studentN,studentM,studentRegn,studentH,studentR,studentE,id,id1,"pending");
                                    try {
                                        databaseUsers.child(id).setValue(user);
                                        databaseUserMeals.child(id).setValue(userMeal);
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                        Toast.makeText(StudentSignUp.this,"Network error please try later",Toast.LENGTH_LONG).show();
                                    }

                                    SharedPreferences sharedPreferences =getSharedPreferences("myFile", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor =sharedPreferences.edit();
                                    editor.putString("name", studentN);
                                    editor.putString("id", id);
                                    editor.putString("mealId",id1);
                                    editor.putString("email", studentE);
                                    editor.putString("mobile", studentM);
                                    editor.commit();
                                    sendEmailVerification();

                                }
                                else{
                                    //progressDialog.dismiss();
                                    Toast.makeText(StudentSignUp.this,"Registration Failed",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                }
                else
                {
                    Toast.makeText(StudentSignUp.this,"Fill all details",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public Boolean validate(){
        Boolean result = false;
        String studentN = studentName.getText().toString();
        String studentP = studentPassword.getText().toString();
        String studentE = studentEmail.getText().toString();
        final String studentR = studentRoom.getText().toString().trim();
        final String studentM = studentMob.getText().toString().trim();
        String studentRegn = studentReg.getText().toString();
        String studentH = studentHostel.getText().toString();
        if(studentN.isEmpty()||studentP.isEmpty()||studentE.isEmpty()|| studentR.isEmpty() || studentM.isEmpty()|| studentRegn.isEmpty()|| studentH.isEmpty())
        {
            Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String email2 = firebaseUser.getEmail();
        Toast.makeText(StudentSignUp.this,email2,Toast.LENGTH_SHORT).show();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(StudentSignUp.this,"Successfully Registered and Email send !!",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        Intent intent = new Intent(StudentSignUp.this,MainActivity.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(StudentSignUp.this,"Verification mail not send ",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}