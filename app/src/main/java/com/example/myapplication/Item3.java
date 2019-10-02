package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Size;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class Item3 extends AppCompatActivity {
    Button button6, button8, pay,button13;

    DatabaseReference dbref;
    //Upload cart;
    CartData cart,Data;
    FirebaseStorage stref;
    TextView textView5, textView6,textView9;
    RadioGroup clgrp,size;
    RadioButton radioButton5,radioButton6,radioButton4,radioButton7,radioButton10,radioButton8;
    ImageView imageView;
    String imageLink = null;
    RecyclerView recycler_view;
    private ImageAdapter mimageadapter;
    private List<Upload> mUpload;
    private ImageAdapter mAdapter;
    BottomNavigationView bottomnav;
    boolean isBlue,isBlack = false;
    boolean isSmall,isMedium,isLarge = false;

    Upload up1,upload;
    String colorr,siz = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item3);

        recycler_view = findViewById(R.id.recycler_view);
        bottomnav = (BottomNavigationView) findViewById(R.id.bottomnav);
        textView5 = findViewById(R.id.textView5);//price
        clgrp = findViewById(R.id.clgrp);
        textView6 = findViewById(R.id.textView6);//title
        textView9 = findViewById(R.id.textView9);//description
        imageView = findViewById(R.id.imageView);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        radioButton6 = findViewById(R.id.radioButton6);
        radioButton8 = findViewById(R.id.radioButton8);
        radioButton7 = findViewById(R.id.radioButton7);
        radioButton10 = findViewById(R.id.radioButton10);
      //  button13 = findViewById(R.id.button13);

        Intent intent = getIntent();
        imageLink = intent.getStringExtra("image");

        //Color Group RadioButtonGroup
        clgrp.check(R.id.radioButton5);
        radioButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBlue = true;
                isBlack = false;
                //Toast.makeText(Item1.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBlack = true;
                isBlue = false;

                //Toast.makeText(Item1.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });


        clgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.radioButton5:
                        //cart.setColor(radioButton5.getText().toString().trim());
                        colorr = radioButton5.getText().toString().trim();
                        break;


                    case R.id.radioButton4:
                        //cart.setColor(radioButton4.getText().toString().trim());
                        colorr = radioButton4.getText().toString().trim();
                        break;
                }
            };
        });

        size = findViewById(R.id.size);

        clgrp.check(R.id.radioButton10);
        radioButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSmall = true;
                isMedium = false;
                isLarge = false;
                //Toast.makeText(Item1.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        radioButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMedium= true;
                isSmall = false;
                isLarge = false;
                //Toast.makeText(Item1.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        radioButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isLarge = true;
                isMedium = false;
                isSmall = false;
                //Toast.makeText(Item1.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });


//        mUpload = new ArrayList<>();
//        mAdapter = new ImageAdapter(Item1.this, mUpload);
//        recycler_view.setAdapter(mAdapter);

        upload = new Upload();
        up1 = new Upload();

        Data = new CartData();

        Uri uri = Uri.parse(imageLink);
        Glide.with(Item3.this).load(uri).into(imageView);

        DatabaseReference redRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("-LpP8kE7wTk-A480Iz3_");
        redRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    textView6.setText(dataSnapshot.child("mtitle").getValue().toString());
                    textView9.setText(dataSnapshot.child("mprice").getValue().toString());
                    textView5.setText(dataSnapshot.child("mdescription").getValue().toString());

                } else {
                    Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pay = (Button) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Item3.this, payment.class);
                startActivity(intent);
            }
        });

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cart = new CartData();
                dbref = FirebaseDatabase.getInstance().getReference().child("cart");
                size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.radioButton7:
                                cart.setSize(radioButton7.getText().toString().trim());

                            case R.id.radioButton8:
                                cart.setSize(radioButton8.getText().toString().trim());


                            case R.id.radioButton10:
                                cart.setSize(radioButton10.getText().toString().trim());
                        }
                    }

                });

                try {
                    if (colorr.equals("")) {
                        if (isBlue) {
                            cart.setColor("Blue");
                        } else if (isBlack) {
                            cart.setColor("Black");
                        } else {
                            cart.setColor("Black");
                        }
                    }
                    else
                    {
                        cart.setColor(colorr);
                    }
                }
                catch (Exception e)
                {
                    cart.setColor("Black");
                }


                try {
                    if (siz.equals("")) {
                        if (isMedium) {
                            cart.setSize("Medium");
                        } else if (isSmall) {
                            cart.setSize("Small");
                        } else if (isLarge) {
                            cart.setSize("Large");
                        } else {
                            cart.setSize("Medium");
                        }
                    }
                    else
                    {
                        cart.setSize(siz);
                    }
                }
                catch (Exception e)
                {
                    cart.setSize("Medium");
                }





                cart.setTitle(textView6.getText().toString().trim());
                cart.setDescription(textView5.getText().toString().trim());
                cart.setPrice(textView9.getText().toString().trim());
                dbref.child("Data").setValue(cart);
                Toast.makeText(getApplicationContext(), "Data Added Succesfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Item3.this,Cart.class);
                startActivity(intent);

            }

        });
        bottomnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                if(menuItem.getItemId() == R.id.nav_home)
                {
                    Intent intent = new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                }
                if(menuItem.getItemId() == R.id.nav_list)
                {
                    Intent intent = new Intent(getApplicationContext(),PostedAds.class);
                    startActivity(intent);
                }
                if(menuItem.getItemId() == R.id.nav_cart)
                {
                    Intent intent = new Intent(getApplicationContext(),Cart.class);
                    startActivity(intent);
                }
                if(menuItem.getItemId() == R.id.nav_account)
                {
                    Intent intent = new Intent(getApplicationContext(),account.class);
                    startActivity(intent);
                }
                return false;
            }
        });

    }
}


