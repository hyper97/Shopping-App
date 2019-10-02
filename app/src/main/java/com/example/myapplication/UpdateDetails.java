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

public class UpdateDetails extends AppCompatActivity {

    DatabaseReference dbref;
    TextView textView8,textView19,textView10,textView20,textView14;
    Button button3,button4;
    User user,user1;

    private void clearcontrols() {
        textView10.setText("");
        textView14.setText("");
        textView19.setText("");
        textView20.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        textView8 = findViewById(R.id.textView8); //name upper
        textView10 = findViewById(R.id.textView10);//Email
        textView20 = findViewById(R.id.textView20);//Address
        textView19 = findViewById(R.id.textView19);//Fulllname
        textView14 = findViewById(R.id.textView14);//phone
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        user = new User();
        user1 = new User();

        //READING DATA
        DatabaseReference redRef = FirebaseDatabase.getInstance().getReference().child("User").child("user1");
        redRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    textView8.setText(dataSnapshot.child("username").getValue().toString());
                    textView10.setText(dataSnapshot.child("email").getValue().toString());
                    textView19.setText(dataSnapshot.child("username").getValue().toString());
                    textView20.setText(dataSnapshot.child("address").getValue().toString());
                    textView14.setText(dataSnapshot.child("mobile").getValue().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference UpRef = FirebaseDatabase.getInstance().getReference().child("User");
                UpRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("user1")) ;
                        try {
                            user.setUsername(textView19.getText().toString().trim());
                            user.setEmail(textView10.getText().toString().trim());
                            user.setMobile(Integer.parseInt(textView14.getText().toString().trim()));
                            user.setAddress(textView20.getText().toString().trim());

                            dbref = FirebaseDatabase.getInstance().getReference().child("User").child("user1");
                            dbref.setValue(user);
                            clearcontrols();
                            Toast.makeText(getApplicationContext(), "Updated the details", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(UpdateDetails.this,acc_details.class);
                            startActivity(intent);


                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Invalid phone number", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearcontrols();

            }
        });

    }
}


