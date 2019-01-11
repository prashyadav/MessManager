package com.example.prashanjeet.messmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AdminCompalinds extends AppCompatActivity {

    private ListView listViewComp;
    private List<ComplaindClass> cmpList;
    private ComplaindClass complaindClass;
    public  DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_compalinds);
        listViewComp =(ListView) findViewById(R.id.listViewComp);
        cmpList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("complainds");
        progressDialog = new ProgressDialog(AdminCompalinds.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Fetching Details of Complainds");
        progressDialog.show();

        SharedPreferences sharedPreferences = getSharedPreferences("myFile", Context.MODE_PRIVATE);
        String def = "defaul";
        //databaseReference= FirebaseDatabase.getInstance().getReference("complainds");
         databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("res", "onStart1 "+status);
                cmpList.clear();

                for(DataSnapshot appoSnapshot : dataSnapshot.getChildren()){
                    ComplaindClass a = appoSnapshot.getValue(ComplaindClass.class);
                    //String stat = a.getStatus()+ "hello";
                    //Toast.makeText(AdminVerify.this,stat,Toast.LENGTH_LONG).show();
                    int j=0;
                    if(a!=null){
                        Log.d("res","matches");
                        cmpList.add(a);
                    }
//                    appoList.add(a);

                }


                ComplaindList adapter = new ComplaindList(AdminCompalinds.this, cmpList);
                listViewComp.setAdapter(adapter);

                if(cmpList.size()==0){
                    Toast.makeText(getApplicationContext(), "No Complainds", Toast.LENGTH_SHORT).show();
                }
                ///Log.d("res",appoList.get(0).getTitle());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("res", databaseError.toException());
            }
        });
        progressDialog.dismiss();
        Log.d("res", "on start ends here");
    }
}
