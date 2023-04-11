package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnLog, btnRegister;
    private EditText txtUser, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context c = this;

        txtUser = findViewById(R.id.txtUsername);
        txtPass = findViewById(R.id.txtPassword);

        btnLog = findViewById(R.id.btnLog);
        btnRegister = findViewById(R.id.btnRegister);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                performLog();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, MainActivity2.class);
                c.startActivity(intent);
            }
        });

    }

    protected void performLog(){
        if(txtUser.getText() != null){
            if(!txtPass.getText().toString().isEmpty()){
                service();
            }
            else{
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
        }
    }



    private void service() {

        dbWorker dbw = new dbWorker(this);
        dbw.execute("login",txtUser.getText().toString(), txtPass.getText().toString());
    }
}