package com.example.prashanjeet.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.prashanjeet.messmanager.R.id.listViewAppo;

public class AdminVerify extends AppCompatActivity {

    private ListView listViewStudent;
    private DatabaseReference databaseUserRef;
    private FirebaseAuth firebaseAuth;
    private List<Student> studentList;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_verify);

        firebaseAuth = FirebaseAuth.getInstance();

        //Log.d("res", status);

        listViewStudent =(ListView) findViewById(listViewAppo);

        studentList = new ArrayList<>();
        listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                student =studentList.get(i);
                String name = student.getName();
                String uid = student.getId();
                //Toast.makeText(AdminVerify.this,name,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminVerify.this,AdminConfirm.class);
                intent.putExtra("student_uid",uid);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("myFile", Context.MODE_PRIVATE);
        String def = "defaul";
//        String userId = sharedPreferences.getString("id",def);
        //String userId = firebaseAuth.getCurrentUser().getUid();
        //Log.d("res", "list"+userId);
//
//        if(admin.equals("true")){
//            databaseUserAppoRef = FirebaseDatabase.getInstance().getReference("adminAppointments");
//
//        }
//        else{
//            databaseUserAppoRef = FirebaseDatabase.getInstance().getReference("userAppointments");
//        }
        databaseUserRef = FirebaseDatabase.getInstance().getReference("users");


        //databaseUserRef= databaseUserRef.child(userId);

        databaseUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("res", "onStart1 "+status);
                studentList.clear();

                for(DataSnapshot appoSnapshot : dataSnapshot.getChildren()){
                    Student a = appoSnapshot.getValue(Student.class);
                    //String stat = a.getStatus()+ "hello";
                    //Toast.makeText(AdminVerify.this,stat,Toast.LENGTH_LONG).show();
                    int j=0;
                    if(a.getStatus().compareTo("pending")==0){
                        Log.d("res","matches");
                        studentList.add(a);
                    }
//                    appoList.add(a);

                }


                AppoArrayList adapter = new AppoArrayList(AdminVerify.this, studentList);
                listViewStudent.setAdapter(adapter);
                if(studentList.size()==0){
                    Toast.makeText(getApplicationContext(), "No Student Left", Toast.LENGTH_LONG).show();
                }
                ///Log.d("res",appoList.get(0).getTitle());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("res", databaseError.toException());
            }
        });
        Log.d("res", "on start ends here");
    }
}
