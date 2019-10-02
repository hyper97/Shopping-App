package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

import java.net.URI;
import java.net.URL;
import java.util.List;


public class Item2 extends AppCompatActivity {

    DatabaseReference dbref;
    Button button6,button8,pay,button12;
    TextView textView6,textView9,textView5;
    RadioGroup clgrp,size;
    RadioButton radioButton5,radioButton6,radioButton4,radioButton7,radioButton10,radioButton8;
    CartData cart,Data;
    String imageLink = null;
    ImageView imageView;
    RecyclerView recycler_view;
    private ImageAdapter mimageadapter;
    private List<Upload> mUpload;
    private ImageAdapter mAdapter;
    BottomNavigationView bottomnav;

    boolean isBlack,isBlue,isGray = false;
    boolean isSmall,isMedium,isLarge = false;

    Upload up1,upload;
    String colorr,siz = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item2);

        recycler_view = findViewById(R.id.recycler_view);
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
        bottomnav = (BottomNavigationView) findViewById(R.id.bottomnav);
      //  button12 = findViewById(R.id.button12);

        Intent intent = getIntent();
        imageLink = intent.getStringExtra("image");
        Uri uri = Uri.parse(imageLink);
        Glide.with(Item2.this).load(uri).into(imageView);

        //Toast.makeText(this, "" + imageLink, Toast.LENGTH_SHORT).show();

        clgrp.check(R.id.radioButton5);
        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBlack = true;
                isBlue = false;
                isGray = false;
                //Toast.makeText(Item1.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        radioButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBlue = true;
                isGray = false;
                isBlack = false;
                //Toast.makeText(Item1.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        radioButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isGray = true;
                isBlack = false;
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


                    case R.id.radioButton6:
                        //cart.setColor(radioButton6.getText().toString().trim());
                        colorr = radioButton6.getText().toString().trim();
                        break;
                }
            };
        });

        size = findViewById(R.id.size);

        size.check(R.id.radioButton10);
        radioButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSmall = false;
                isMedium = true;
                isLarge = false;
                //Toast.makeText(Item1.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        radioButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMedium= false;
                isSmall = false;
                isLarge = true;
                //Toast.makeText(Item1.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        radioButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isLarge = false;
                isMedium = false;
                isSmall = true;
                //Toast.makeText(Item1.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        size = findViewById(R.id.size);
        clgrp = findViewById(R.id.clgrp);



        cart = new CartData();
        Data = new CartData();

        DatabaseReference redRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("-LpOlCTov5tVsDika-Gg");
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

            pay = (Button)findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Item2.this,payment.class);
                startActivity(intent);
            }
        });

        button6 = (Button)findViewById(R.id.button14);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbref = FirebaseDatabase.getInstance().getReference().child("cart");
                clgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {

                        switch (i){
                            case R.id.radioButton5:
                                cart.setColor(radioButton5.getText().toString().trim());

                                break;
                            case R.id.radioButton4:
                                cart.setColor(radioButton4.getText().toString().trim());


                                break;
                            case R.id.radioButton6:
                                cart.setColor(radioButton6.getText().toString().trim());
                        }
                    };
                });

                size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.radioButton7:
                                cart.setSize(radioButton7.getText().toString().trim());

                                break;
                            case R.id.radioButton8:
                                cart.setSize(radioButton8.getText().toString().trim());


                                break;
                            case R.id.radioButton10:
                                cart.setSize(radioButton10.getText().toString().trim());
                        }
                    }

                });

                try {
                    if (colorr.equals("")) {
                        if (isBlack) {
                            cart.setColor("Black");
                        } else if (isGray) {
                            cart.setColor("Gray");
                        } else if (isBlue) {
                            cart.setColor("Blue");
                        } else {
                            cart.setColor("Blue");
                        }
                    }
                    else
                    {
                        cart.setColor(colorr);
                    }
                }
                catch (Exception e)
                {
                    cart.setColor("Blue");
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

                Intent intent = new Intent(Item2.this,Cart.class);
                startActivity(intent);
            };
//
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

        };


    }

