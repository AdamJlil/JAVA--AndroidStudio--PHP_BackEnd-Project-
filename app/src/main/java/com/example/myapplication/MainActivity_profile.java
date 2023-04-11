package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity_profile extends AppCompatActivity {
    EditText name, username, pass, npass, cpass, phone;
    Button btnUpdate, btnCancel;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        name = findViewById(R.id.txtName);
        username = findViewById(R.id.txtUsername);
        pass = findViewById(R.id.txtPassword);
        npass = findViewById(R.id.txtNpassword);
        cpass = findViewById(R.id.txtCpassword);
        phone = findViewById(R.id.txtPhone);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);

        name.setText(StaticLog.name);
        username.setText(StaticLog.user);
        phone.setText(StaticLog.phone);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, MainActivity_Logedin.class);
                startActivity(i);
            }
        });



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        name.length() == 0||
                        username.length() == 0||
                        pass.length() == 0||
                        phone.length() == 0
                ){
                    showAlert("Warning", "Please enter your password, name and phone username for us to update your profile information.");
                }
                else if(npass.getText().toString().length() == 0){

                    dbWorker db = new dbWorker(c);
                    db.execute("updateProfileNoPw", StaticLog.id, pass.getText().toString(),
                                username.getText().toString(), name.getText().toString(), phone.getText().toString());

                }
                else if (npass.getText().toString().length() != 0){
                    if(!npass.getText().toString().equals(cpass.getText().toString()) ){
                        showAlert("Warning", "Your password and confirmation does not match.");
                    }
                    else{
                        dbWorker db = new dbWorker(c);
                        db.execute("updateProfileWPw", StaticLog.id, pass.getText().toString(),
                                username.getText().toString(), name.getText().toString(), phone.getText().toString(),
                                npass.getText().toString());
                    }
                }


            }
        });






    }


    private void showAlert(String title, String msg) {
        AlertDialog al = new AlertDialog.Builder(c).create();
        al.setTitle(title);
        al.setMessage(msg);
        al.create();
        al.show();
    }
}