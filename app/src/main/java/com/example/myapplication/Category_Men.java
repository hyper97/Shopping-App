package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Category_Men extends AppCompatActivity {

    Button button, icancel,iupdate;
    ImageView imageView10;
    TextView textView11,textView2;
    EditText editText3;
    BottomNavigationView bottomnav;
    String imageLink = null;
    DatabaseReference dbref;
    Upload item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__men);

        imageView10 = findViewById(R.id.imageView10);
        icancel = findViewById(R.id.icancel);
        iupdate = findViewById(R.id.iupdate);
        //textView2 = findViewById(R.id.textView2);
        //textView11 = findViewById(R.id.textView11);
        //editText3 = findViewById(R.id.editText3);
        final EditText editText3 = findViewById(R.id.editText3);
        final EditText textView11 = findViewById(R.id.textView11);
        final EditText textView2 = findViewById(R.id.textView2);

        item = new Upload();


        Intent intent = getIntent();
        imageLink = intent.getStringExtra("image");
        Uri uri = Uri.parse(imageLink);
        Glide.with(Category_Men.this).load(uri).into(imageView10);
        bottomnav = (BottomNavigationView) findViewById(R.id.bottomnav);

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

        icancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category_Men.this,PostedAds.class);
                startActivity(intent);
            }
        });



        DatabaseReference redRef = FirebaseDatabase.getInstance().getReference().child("uploads").child("-LpR4LL53VXLLq5AG0L3");
        redRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    editText3.setText(dataSnapshot.child("mprice").getValue().toString());
                    textView11.setText(dataSnapshot.child("mdescription").getValue().toString());
                    textView2.setText(dataSnapshot.child("mtitle").getValue().toString());
            }
                else {
                    Toast.makeText(getApplicationContext(),"No Source to Display",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        iupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference UpRef = FirebaseDatabase.getInstance().getReference().child("uploads");
                UpRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild("-LpR4LL53VXLLq5AG0L3")) ;

                            item.setMtitle(textView2.getText().toString().trim());
                            item.setMdescription(textView11.getText().toString().trim());
                            item.setMprice(editText3.getText().toString().trim());
                            item.getmImageUrl();

                        dbref = FirebaseDatabase.getInstance().getReference().child("uploads").child("-LpR2cC03uaugZwOxXBl");
                        dbref.setValue(item);
//                        clearcontrols();

                        Toast.makeText(getApplicationContext(), "Updated Succesfully", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        }


        });

    }
//    private void clearcontrols(){
//        editText3.setText("");
//        textView11.setText("");
//        textView2.setText("");
//
//   }
}















