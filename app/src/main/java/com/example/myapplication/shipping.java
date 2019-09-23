package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class shipping extends AppCompatActivity {

    Button button;
    EditText editText17,editText16,editText15,editText11,editText8,editText9,editText6;
    DatabaseReference dbref;
    Payments shipping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        editText11 = findViewById(R.id.editText11);
        editText17 = findViewById(R.id.editText17);
        editText15 = findViewById(R.id.editText15);
        editText6 = findViewById(R.id.editText6);
        editText8 = findViewById(R.id.editText8);
        editText16 = findViewById(R.id.editText16);


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shipping = new Payments();
                dbref = FirebaseDatabase.getInstance().getReference().child("Payments");


                if (TextUtils.isEmpty(editText17.getText().toString()))
                    Toast.makeText(getApplicationContext(), "please Enter Postal Code", Toast.LENGTH_SHORT).show();

                else if (TextUtils.isEmpty(editText6.getText().toString()))
                    Toast.makeText(getApplicationContext(), "please Enter City", Toast.LENGTH_SHORT).show();

                else if (TextUtils.isEmpty(editText8.getText().toString()))
                    Toast.makeText(getApplicationContext(), "please Enter Address", Toast.LENGTH_SHORT).show();

                else if (TextUtils.isEmpty(editText16.getText().toString()))
                    Toast.makeText(getApplicationContext(), "please Enter Country", Toast.LENGTH_SHORT).show();

                else if (TextUtils.isEmpty(editText11.getText().toString()))
                    Toast.makeText(getApplicationContext(), "please Enter Name", Toast.LENGTH_SHORT).show();

                else if (TextUtils.isEmpty(editText16.getText().toString()))
                    Toast.makeText(getApplicationContext(), "please Enter Phonenumber", Toast.LENGTH_SHORT).show();

                else {
                    shipping.setFullname(editText11.getText().toString().trim());
                    shipping.setPhone(editText16.getText().toString().trim());
                    shipping.setAddress(editText16.getText().toString().trim());
                    shipping.setCity(editText6.getText().toString().trim());
                    shipping.setCountry(editText16.getText().toString().trim());
                    shipping.setPostal(editText17.getText().toString().trim());


                    dbref.push().setValue(shipping);

                    Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
                    //clearcontrols();
                    Intent intent = new Intent(shipping.this, payment.class);
                    startActivity(intent);
                }
            }

        });
    }

    }



