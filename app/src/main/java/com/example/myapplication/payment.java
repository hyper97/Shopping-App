package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class payment extends AppCompatActivity {

    Button button2;
    EditText editText14, editText7, editText10, editText12, editText13; //7-cardnumber, 10-expire, 12-cvv, 14-first, 13-last
 //   Switch switch4, switch2;
    DatabaseReference dbref;
    Payments payment, payment2;
    Switch swicth2;

    private void clearcontrols() {
        editText13.setText("");
        editText14.setText("");
        editText7.setText("");
        editText10.setText("");
        editText12.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        editText13 = findViewById(R.id.editText13);//last name
        editText14 = findViewById(R.id.editText14);//first name
        editText7 = findViewById(R.id.editText7);//card number
        editText10 = findViewById(R.id.editText10);//expire
        editText12 = findViewById(R.id.editText12);//cvv
        swicth2 = findViewById(R.id.switch2);// default
        // switch4 = findViewById(R.id.switch4);//save this card


        payment = new Payments();
        payment2 = new Payments();
        swicth2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseReference redRef = FirebaseDatabase.getInstance().getReference().child("Payments").child("payment2");
                redRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            editText7.setText(dataSnapshot.child("cardno").getValue().toString());
                            editText10.setText(dataSnapshot.child("expdate").getValue().toString());
                            editText12.setText(dataSnapshot.child("cvv").getValue().toString());
                            editText14.setText(dataSnapshot.child("fname").getValue().toString());
                            editText13.setText(dataSnapshot.child("lname").getValue().toString());
                        } else {

                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });

                button2 = findViewById(R.id.button2);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        dbref = FirebaseDatabase.getInstance().getReference().child("Payments");

                        try {
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(payment.this);

                                builder.setMessage("Are you sure ?")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
                                                clearcontrols();
                                                Intent intent = new Intent(payment.this, Home.class);
                                                startActivity(intent);
                                            }
                                        }).setNegativeButton("Cancel", null);

                                AlertDialog alert = builder.create();
                                alert.show();


                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }
    public static boolean validateCVV(String cvv) {
        if (cvv.equals("") || cvv.length() == 3 ) return false;

        else
            return true;

    }


}

