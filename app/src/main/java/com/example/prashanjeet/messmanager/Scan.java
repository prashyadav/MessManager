package com.example.prashanjeet.messmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Scan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    Button scanButton;
    private Spinner me;
    private DatabaseReference databaseUserMealsRef;
    UserMeal userMeal,userMeal1;
    private FirebaseAuth firebaseAuth;
    TextView da;
    String formattedDate = new String("hello");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseUserMealsRef = FirebaseDatabase.getInstance().getReference("userMeals");


        me =(Spinner) findViewById(R.id.editText_scan);
        da = (TextView) findViewById(R.id.date_scan);
        da.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker ");

            }
        });
        List<String> list  = new ArrayList<String>();
        list.add("Select Meal");
        list.add("breakfast");
        list.add("lunch");
        list.add("snacks");
        list.add("dinner");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Scan.this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        me.setAdapter(arrayAdapter);
        me.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                me.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        scanButton = (Button) findViewById(R.id.btn_scan);
        final Activity activity = this;
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formattedDate.compareTo("hello")==0||me.getSelectedItem().toString().compareTo("Select Meal")==0){
                    Toast.makeText(Scan.this,"Fill Details",Toast.LENGTH_SHORT).show();
                }
                else {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    intentIntegrator.setPrompt("Scan");
                    intentIntegrator.setCameraId(0);
                    intentIntegrator.setBeepEnabled(false);
                    intentIntegrator.setBarcodeImageEnabled(false);
                    intentIntegrator.initiateScan();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result !=null){
            if(result.getContents()==null){
                Toast.makeText(this, "you cancelled scanning", Toast.LENGTH_SHORT).show();
            }
            else{
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                checkStatus(result.getContents());

            }
        }
        else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        //String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
         formattedDate = df.format(c.getTime());

        da.setText(formattedDate);
    }

    public void checkStatus(String id){

        databaseUserMealsRef = databaseUserMealsRef.child(id);
        databaseUserMealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userMeal = dataSnapshot.getValue(UserMeal.class);
                //do what you want with the email
                Log.d("name", userMeal.getName());

                String date = da.getText().toString();

                int index= (Integer.parseInt(date.substring(3,5))-1)*31+Integer.parseInt(date.substring(0,2));
                int v =userMeal.list.get(index);
                String usermeal = me.getSelectedItem().toString();
                int b=0,l=0,s=0,d=0,b1=0,l1=0,s1=0,d1=0;
                b = v&1;
                b1=v&16;
                l=v&2;
                l1=v&32;
                s=v&4;
                s1=v&64;
                d=v&8;
                d1=v&128;
                if(b!=0&&b1==0&&usermeal.compareTo("breakfast")==0){
                    Toast.makeText(Scan.this, "yes, he is registered for meal", Toast.LENGTH_SHORT).show();
                    v=v|16;
                    List<Integer> list  =  new ArrayList<Integer>();
                    list = userMeal.getList();
                    list.add(index,v);
                    userMeal1 = new UserMeal(userMeal.getName(),userMeal.getRegNumber(),userMeal.getTotalMeals(),userMeal.getBalance(),list);
                    databaseUserMealsRef.setValue(userMeal1);
                }
                else if(l!=0&&l1==0&&usermeal.compareTo("lunch")==0){
                    Log.d("name", userMeal.getName());
                    Toast.makeText(Scan.this, "yes, he is registered for meal", Toast.LENGTH_SHORT).show();
                    v=v|32;
                    List<Integer> list  =  new ArrayList<Integer>();
                    list = userMeal.getList();
                    list.add(index,v);
                    userMeal1 = new UserMeal(userMeal.getName(),userMeal.getRegNumber(),userMeal.getTotalMeals(),userMeal.getBalance(),list);
                    databaseUserMealsRef.setValue(userMeal1);
                }
                else if(s!=0&&s1==0&&usermeal.compareTo("snacks")==0){
                    Log.d("name", userMeal.getName());
                    Toast.makeText(Scan.this, "yes, he is registered for meal", Toast.LENGTH_SHORT).show();
                    v=v|64;
                    List<Integer> list  =  new ArrayList<Integer>();
                    list = userMeal.getList();
                    list.add(index,v);
                    userMeal1 = new UserMeal(userMeal.getName(),userMeal.getRegNumber(),userMeal.getTotalMeals(),userMeal.getBalance(),list);
                    databaseUserMealsRef.setValue(userMeal1);
                }
                else if(d!=0&&d1==0&&usermeal.compareTo("dinner")==0){
                    Log.d("name", userMeal.getName());
                    Toast.makeText(Scan.this, "yes, he is registered for meal", Toast.LENGTH_SHORT).show();
                    v=v|128;
                    List<Integer> list  =  new ArrayList<Integer>();
                    list = userMeal.getList();
                    list.add(index,v);
                    userMeal1 = new UserMeal(userMeal.getName(),userMeal.getRegNumber(),userMeal.getTotalMeals(),userMeal.getBalance(),list);
                    databaseUserMealsRef.setValue(userMeal1);
                }
                else {
                    Log.d("name", "not registered");
//                    Toast.makeText(this, "No, he is not  registered for meal", Toast.LENGTH_LONG).show();
                    Toast.makeText(Scan.this,"Not Registered for meal",Toast.LENGTH_SHORT).show();

                }

                Intent intent = new Intent(Scan.this,Scan.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Scan.this,"Network Error",Toast.LENGTH_SHORT).show();

            }
        });

//        String date = da.getText().toString();
//
//        int index= (Integer.parseInt(date.substring(3,5))-1)*31+Integer.parseInt(date.substring(0,2));
//        int v =userMeal.list.get(index);
//        String usermeal = me.getText().toString();
//        int b=0,l=0,s=0,d=0;
//        b = v&1;
//        l=v&2;
//        s=v&4;
//        d=v&8;
//        if(b!=0&&usermeal.compareTo("breakfast")==0){
//            Toast.makeText(this, "yes, he is registered for meal", Toast.LENGTH_LONG).show();
//        }
//        else if(l!=0&&usermeal.compareTo("lunch")==0){
//            Toast.makeText(this, "yes, he is registered for meal", Toast.LENGTH_LONG).show();
//        }
//        else if(s!=0&&usermeal.compareTo("snacks")==0){
//            Toast.makeText(this, "yes, he is registered for meal", Toast.LENGTH_LONG).show();
//        }
//        else if(d!=0&&usermeal.compareTo("dinner")==0){
//            Toast.makeText(this, "yes, he is registered for meal", Toast.LENGTH_LONG).show();
//        }
//        else {
//
//                Toast.makeText(this, "No, he is not  registered for meal", Toast.LENGTH_LONG).show();
//
//        }



    }




}
