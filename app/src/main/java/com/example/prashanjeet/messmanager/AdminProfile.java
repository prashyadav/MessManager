package com.example.prashanjeet.messmanager;

import android.app.ProgressDialog;
import android.content.Intent;
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
    public TextView totalAmount,totalSpent,totalStudent,monthExp,monthExpShow;
    public EditText monthNumber;
    public Button showBtn;
    public DatabaseReference databaseReference,databaseReference1;
    private ProgressDialog progressDialog;
    int j=0;
    int x=0;
    int totalexp=0;
    int[]  arr = new int[20];
    int m;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        totalAmount = (TextView)findViewById(R.id.totalAmount);
        totalSpent = (TextView)findViewById(R.id.totalSpent);
        totalStudent = (TextView)findViewById(R.id.totalStudent);
        monthExpShow = (TextView)findViewById(R.id.monthExpshow);
        monthNumber = (EditText)findViewById(R.id.month);
        monthExp = (TextView)findViewById(R.id.monthExp);
        showBtn = (Button)findViewById(R.id.showBtn);
       // monthBtn = (Button)findViewById(R.id.monthBtn) ;
        progressDialog = new ProgressDialog(AdminProfile.this);
        progressDialog.setMessage("Fetching Details");
        progressDialog.show();
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
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("res", databaseError.toException());
            }
        });
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.setMessage("Fetching Details");
                progressDialog.show();
                for(DataSnapshot appoSnapshot : dataSnapshot.getChildren()){
                    AdminExpense a = appoSnapshot.getValue(AdminExpense.class);
                    if(a!=null){
                        totalexp+=a.getCost();
                        arr[a.getMonth()]+=a.getCost();
                    }

                }
                String s=String.valueOf(totalexp);
                totalSpent.setText("Total spent::"+s);
                progressDialog.dismiss();
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
                    m = Integer.parseInt(monthNumber.getText().toString());
                    if(m>12&&m>0)
                    {
                        Toast.makeText(AdminProfile.this,"Please enter value between 1-12",Toast.LENGTH_LONG).show();
                    }
                    else {
                        str = String.valueOf(m);
                        m = arr[m];

                        monthExp.setText("Monthly expense is ::" + String.valueOf(m));
                        monthExpShow.setText("See Monthly Expense");
                    }
                }

            }
        });
        monthExpShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProfile.this,AdminAllExpenses.class);
                //Toast.makeText(AdminProfile.this, "Month is "+str,Toast.LENGTH_SHORT).show();
                intent.putExtra("month",str);
                monthExp.setText("");
                monthExpShow.setText("");
                startActivity(intent);
            }
        });

    }
}
