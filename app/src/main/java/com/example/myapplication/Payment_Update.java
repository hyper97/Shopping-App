package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Payment_Update extends AppCompatActivity {

    private void clearcontrols() {
        editText13.setText("");
        editText14.setText("");
        editText7.setText("");
        editText10.setText("");
        editText12.setText("");
    }

    Button button2;
    Switch switch2;
    EditText editText7,editText10,editText12,editText13,editText14;
    Payments payment,payment2;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__update);

        switch2 = findViewById(R.id.switch2);
        editText7 =findViewById(R.id.editText7);
        editText13 =findViewById(R.id.editText13);
        editText12 =findViewById(R.id.editText12);
        editText10 =findViewById(R.id.editText10);
        editText14 =findViewById(R.id.editText14);
        button2 = findViewById(R.id.button2);

        payment = new Payments();
        payment2 = new Payments();


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
                    clearcontrols();
                    Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference UpRef = FirebaseDatabase.getInstance().getReference().child("Payments");
                UpRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("payment2")) ;
                        try {
                            payment.setFname(editText14.getText().toString().trim());
                            payment.setLname(editText13.getText().toString().trim());
                            payment.setCvv(Integer.parseInt(editText12.getText().toString().trim()));
                            payment.setExpdate(Integer.parseInt(editText10.getText().toString().trim()));
                            payment.setCardno(Integer.parseInt(editText7.getText().toString().trim()));

                            dbRef = FirebaseDatabase.getInstance().getReference().child("Payments").child("payment2");
                            dbRef.setValue(payment);
                            clearcontrols();

                            Toast.makeText(getApplicationContext(), "Updated Succesfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Payment_Update.this,paymentview.class);
                            startActivity(intent);

                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

            });
        }
        });

    }
}