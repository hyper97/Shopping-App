package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class paymentview extends AppCompatActivity {

    Button button9,button5,delete;
    TextView textView22,textView23;
    DatabaseReference dbRef;
    Payments payment,payment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentview);

        textView22 = findViewById(R.id.textView22);
        textView23 = findViewById(R.id.textView23);

        button5 = findViewById(R.id.button5);
        button9 = findViewById(R.id.button9);
        delete = findViewById(R.id.delete);

        //////////////////////////////////////////READING DATA TO CARD VIEW///////////////////
        DatabaseReference redRef = FirebaseDatabase.getInstance().getReference().child("Payments").child("payment2");
        redRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    textView22.setText(dataSnapshot.child("fname").getValue().toString());
                    textView23.setText(dataSnapshot.child("cardno").getValue().toString());
            }
                        else {
                Toast.makeText(getApplicationContext(),"No Source to Display",Toast.LENGTH_SHORT).show();
            }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(paymentview.this,Add_new_card.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(paymentview.this,Payment_Update.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Payments");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("payment2")) {
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Payments").child("payment2");
                            dbRef.removeValue();
                            clearcontorls();
                            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            clearcontorls();

                        } else
                            Toast.makeText(getApplicationContext(), "Invalid phone number", Toast.LENGTH_SHORT).show();


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        }

        private void clearcontorls (){
        textView22.setText("");
        textView23.setText("");
    }

    }


