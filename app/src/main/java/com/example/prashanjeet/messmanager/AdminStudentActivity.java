package com.example.prashanjeet.messmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminStudentActivity extends AppCompatActivity {

    public EditText studentReg;
    public Button showBtn;
    String reg,mealUid;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student);
        studentReg = (EditText)findViewById(R.id.studentRegn1);
        showBtn = (Button)findViewById(R.id.showBtn);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg = studentReg.getText().toString();
               // Toast.makeText(AdminStudentActivity.this,reg,Toast.LENGTH_LONG).show();
                if(reg.isEmpty()){
                    Toast.makeText(AdminStudentActivity.this,"Please fill all details",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int j=0;
                            for(DataSnapshot appoSnapshot : dataSnapshot.getChildren()){
                                Student a = appoSnapshot.getValue(Student.class);
                                String stat = a.getRegNumber();
                                mealUid = a.getId();
                                //Toast.makeText(AdminStudentActivity.this,stat,Toast.LENGTH_LONG).show();
                                if(a.getRegNumber().compareTo(reg)==0){
                                    //Toast.makeText(AdminStudentActivity.this,"hii",Toast.LENGTH_LONG).show();
                                    j=1;
                                    Intent intent = new Intent(AdminStudentActivity.this,StudentActivities.class);
                                    intent.putExtra("mealId",mealUid);
                                    startActivity(intent);
                                    Log.d("res","matches");
                                    break;
                                    //studentList.add(a);
                                }
//                    appoList.add(a);

                            }


                           // AppoArrayList adapter = new AppoArrayList(AdminVerify.this, studentList);
                           // listViewStudent.setAdapter(adapter);
                            if(j==0){
                                Toast.makeText(getApplicationContext(), "No StudentFind with this Registration Number", Toast.LENGTH_LONG).show();
                            }
                            ///Log.d("res",appoList.get(0).getTitle());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("res", databaseError.toException());
                        }
                    });


                }

            }
        });
    }
}
