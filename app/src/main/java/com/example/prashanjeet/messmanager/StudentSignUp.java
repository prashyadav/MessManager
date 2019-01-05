package com.example.prashanjeet.messmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUsers = FirebaseDatabase.getInstance().getReference("userMeals");
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
                if(validate()){
                    //progressDialog.dismiss();

                    String id = databaseUsers.push().getKey();
                    String id1 = databaseUserMeals.push().getKey();
                    String studentN = studentName.getText().toString();
                    String studentP = studentPassword.getText().toString();
                    String studentE = studentEmail.getText().toString();
                    final String studentR = studentRoom.getText().toString().trim();
                    final String studentM = studentMob.getText().toString().trim();
                    String studentRegn = studentReg.getText().toString();
                    String studentH = studentHostel.getText().toString();
                    Student user =new Student(studentN,studentM,studentRegn,studentH,studentR,studentE);
                    UserMeal userMeal = new UserMeal(studentN,studentRegn);


                    try {
                        databaseUsers.child(id).setValue(user);
                        databaseUserMeals.child(id1).setValue(userMeal);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(StudentSignUp.this,"Network error please try later",Toast.LENGTH_LONG).show();
                    }

                    SharedPreferences sharedPreferences =getSharedPreferences("myFile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putString("name", studentN);
                    editor.putString("email", studentE);
                    editor.putString("mobile", studentM);
                    editor.putString("mealId",id1);
                    editor.putString("id",id);
                    editor.commit();
                    Toast.makeText(StudentSignUp.this,"Registration Successful",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(StudentSignUp.this,"Registration UnSuccessful",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public Boolean validate(){
        Boolean result = true;
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

}