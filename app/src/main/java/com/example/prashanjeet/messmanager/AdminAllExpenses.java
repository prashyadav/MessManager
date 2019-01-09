package com.example.prashanjeet.messmanager;

import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_expenses);
        listViewExp =(ListView) findViewById(R.id.listViewExp);
        expList = new ArrayList<>();
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
        databaseReference= FirebaseDatabase.getInstance().getReference("expense");


        //databaseUserRef= databaseUserRef.child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("res", "onStart1 "+status);
                expList.clear();

                for(DataSnapshot appoSnapshot : dataSnapshot.getChildren()){
                    AdminExpense a = appoSnapshot.getValue(AdminExpense.class);
                    //String stat = a.getStatus()+ "hello";
                    //Toast.makeText(AdminVerify.this,stat,Toast.LENGTH_LONG).show();
                    int j=0;
                    if(a!=null){
                        Log.d("res","matches");
                        expList.add(a);
                    }
//                    appoList.add(a);

                }


                ExpArrayList adapter = new ExpArrayList(AdminAllExpenses.this, expList);
                listViewExp.setAdapter(adapter);
                if(expList.size()==0){
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
