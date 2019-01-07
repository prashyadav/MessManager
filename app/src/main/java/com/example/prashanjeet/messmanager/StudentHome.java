package com.example.prashanjeet.messmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentHome extends AppCompatActivity {

    private ListView listViewMeal;
    private DatabaseReference databaseMealsRef;
    private FirebaseAuth firebaseAuth;
    private List<Meal> mealList;
    private Meal m;

    UserMeal meal;
    AlertDialog alertDialog;

    String mealId,userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        listViewMeal =(ListView) findViewById(R.id.listViewAppo);
        mealList = new ArrayList<>();

        listViewMeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                m =mealList.get(i);

                    showMealDialog(m.title, m.description, m.val);


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("myFile", Context.MODE_PRIVATE);
        String def = "defaul";
        userId = sharedPreferences.getString("id",def);
        mealId = sharedPreferences.getString("mealId",def);
        //String userId = firebaseAuth.getCurrentUser().getUid();
//        Log.d("res", "list"+userId);

        databaseMealsRef = FirebaseDatabase.getInstance().getReference("meals");

//        databaseUserMealRef= databaseUserMealRef.child("meal");

        databaseMealsRef.addValueEventListener(new ValueEventListener() {
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
        Log.d("res", "on start ends here");
    }

    public void showMealDialog(String title, String description, final int val){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflator = getLayoutInflater();
        final View dialogView = inflator.inflate(R.layout.meal_dialog, null);
        dialogBuilder.setView(dialogView);

        Log.d("res", description);
        Log.d("res", title);

        TextView textViewTitle = (TextView) dialogView.findViewById(R.id.adminAppoTitle);
        TextView textViewDescription = (TextView) dialogView.findViewById(R.id.adminAppoDes);
        Button conf = (Button) dialogView.findViewById(R.id.adminDialogConfirmButton);
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm(val);
            }
        });
        Button del = (Button) dialogView.findViewById(R.id.adminDialogCancelButton);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAdminAppo();
            }
        });

        textViewTitle.setText(title);
        textViewDescription.setText(description);

        dialogBuilder.setTitle("Appointment Description");

        Log.d("res", description);
        Log.d("res", title);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void confirm(int val){
        deleteAdminAppo();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("userMeals");

//        databaseReference.child(mealId).child(ad.getId()).setValue(ad);
//        databaseReference.child(ad.getAdminId()).child(ad.getId()).setValue(ad);


//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        final DatabaseReference reference = firebaseDatabase.getReference();


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


//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mealref = databaseReference.child(mealId);


//        mealref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                 List l = dataSnapshot.getValue(List.class);
//                //do what you want with the email
//                Log.d("name", l.toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        mealref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("res", "onStart1 "+status);
                //mealList.clear();

                for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()){
                    Integer a = Integer.parseInt(mealSnapshot.getValue().toString());

//                    if(a.getStatus().equals(status)){
//                        Log.d("res","matches");
//                        appoList.add(a);
//                    }
                    Log.d("jjk",a.toString());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("res", databaseError.toException());
            }
        });



//        int v=meal.list.get(2);
//        String name = meal.name;
//        Log.d("name",  name);
//            try{
//                int v= meal.list.get(0);
//            }
//            catch(Exception e){
//                //Toast.makeText(StudentHome.this, Toast.LENGTH_LONG, e.printStackTrace()).show();
//                e.printStackTrace();
//            }
//        meal.list.set(2,67);
//        mealref.setValue(meal);


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


