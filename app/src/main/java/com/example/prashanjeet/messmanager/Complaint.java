package com.example.prashanjeet.messmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.firebase.client.Firebase;

public class Complaint extends AppCompatActivity {
    //Firebase firebase;
    EditText namedata, EmailData, MessageData;
    Button Sendbutton, Detailsbutton;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        namedata = (EditText) findViewById(R.id.Name_EditText);
        EmailData = (EditText) findViewById(R.id.Emailid_EditText);
        MessageData = (EditText) findViewById(R.id.Message_EditText);
        Sendbutton = (Button) findViewById(R.id.SendData_Button);
        progressDialog = new ProgressDialog(Complaint.this);
        databaseReference = FirebaseDatabase.getInstance().getReference("complainds");
        Sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Detailsbutton.setEnabled(true);
                final String name = namedata.getText().toString();
                final String Email = EmailData.getText().toString();
                final String Message = MessageData.getText().toString();
                String id  = databaseReference.push().getKey();
                if(name.isEmpty()||Email.isEmpty()||Message.isEmpty()){
                    Toast.makeText(Complaint.this,"Fill All Details",Toast.LENGTH_LONG).show();
                }
                else{
                    progressDialog.setMessage("Sending Complainds");
                    progressDialog.show();

                    ComplaindClass c = new ComplaindClass(Message,name,Email);

                    try {
                        databaseReference.child(id).setValue(c);
                        Toast.makeText(Complaint.this, "your data sended to the server",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(Complaint.this, "Network Error Try again",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    Intent intent = new Intent(Complaint.this,StudentHome.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }

}
