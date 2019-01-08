package com.example.prashanjeet.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class StudentHome extends AppCompatActivity {

    private ListView listViewMeal;
    private DatabaseReference databaseMealsRef, databaseUserRef, databaseUserMealsRef;
    private FirebaseAuth firebaseAuth;
    private List<Meal> mealList;
    private Meal m;
    int cost;

    UserMeal meal;
    AlertDialog alertDialog;

    String mealId,userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        listViewMeal =(ListView) findViewById(R.id.listViewAppo);
        //complaintbutton=(Button)findViewById(R.id.Complaint_Button);
        //complaintbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent  = new  Intent(StudentHome.this ,Complaint.class);
//                startActivity(intent);
//            }
//        });

        mealList = new ArrayList<>();

        listViewMeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                m =mealList.get(i);
                cost = m.getExpectedCost();
                    showMealDialog();



            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.id_profile)
        {
            return  true;
        }
        if(id==R.id.id_Comp)
        {
            Intent intentcompalints=new Intent(StudentHome.this,Complaint.class);
            startActivity(intentcompalints);
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
        return  true;
    }
    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        databaseMealsRef = FirebaseDatabase.getInstance().getReference("meals");

        databaseUserRef  = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseMealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("res", "onStart1 "+status);
                mealList.clear();

                for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()){
                    Meal a = mealSnapshot.getValue(Meal.class);

                    Date c = Calendar.getInstance().getTime();
                    //System.out.println("Current time => " + c);

                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = df.format(c);

                    if(formattedDate.compareTo(a.date)<=0){
                        mealList.add(a);
                    }


                }

                Collections.sort(mealList, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Meal a1 = (Meal) o1;
                        Meal a2 = (Meal) o2;
                        return a1.date.compareToIgnoreCase(a2.date);
                    }
                });


                MealArrayList adapter = new MealArrayList(StudentHome.this, mealList);
                listViewMeal.setAdapter(adapter);
                if(mealList.size()==0){
                    Toast.makeText(getApplicationContext(), "No Meals found", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("res", databaseError.toException());
            }
        });

        databaseUserMealsRef = FirebaseDatabase.getInstance().getReference("userMeals").child("-LVdNEdqkkFxxYwxumR2");


        databaseUserMealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                meal = dataSnapshot.getValue(UserMeal.class);
                //do what you want with the email
                Log.d("name", meal.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//


        Log.d("res", "on start ends here");
    }

    public void showMealDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflator = getLayoutInflater();
        final View dialogView = inflator.inflate(R.layout.meal_dialog, null);
        dialogBuilder.setView(dialogView);

        Log.d("res", m.description);
        Log.d("res", m.title);

        TextView textViewTitle = (TextView) dialogView.findViewById(R.id.adminAppoTitle);
        TextView textViewDescription = (TextView) dialogView.findViewById(R.id.adminAppoDes);
        Button conf = (Button) dialogView.findViewById(R.id.adminDialogConfirmButton);
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
        Button del = (Button) dialogView.findViewById(R.id.adminDialogCancelButton);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAdminAppo();
            }
        });

        textViewTitle.setText(m.title);
        textViewDescription.setText(m.description);

        dialogBuilder.setTitle("Meal Description");

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void confirm(){

//        Query query = reference.child("tasks").orderByChild("Description").equalTo("test2");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
//                String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
//                String path = "/" + dataSnapshot.getKey() + "/" + key;
//                HashMap<String, Object> result = new HashMap<>();
//                result.put("Status", "COMPLETED");
//                reference.child(path).updateChildren(result);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Logger.error(TAG, ">>> Error:" + "find onCancelled:" + databaseError);
//
//            }
//        });



        int index= (Integer.parseInt(m.date.substring(3,5))-1)*31+Integer.parseInt(m.date.substring(0,2));
        Log.d("name", String.valueOf(index));
        int v =meal.list.get(index);
        meal.list.set(index,v+m.val);
        v=meal.getTotalMeals();
        v++;
        meal.setTotalMeals(v);
        v=meal.getBalance();
        v= v-cost;
        meal.setBalance(v);
        databaseUserMealsRef.setValue(meal);
        alertDialog.dismiss();

    }
    private void deleteAdminAppo(){
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("adminAppointments").child(ad.getAdminId()).child(ad.getId());
//        DatabaseReference dbRef =FirebaseDatabase.getInstance().getReference().child("userAppointments").child(ad.getUserId()).child(ad.getId());
//        dbRef.removeValue();
//        databaseReference.removeValue();
        Log.d("res", "deleted");

        alertDialog.dismiss();

    }
}


