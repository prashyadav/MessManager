package com.example.prashanjeet.messmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    private EditText adminName,adminPass;
    private Button adminLogin;
    String name,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        adminName = (EditText)findViewById(R.id.adminName);
        adminPass = (EditText)findViewById(R.id.adminPassword);
        adminLogin=(Button)findViewById(R.id.adminLogin);
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = adminName.getText().toString();
                pass = adminPass.getText().toString();
                if(name.isEmpty()||pass.isEmpty()){
                    Toast.makeText(AdminLogin.this,"Fill All Details",Toast.LENGTH_SHORT).show();
                    adminName.setText("");
                    adminPass.setText("");
                }
                else
                {
                    if(name.compareTo("root")==0&&pass.compareTo("password")==0){
                        Intent intent = new Intent(AdminLogin.this,ManagerHome.class);
                        Toast.makeText(AdminLogin.this,"Login as Admin",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        adminName.setText("");
                        adminPass.setText("");
                    }
                    else{
                        Toast.makeText(AdminLogin.this,"Wrong User name or Password",Toast.LENGTH_SHORT).show();
                        adminName.setText("");
                        adminPass.setText("");
                    }
                }

            }
        });
    }
}
