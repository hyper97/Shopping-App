package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cartupdate extends AppCompatActivity {

   // TextView cuptitle, textView12, textView15;
   // Button button15, button16;
//    EditText editText, editText2;
        CartData Data;
        DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartupdate);

        final TextView cuptitle = findViewById(R.id.cuptitle);//title
        final TextView textView12 = findViewById(R.id.textView12); //description
        final TextView textView15 = findViewById(R.id.textView15); // price

        final EditText editText = findViewById(R.id.editText);   //size
        final EditText editText2 = findViewById(R.id.editText2); // color

        Button button15 = findViewById(R.id.button15);//cancel
        Button button16 = findViewById(R.id.button16);//update

        Data = new CartData();

        DatabaseReference redRef = FirebaseDatabase.getInstance().getReference().child("cart").child("Data");
        redRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    cuptitle.setText(dataSnapshot.child("title").getValue().toString());
                    textView12.setText(dataSnapshot.child("description").getValue().toString());
                    textView15.setText(dataSnapshot.child("price").getValue().toString());
                    editText.setText(dataSnapshot.child("size").getValue().toString());
                    editText2.setText(dataSnapshot.child("color").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(cartupdate.this, Cart.class);
                startActivity(intent);
            }
        });

        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference UpRef = FirebaseDatabase.getInstance().getReference().child("cart");
                UpRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Data")) ;
                        Data.setSize(editText.getText().toString().trim());
                        Data.setColor(editText2.getText().toString().trim());
                        Data.setPrice(textView15.getText().toString().trim());
                        Data.setDescription(textView12.getText().toString().trim());
                        Data.setTitle(cuptitle.getText().toString().trim());

                        dbRef = FirebaseDatabase.getInstance().getReference().child("cart").child("Data");
                        dbRef.setValue(Data);
                        Toast.makeText(getApplicationContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(cartupdate.this,Cart.class);
                        startActivity(intent);
                        //clearcontrols();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
