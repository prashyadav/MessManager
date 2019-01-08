package com.example.prashanjeet.messmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AdminConfirm extends AppCompatActivity {
    private TextView studentN,studentReg,studentRoom,studentH;
    private Button acceptBtn,rejectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_confirm);
        studentN = (TextView)findViewById(R.id.studentName);
        studentReg = (TextView)findViewById(R.id.studentRegn);
        studentRoom = (TextView)findViewById(R.id.studentRoom);
        studentH = (TextView)findViewById(R.id.studentHostel);
        acceptBtn = (Button)findViewById(R.id.acceptBtn);
        rejectBtn = (Button)findViewById(R.id.rejectBtn);

    }
}
