package com.example.prashanjeet.messmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminProfile extends AppCompatActivity {
    public TextView totalAmount,totalSpent,totalStudent,monthExp;
    public EditText monthNumber;
    public Button showBtn;
    public DatabaseReference databaseReference,databaseReference1;
    int j=0;
    int x=0;
    int totalexp=0;
    int[]  arr = new int[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        totalAmount = (TextView)findViewById(R.id.totalAmount);
        totalSpent = (TextView)findViewById(R.id.totalSpent);
        totalStudent = (TextView)findViewById(R.id.totalStudent);
        monthNumber = (EditText)findViewById(R.id.month);
        monthExp = (TextView)findViewById(R.id.monthExp);
        showBtn = (Button)findViewById(R.id.showBtn);
        databaseReference = FirebaseDatabase.getInstance().getReference("userMeals");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("expense");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int z=0;
                for(DataSnapshot appoSnapshot : dataSnapshot.getChildren()){
                    UserMeal a = appoSnapshot.getValue(UserMeal.class);
                    if(a.getTotalMeals()!=0){
                        z++;
                        x=x+(15000-a.getBalance());
                       // int v = a.list.get(0);


                    }
                }
                String s = String.valueOf(z);
                totalStudent.setText("Total Students :: "  + s);
                s = String.valueOf(x);
                totalAmount.setText("Total Amount coll:: "  + s);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("res", databaseError.toException());
            }
        });
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot appoSnapshot : dataSnapshot.getChildren()){
                    AdminExpense a = appoSnapshot.getValue(AdminExpense.class);
                    if(a!=null){
                        totalexp+=a.getCost();
                        arr[a.getMonth()]+=a.getCost();
                    }

                }
                String s=String.valueOf(totalexp);
                totalSpent.setText("Total spent::"+s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("res", databaseError.toException());
            }
        });
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monthNumber.getText().toString().isEmpty()){
                    Toast.makeText(AdminProfile.this,"Enter Month",Toast.LENGTH_LONG).show();
                }
                else{
                    int m = Integer.parseInt(monthNumber.getText().toString());
                    m = arr[m];
                    String str = String.valueOf(m);
                    monthExp.setText("Monthly expense is ::" + str);
                }

            }
        });

    }
}
