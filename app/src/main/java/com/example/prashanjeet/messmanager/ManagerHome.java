package com.example.prashanjeet.messmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManagerHome extends AppCompatActivity {

    FloatingActionButton fabMeal;
    private ListView listViewMeal;
    private DatabaseReference databaseUserMealRef,databaseReference;
    private FirebaseAuth firebaseAuth;
    private List<Meal> mealList;
    private Meal m;
    private Button stop;
    private ProgressDialog progressDialog;
    AlertDialog alertDialog;
    int cost;
    int cnfstat=0,cancelstat=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        listViewMeal =(ListView) findViewById(R.id.listViewAppo);
        mealList = new ArrayList<>();
        fabMeal = (FloatingActionButton) findViewById(R.id.fab_add);
        progressDialog = new ProgressDialog(ManagerHome.this);
        fabMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new  Intent(ManagerHome.this ,AddMeal.class);
                startActivity(intent);
            }
        });
        listViewMeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                m =mealList.get(i);
                cost = Integer.valueOf(m.getExpectedCost());
               // Toast.makeText(ManagerHome.this,m.getExpectedCost(),Toast.LENGTH_LONG).show();
                //cost = m.getExpectedCost().
                //cost = m.getExpectedCost();
                Log.d("res", "on click listener");
                showMealDialog1();
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
        if(id==R.id.id_scan)
        {
            Intent intent = new Intent(ManagerHome.this,Scan.class);
            startActivity(intent);
            return  true;
        }
        if(id==R.id.id_Comp)
        {
            Intent intent = new Intent(ManagerHome.this,AdminCompalinds.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.id_exp)
        {
            Intent intent = new Intent(ManagerHome.this,AddExpense.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.id_allexp)
        {
            Intent intent = new Intent(ManagerHome.this,AdminAllExpenses.class);
            int y=20;
            String str = String.valueOf(y);
            intent.putExtra("month",str);
            startActivity(intent);
            return true;
        }
        if(id==R.id.id_feedback)
        {
            Intent intent = new Intent(ManagerHome.this,FeedbackAdmin.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.id_profile)
        {
            Intent intent = new Intent(ManagerHome.this,AdminProfile.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.id_activity)
        {
            Intent intent = new Intent(ManagerHome.this,AdminStudentActivity.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.id_QR)
        {
            Intent intent = new Intent(ManagerHome.this,Scan.class);
            startActivity(intent);
            return true;
        }
        return  true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Fetching Details of meals");
        progressDialog.show();

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

               Collections.sort(mealList, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Meal a1 = (Meal) o1;
                    Meal a2 = (Meal) o2;
                    return a1.date.compareToIgnoreCase(a2.date);
                }
            });

                MealArrayList adapter = new MealArrayList(ManagerHome.this, mealList);
                listViewMeal.setAdapter(adapter);
                //progressDialog.dismiss();
                if(mealList.size()==0){
                    Toast.makeText(getApplicationContext(), "No Meals found", Toast.LENGTH_SHORT).show();
                }
                ///Log.d("res",appoList.get(0).getTitle());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("res", databaseError.toException());
                Toast.makeText(getApplicationContext(), "Network Error ", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("res", "on start ends here");
        progressDialog.dismiss();
    }

    public void showMealDialog1(){

       AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ManagerHome.this);
        LayoutInflater inflator = getLayoutInflater();
        final View dialogView = inflator.inflate(R.layout.meal_dialog, null);
        dialogBuilder.setView(dialogView);

        Log.d("res", m.description);
        Log.d("res", m.title);
        TextView textViewTitle1 = (TextView) dialogView.findViewById(R.id.adminAppoTitle);
        TextView textViewDescription1 = (TextView) dialogView.findViewById(R.id.adminAppoDes);
        TextView textViewRegistered1 = (TextView) dialogView.findViewById(R.id.adminAppoTotalReg);
        TextView textViewCost1 = (TextView) dialogView.findViewById(R.id.adminAppoExpCost);
        stop = (Button) dialogView.findViewById(R.id.adminDialogConfirmButton);
        stop.setText("Stop Reg");
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Change status from open to close
                if(m.getRegistration().compareTo("stop")==0){
                    Toast.makeText(ManagerHome.this,"Already Stopped",Toast.LENGTH_SHORT).show();
                }
                else {
                    m.setRegistration("stop");
                    databaseReference = FirebaseDatabase.getInstance().getReference("meals").child(m.getId());
                    databaseReference.setValue(m);
                    Toast.makeText(ManagerHome.this, "Registrations Stopped", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
            }
        });
        Button del = (Button) dialogView.findViewById(R.id.adminDialogCancelButton);
        del.setText("Delete");
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("meals").child(String.valueOf(m.id));
                databaseReference.removeValue();
                Toast.makeText(ManagerHome.this,"Removed",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();

            }
        });
        Button update  = (Button) dialogView.findViewById(R.id.adminDialogUpdateButton);
        update.setText("Update");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerHome.this,UpdateMealActivity.class);
                intent.putExtra("meal_id",m.getId());
                alertDialog.dismiss();
                startActivity(intent);
            }
        });

        textViewTitle1.setText(String.valueOf(m.title));
        textViewDescription1.setText(String.valueOf(m.description));
        textViewCost1.setText("Cost :: "+String.valueOf(m.expectedCost));
        textViewRegistered1.setText("Total Registered::"+String.valueOf(m.registered));
        Log.d("res","set text complete");

        dialogBuilder.setTitle("Meal Description");
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
