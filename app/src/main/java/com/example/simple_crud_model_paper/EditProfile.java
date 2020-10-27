package com.example.simple_crud_model_paper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

import database.DBHandler;

public class EditProfile extends AppCompatActivity {

    EditText dob, password, search;
    RadioButton male, female;
    Button edit, delete, searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        search = findViewById(R.id.edSearchEp);
        dob = findViewById(R.id.edDobEp);
        password = findViewById(R.id.edPasswordEp);
        male = findViewById(R.id.radioButtonMaleEP);
        female = findViewById(R.id.radioButtonFemaleEP);
        delete = findViewById(R.id.btnDeleteEP);
        edit = findViewById(R.id.btnEditEP);
        searchBtn = findViewById(R.id.btnSearchEP);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());

                List userInfo = dbHandler.readAllInfo(search.getText().toString());

                if(userInfo.isEmpty()){
                    Toast.makeText(EditProfile.this, "No such a User!", Toast.LENGTH_SHORT).show();
                    search.setText(null);

                }else{

                    Toast.makeText(EditProfile.this, "User found!", Toast.LENGTH_SHORT).show();

                    dob.setText(userInfo.get(1).toString());
                    password.setText(userInfo.get(2).toString());

                    if(userInfo.get(3).toString().equals("Male")){

                        male.setChecked(true);

                    }else{

                        female.setChecked(true);

                    }

                }

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());


                String US = search.getText().toString();
                String dateOfBirth = dob.getText().toString();
                String PS = password.getText().toString();
                String gender;

                if(male.isChecked()){
                    gender = "Male";
                }
                else{
                    gender = "Female";
                }


                boolean status = dbHandler.updateInfo(US,dateOfBirth,PS,gender);

                if(status == true){

                    Toast.makeText(EditProfile.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(EditProfile.this, "Update Failed!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());

                int deleteRowId = dbHandler.deleteInfo(search.getText().toString());

                if(deleteRowId >=1) {

                    Toast.makeText(EditProfile.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(EditProfile.this, "Deleted Failed!", Toast.LENGTH_SHORT).show();

                }

                search.setText(null);
                dob.setText(null);
                password.setText(null);
                male.setChecked(false);
                female.setChecked(false);

            }
        });

    }
}