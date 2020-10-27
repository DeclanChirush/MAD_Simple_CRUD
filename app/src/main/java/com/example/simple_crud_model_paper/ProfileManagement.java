package com.example.simple_crud_model_paper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import database.DBHandler;

public class ProfileManagement extends AppCompatActivity {

    EditText userName, dob, password;
    RadioButton male, female;
    Button update, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        userName = findViewById(R.id.edUserNamePM);
        dob = findViewById(R.id.edDobPM);
        password = findViewById(R.id.edPasswordPM);
        male = findViewById(R.id.radioButtonMalePM);
        female = findViewById(R.id.radioButtonFermalePM);
        add = findViewById(R.id.btnAddPM);
        update = findViewById(R.id.btnUpdatePM);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfile.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());

                String US = userName.getText().toString();
                String dateOfBirth = dob.getText().toString();
                String PS = password.getText().toString();
                String gender;

                if(male.isChecked()){
                    gender = "Male";
                }
                else{
                    gender = "Female";
                }

                long newRawId = dbHandler.addInfo(US,dateOfBirth,PS,gender);

                if(newRawId >=1) {
                    Toast.makeText(ProfileManagement.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProfileManagement.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(getApplicationContext(),EditProfile.class));
            }
        });
    }
}