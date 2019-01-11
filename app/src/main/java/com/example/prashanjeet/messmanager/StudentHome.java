package com.example.prashanjeet.messmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
    public Button conf;
    private DatabaseReference databaseMealsRef, databaseUserRef, databaseUserMealsRef;
    private FirebaseAuth firebaseAuth;
    private List<Meal> mealList;
    private Meal m;
    int cost;
    int cnfstat=0;
    int cancelstat=0;
    String mealUid,userName;
    private ProgressDialog progressDialog;


    UserMeal meal;
    AlertDialog alertDialog;

    String mealId,userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        Intent intent = getIntent();
        mealUid= intent.getStringExtra("mealId");
        userName = intent.getStringExtra("user_name");
        firebaseAuth=FirebaseAuth.getInstance();
        listViewMeal =(ListView) findViewById(R.id.listViewAppo);
        progressDialog = new ProgressDialog(StudentHome.this);
        //complaintbutton=(Button)findViewById(R.id.Complaint_Button);
//        complaintbutton.setOnClickListener(new View.OnClickListener() {
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
                cost = Integer.valueOf(m.getExpectedCost());
                //cost = m.getExpectedCost().
                //cost = m.getExpectedCost();
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
            Intent intent = new Intent(StudentHome.this,StudentActivities.class);
            intent.putExtra("mealId",firebaseAuth.getCurrentUser().getUid());
            startActivity(intent);
            return  true;
        }
        if(id==R.id.id_Comp)
        {
            Intent intent=new Intent(StudentHome.this,Complaint.class);
            //intent.putExtra("userId",firebaseAuth.getCurrentUser().getUid());
            intent.putExtra("userId",firebaseAuth.getCurrentUser().getUid());
            startActivity(intent);
            return true;
        }
        if(id==R.id.id_QR)
        {
            Intent intent = new Intent(StudentHome.this,QRCode.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.id_feedback)
        {
            Intent intent = new Intent(StudentHome.this,FeedBack.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.id_signout)
        {
            firebaseAuth.signOut();
            Intent intent = new Intent(StudentHome.this,MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return  true;
    }
    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        progressDialog.setMessage("Fetching Details!!");
        progressDialog.show();

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

                    if(formattedDate.compareTo(a.date)<=0&&a.getRegistration().compareTo("open")==0){
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
                progressDialog.dismiss();
                if(mealList.size()==0){
                    Toast.makeText(getApplicationContext(), "No Meals found", Toast.LENGTH_LONG).show();
                   // progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("res", databaseError.toException());
            }
        });

        databaseUserMealsRef = FirebaseDatabase.getInstance().getReference("userMeals").child(userId);


        databaseUserMealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                meal = dataSnapshot.getValue(UserMeal.class);
                //do what you want with the email
                //Log.d("name", meal.toString());
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
        TextView textViewDCost = (TextView) dialogView.findViewById(R.id.adminAppoExpCost);
        TextView textViewDate = (TextView) dialogView.findViewById(R.id.adminAppoTotalReg);
          conf = (Button) dialogView.findViewById(R.id.adminDialogConfirmButton);
        conf.setText("Confirm");
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
//                if(cnfstat==0) {
//                    confirm();
//                    cnfstat =1;
//                    cancelstat=1;
//                }
//                else
//                {
//                    Toast.makeText(StudentHome.this,"Already Confirmed",Toast.LENGTH_LONG).show();
//                    alertDialog.dismiss();
//                }

            }
        });
        Button del = (Button) dialogView.findViewById(R.id.adminDialogCancelButton);
        del.setText("Cancel");
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirm();
                deleteAdminAppo();
            }
        });
        Button feedback = (Button)  dialogView.findViewById(R.id.adminDialogUpdateButton);
        feedback.setText("Feedback");
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this,FeedBack.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });

        textViewTitle.setText(m.title);
        textViewDescription.setText(m.description);
        textViewDCost.setText("Expected cost ::"+m.expectedCost);
        textViewDate.setText(m.date);

        dialogBuilder.setTitle("Meal Description");

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void confirm(){
        int index= (Integer.parseInt(m.date.substring(3,5))-1)*31+Integer.parseInt(m.date.substring(0,2));
        Log.d("name", String.valueOf(index));
        int v =meal.list.get(index);
        int p=v&m.val;
        if(p!=0)
        {
            alertDialog.dismiss();
            Toast.makeText(StudentHome.this,"Already Confirmed",Toast.LENGTH_LONG).show();
        }
        else {
            meal.list.set(index, v + m.val);
            v = meal.getTotalMeals();
            v++;
            meal.setTotalMeals(v);
            v = meal.getBalance();
            v = v - cost;
            meal.setBalance(v);
            databaseUserMealsRef.setValue(meal);
            m.registered += 1;
            databaseMealsRef.child(m.getId()).setValue(m);
            alertDialog.dismiss();
            Toast.makeText(StudentHome.this, "confirmed", Toast.LENGTH_LONG).show();
        }
    }
    private void deleteAdminAppo(){
        Log.d("res", "deleted");
        int index= (Integer.parseInt(m.date.substring(3,5))-1)*31+Integer.parseInt(m.date.substring(0,2));
        Log.d("name", String.valueOf(index));
        int v =meal.list.get(index);
        int p=v&m.val;
        if(p==0)
        {
            alertDialog.dismiss();
            Toast.makeText(StudentHome.this,"Not Confirmed",Toast.LENGTH_LONG).show();
        }
        else {
            meal.list.set(index, v - m.val);
            v = meal.getTotalMeals();
            v--;
            meal.setTotalMeals(v);
            v = meal.getBalance();
            v = v + cost;
            meal.setBalance(v);
            databaseUserMealsRef.setValue(meal);
            m.registered -= 1;
            databaseMealsRef.child(m.getId()).setValue(m);
            alertDialog.dismiss();
            Toast.makeText(StudentHome.this, "cancelled", Toast.LENGTH_LONG).show();
        }
    }
}


