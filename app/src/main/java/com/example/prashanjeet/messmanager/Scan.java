package com.example.prashanjeet.messmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Scan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    Button scanButton;
    private DatabaseReference databaseUserMealsRef;
    UserMeal userMeal;
    private FirebaseAuth firebaseAuth;
    EditText me;
    TextView da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseUserMealsRef = FirebaseDatabase.getInstance().getReference("userMeals").child("-LVdNEdqkkFxxYwxumR2");


        me =(EditText) findViewById(R.id.editText_scan);
        da = (TextView) findViewById(R.id.date_scan);
        da.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker ");

            }
        });


        scanButton = (Button) findViewById(R.id.btn_scan);
        final Activity activity = this;
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result !=null){
            if(result.getContents()==null){
                Toast.makeText(this, "you cancelled scanning", Toast.LENGTH_LONG).show();
            }
            else{
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                checkStatus();




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
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        da.setText(formattedDate);
    }

    public void checkStatus(){

        databaseUserMealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userMeal = dataSnapshot.getValue(UserMeal.class);
                //do what you want with the email
                Log.d("name", userMeal.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String date = da.getText().toString();

        int index= (Integer.parseInt(date.substring(3,5))-1)*31+Integer.parseInt(date.substring(0,2));
        int v =userMeal.list.get(index);
        String usermeal = me.getText().toString();
        int b=0,l=0,s=0,d=0;
        b = v&1;
        l=v&2;
        s=v&4;
        d=v&8;
        if(b!=0&&usermeal.compareTo("breakfast")==0){
            Toast.makeText(this, "yes, he is registered for meal", Toast.LENGTH_LONG).show();
        }
        else if(l!=0&&usermeal.compareTo("lunch")==0){
            Toast.makeText(this, "yes, he is registered for meal", Toast.LENGTH_LONG).show();
        }
        else if(s!=0&&usermeal.compareTo("snacks")==0){
            Toast.makeText(this, "yes, he is registered for meal", Toast.LENGTH_LONG).show();
        }
        else if(d!=0&&usermeal.compareTo("dinner")==0){
            Toast.makeText(this, "yes, he is registered for meal", Toast.LENGTH_LONG).show();
        }
        else {

                Toast.makeText(this, "No, he is not  registered for meal", Toast.LENGTH_LONG).show();

        }



    }




}