package com.example.prashanjeet.messmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminAllExpenses extends AppCompatActivity {

    private ListView listViewExp;
    DatabaseReference databaseReference;
    private List<AdminExpense> expList;
    private AdminExpense adminExpense;
    private ProgressDialog progressDialog;
    String m;
    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_expenses);
        progressDialog = new ProgressDialog(AdminAllExpenses.this);
        listViewExp =(ListView) findViewById(R.id.listViewExp);
        expList = new ArrayList<>();
        Intent intent = getIntent();
        m= intent.getStringExtra("month");
        j = Integer.parseInt(m);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Fetching Details of expenses");
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("myFile", Context.MODE_PRIVATE);
        String def = "defaul";

        databaseReference= FirebaseDatabase.getInstance().getReference("expense");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("res", "onStart1 "+status);
                expList.clear();

                for(DataSnapshot appoSnapshot : dataSnapshot.getChildren()){
                    AdminExpense a = appoSnapshot.getValue(AdminExpense.class);
                    //String stat = a.getStatus()+ "hello";
                    //Toast.makeText(AdminVerify.this,stat,Toast.LENGTH_LONG).show();
                    if(a!=null){
                        Log.d("res","matches");
                        if(j==20){
                            expList.add(a);
                        }
                        else
                        {
                            if(a.getMonth()==j)
                            {
                                expList.add(a);
                            }
                        }

                    }
//                    appoList.add(a);

                }


                ExpArrayList adapter = new ExpArrayList(AdminAllExpenses.this, expList);
                listViewExp.setAdapter(adapter);

                if(expList.size()==0){
                    Toast.makeText(getApplicationContext(), "No Expenses Done", Toast.LENGTH_SHORT).show();
                }
                ///Log.d("res",appoList.get(0).getTitle());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminAllExpenses.this,"Network Error Try Again",Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.dismiss();
        Log.d("res", "on start ends here");
    }


}
