package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText name, username, pass, cpass, phone;
    Button btnRegister, btnLog;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        name = findViewById(R.id.txtName);
        username = findViewById(R.id.txtUsername);
        pass = findViewById(R.id.txtPassword);
        cpass = findViewById(R.id.txtCpassword);
        phone = findViewById(R.id.txtPhone);

        btnLog = findViewById(R.id.btnLog);
        btnRegister = findViewById(R.id.btnRegister);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLog();
            }
        });



    }

    protected void performLog(){
        if(name.getText() != null || username.getText() != null || phone.getText() != null){
            if(!pass.getText().toString().isEmpty()){
                String pw = pass.getText().toString();
                String cpw = cpass.getText().toString();

                if(pw.equals(cpw)){
                    service();
                }
                else{
                    showAlert("verification", "Your password does not match the confirmation");
                }

            }
            else{
                showAlert("verification", "Please enter your password");
            }
        }else{
            showAlert("verification", "Please enter your information to get registered");
        }
    }

    private void service() {

        dbWorker dbw = new dbWorker(c);
        dbw.execute("register", name.getText().toString(), username.getText().toString(), pass.getText().toString(), phone.getText().toString());
    }

    private void showAlert(String title, String msg) {
        AlertDialog al = new AlertDialog.Builder(c).create();
        al.setTitle(title);
        al.setMessage(msg);
        al.create();
        al.show();
    }
}