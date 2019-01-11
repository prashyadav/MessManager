package com.example.prashanjeet.messmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExpense extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public EditText expenseDesc,expenseCost;
    public  TextView expenseDate;
    public Button submitBtn;
    DatabaseReference databaseReference;
    String formattedDate = new String("hello");
    String desc,cost1;
    int month,cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        expenseCost = (EditText) findViewById(R.id.expenseCost);
        expenseDate = (TextView)findViewById(R.id.expenseDate);
        expenseDesc = (EditText)findViewById(R.id.expenseDesc);
       // progressDialog = new ProgressDialog(AddExpense.this);
        submitBtn = (Button)findViewById(R.id.submitBtn);
        expenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker ");

            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressDialog.setMessage("Adding expense");
                //progressDialog.show();
               // Toast.makeText(AddExpense.this,"show pressed",Toast.LENGTH_SHORT).show();
                databaseReference = FirebaseDatabase.getInstance().getReference("expense");
                String id = databaseReference.push().getKey();
                desc = expenseDesc.getText().toString();
                cost1 = expenseCost.getText().toString();
                month = Integer.parseInt(formattedDate.substring(3, 5));
                cost = Integer.parseInt(cost1);
                if (formattedDate.compareTo("hello")==0||desc.isEmpty()||cost1.isEmpty()) {
                    Toast.makeText(AddExpense.this, "Fill All details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //progressDialog.setMessage("Adding Expenses");
                   // progressDialog.show();

                    try {
                        AdminExpense addExpense = new AdminExpense(cost,desc,formattedDate,month);

                        databaseReference.child(id).setValue(addExpense);
                        Toast.makeText(AddExpense.this,"added successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddExpense.this,ManagerHome.class);
                        startActivity(intent);
                        finish();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(AddExpense.this,"Network error please try later",Toast.LENGTH_SHORT).show();
                    }
                    //progressDialog.dismiss();
                }


            }
        });


    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        expenseDate.setText(currentDate);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c.getTime());
        //month = Integer.parseInt(formattedDate.substring(3,5));

    }
}
