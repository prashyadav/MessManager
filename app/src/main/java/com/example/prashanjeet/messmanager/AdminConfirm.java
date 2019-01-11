package com.example.prashanjeet.messmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminConfirm extends AppCompatActivity {
    private TextView studentN,studentReg,studentRoom,studentH;
    private Button acceptBtn,rejectBtn;
    private Student student;
    String uid;
    public String sname,reg,room,hostel,mobileN,email,mealId;
    public DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_confirm);
        studentN = (TextView)findViewById(R.id.studentNam);
        studentReg = (TextView)findViewById(R.id.studentRegn1);
        studentRoom = (TextView)findViewById(R.id.studentRoom);
        studentH = (TextView)findViewById(R.id.studentHost);
        acceptBtn = (Button)findViewById(R.id.acceptBtn);
        rejectBtn = (Button)findViewById(R.id.rejectBtn);
        progressDialog = new ProgressDialog(AdminConfirm.this);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Intent intent = getIntent();
        uid = intent.getStringExtra("student_uid");
       // Toast.makeText(AdminConfirm.this,uid,Toast.LENGTH_LONG).show();

        //final FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        //emailflag = firebaseUser.isEmailVerified();
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(uid);

           databaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   try {
                   student = dataSnapshot.getValue(Student.class);

                   sname = student.getName();
                   studentN.setText("Name :: "+sname);
                   reg = student.getRegNumber();
                   studentReg.setText("Reg No. :: "+reg);
                   room = student.getRoomNo();
                   studentRoom.setText("Room No. :: "+room);
                   hostel = student.getHostel();
                   studentH.setText("Hostel  ::  "+hostel);
                   mobileN = student.getMobile();
                   email = student.getEmail();
                   mealId = student.getMealId();
               }
            catch (Exception e){
                   e.printStackTrace();
               }

               }

               @Override
               public void onCancelled(DatabaseError databaseError) {
                   Toast.makeText(AdminConfirm.this, " data base error ", Toast.LENGTH_SHORT).show();
               }
           });



        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setStatus("confirm");
                databaseReference.setValue(student);
                Toast.makeText(AdminConfirm.this,"Confirmed",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminConfirm.this,AdminVerify.class);
                startActivity(intent);
                finish();
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.removeValue();
                Toast.makeText(AdminConfirm.this,"Rejected ",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminConfirm.this,AdminVerify.class);
                startActivity(intent);
                finish();
            }
        });

    }



}
