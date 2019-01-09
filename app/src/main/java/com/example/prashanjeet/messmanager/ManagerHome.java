package com.example.prashanjeet.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class ManagerHome extends AppCompatActivity {

    FloatingActionButton fabMeal;
    private ListView listViewMeal;
    private DatabaseReference databaseUserMealRef;
    private FirebaseAuth firebaseAuth;
    private List<Meal> mealList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        listViewMeal =(ListView) findViewById(R.id.listViewAppo);
        mealList = new ArrayList<>();
        fabMeal = (FloatingActionButton) findViewById(R.id.fab_add);
        fabMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new  Intent(ManagerHome.this ,AddMeal.class);
                startActivity(intent);
            }
        });

    }
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
      getMenuInflater().inflate(R.menu.manager_main,menu);
      return  true;
  }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.id_verify)
        {
            Intent intent = new Intent(ManagerHome.this,AdminVerify.class);
            startActivity(intent);
            return  true;
        }
        if(id==R.id.id_Comp)
        {
            return true;
        }
        if(id==R.id.id_QR)
        {
            return true;
        }
        if(id==R.id.id_feedback)
        {
            return true;
        }
        if(id==R.id.id_profile)
        {
            return true;
        }
        if(id==R.id.id_activity)
        {
            Intent intent = new Intent(ManagerHome.this,AdminStudentActivity.class);
            startActivity(intent);
            return true;
        }
        return  true;
    }
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("myFile", Context.MODE_PRIVATE);
        String def = "defaul";
//        String userId = sharedPreferences.getString("id",def);
        //String userId = firebaseAuth.getCurrentUser().getUid();
//        Log.d("res", "list"+userId);

        databaseUserMealRef = FirebaseDatabase.getInstance().getReference("meals");

//        databaseUserMealRef= databaseUserMealRef.child("meal");

        databaseUserMealRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("res", "onStart1 "+status);
                mealList.clear();

                for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()){
                    Meal a = mealSnapshot.getValue(Meal.class);
//                    if(a.getStatus().equals(status)){
//                        Log.d("res","matches");
//                        appoList.add(a);
//                    }
                    mealList.add(a);

                }

//                Collections.sort(appoList, new Comparator() {
//                    @Override
//                    public int compare(Object o1, Object o2) {
//                        Appointment a1 = (Appointment) o1;
//                        Appointment a2 = (Appointment) o2;
//                        return a1.getDate().compareToIgnoreCase(a2.getDate());
//                    }
//                });

//                Admin a =new Admin("a","sd","sd","sd","sd","sd","sd");
//                adminlist.add(a);
//                Admin b =new Admin("a","sad","asd","asd","sd","sd","sd");
//                adminlist.add(b);
                MealArrayList adapter = new MealArrayList(ManagerHome.this, mealList);
                listViewMeal.setAdapter(adapter);
                if(mealList.size()==0){
                    Toast.makeText(getApplicationContext(), "No Meals found", Toast.LENGTH_LONG).show();
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
