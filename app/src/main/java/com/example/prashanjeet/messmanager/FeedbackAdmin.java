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

public class FeedbackAdmin extends AppCompatActivity {

    private ListView listViewFeed;
    private List<feedbackprofile> feedList;
    private feedbackprofile feedbackprofile1;
    private ProgressDialog progressDialog;
    public DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_admin);
        listViewFeed =(ListView) findViewById(R.id.listViewFeed);
        progressDialog = new ProgressDialog(FeedbackAdmin.this);
        feedList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("feedback");
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Fetching Feedbacks");
        progressDialog.show();


        //databaseReference= FirebaseDatabase.getInstance().getReference("complainds");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("res", "onStart1 "+status);
                feedList.clear();

                for(DataSnapshot appoSnapshot : dataSnapshot.getChildren()){
                    feedbackprofile a = appoSnapshot.getValue(feedbackprofile.class);
                    //String stat = a.getStatus()+ "hello";
                    //Toast.makeText(AdminVerify.this,stat,Toast.LENGTH_LONG).show();
                    int j=0;
                    if(a!=null){
                        Log.d("res","matches");
                        feedList.add(a);
                    }
//                    appoList.add(a);

                }


                FeedbackList adapter = new FeedbackList(FeedbackAdmin.this, feedList);
                listViewFeed.setAdapter(adapter);
                //progressDialog.dismiss();
                if(feedList.size()==0){
                    Toast.makeText(getApplicationContext(), "No FeedBacks", Toast.LENGTH_SHORT).show();
                }
                ///Log.d("res",appoList.get(0).getTitle());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("res", databaseError.toException());
            }
        });
        Log.d("res", "on start ends here");
        progressDialog.dismiss();
    }
}
