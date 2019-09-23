package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.myapplication.payment.validateCVV;

public class Add_new_card extends AppCompatActivity {

    Payments payment;
    DatabaseReference dbref;
    Button button2;
    EditText editText7, editText10, editText12, editText13, editText14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        editText7 = findViewById(R.id.editText7);
        editText10 = findViewById(R.id.editText10);
        editText12 = findViewById(R.id.editText12);
        editText13= findViewById(R.id.editText13);
        editText14 = findViewById(R.id.editText14);
        button2 = findViewById(R.id.button2);
        payment = new Payments();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        dbref = FirebaseDatabase.getInstance().getReference().child("Payments");

            if (TextUtils.isEmpty(editText7.getText().toString()))
                Toast.makeText(getApplicationContext(), "please Enter CardNumber", Toast.LENGTH_SHORT).show();

            else if (TextUtils.isEmpty(editText10.getText().toString()))
                Toast.makeText(getApplicationContext(), "please Expire Date", Toast.LENGTH_SHORT).show();

            else if (TextUtils.isEmpty(editText12.getText().toString()))
                Toast.makeText(getApplicationContext(), "please Enter Security Code", Toast.LENGTH_SHORT).show();

            else if (validateCVV(editText12.getText().toString()))
                editText12.setError("Enter Valid Security Code");

            else if (TextUtils.isEmpty(editText13.getText().toString()))
                Toast.makeText(getApplicationContext(), "please Enter First Name", Toast.LENGTH_SHORT).show();

            else if (TextUtils.isEmpty(editText14.getText().toString()))
                Toast.makeText(getApplicationContext(), "please Enter Last Name", Toast.LENGTH_SHORT).show();

            else {

                payment.setCardno(Integer.parseInt(editText7.getText().toString().trim()));
                payment.setExpdate(Integer.parseInt(editText10.getText().toString().trim()));
                payment.setCvv(Integer.parseInt(editText12.getText().toString().trim()));
                payment.setLname(editText13.getText().toString().trim());
                payment.setFname(editText14.getText().toString().trim());

                dbref.child("payment2").setValue(payment);
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_new_card.this);

                builder.setMessage("Are you sure ?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
                                //  clearcontrols();
                                Intent intent = new Intent(Add_new_card.this, Home.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("Cancel", null);

                AlertDialog alert = builder.create();
                alert.show();
              }

            }
        });
    }

    public static boolean validateCVV(String cvv) {
        if (cvv.equals("") || cvv.length() == 3) return false;

        else
            return true;

    }
}
