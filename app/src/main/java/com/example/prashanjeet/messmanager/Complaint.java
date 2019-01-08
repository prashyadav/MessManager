package com.example.prashanjeet.messmanager;

import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import static android.R.attr.name;
import static android.os.Build.ID;

public class Complaint extends AppCompatActivity {
    Firebase firebase;
    EditText namedata, EmailData, MessageData;
    Button Sendbutton, Detailsbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        namedata = (EditText) findViewById(R.id.Name_EditText);
        EmailData = (EditText) findViewById(R.id.Emailid_EditText);
        MessageData = (EditText) findViewById(R.id.Message_EditText);
        Sendbutton = (Button) findViewById(R.id.SendData_Button);
        Detailsbutton = (Button) findViewById(R.id.Details_Button);
        Firebase.setAndroidContext(this);
        String UniqueId=
                        Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
        firebase=new Firebase ("https://messmanagement-254e2.firebaseio.com/complaints/"+UniqueId);
        Sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Detailsbutton.setEnabled(true);
                final String name = namedata.getText().toString();
                final String Email = EmailData.getText().toString();
                final String Message = MessageData.getText().toString();
                Firebase child_name = firebase.child("Name");
                child_name.setValue(name);
                if (name.isEmpty()) {
                    namedata.setError("This Field Required Name!");
                    Sendbutton.setEnabled(false);
                } else {
                    namedata.setError(null);
                    Sendbutton.setEnabled(true);
                }

                Firebase child_Email = firebase.child("E-mail");
                child_Email.setValue(Email);
                if (Email.isEmpty()) {
                    EmailData.setError("This Field Required Name!");
                    Sendbutton.setEnabled(false);
                } else {
                    EmailData.setError(null);
                    Sendbutton.setEnabled(true);
                }

                Firebase child_Message = firebase.child("Message");
                child_Message.setValue(Message);
                if (Message.isEmpty()) {
                    MessageData.setError("This Field Required Name!");
                    Sendbutton.setEnabled(false);
                } else {
                    MessageData.setError(null);
                    Sendbutton.setEnabled(true);
                }
                Toast.makeText(Complaint.this, "your data sended to the server",Toast.LENGTH_SHORT).show();
                Detailsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     new AlertDialog.Builder(Complaint.this)
                             .setTitle("Sended Details:")
                             .setMessage("Name-"+name+"\n\nEmail-"+Email+"\n\nMessage"+Message).show();

            }
        });
            }
        });
    }

}
