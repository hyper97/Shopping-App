package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class account extends AppCompatActivity {
    BottomNavigationView bottomnav;
    TextView editText,editText3,editText4,editText2,editText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        editText=findViewById(R.id.textView37);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(account.this,acc_details.class);
                startActivity(intent);
            }
        });
        editText3 = findViewById(R.id.textView36);
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(account.this,paymentview.class);
                startActivity(intent);
            }
        });
        editText5 = findViewById(R.id.textView39);
        editText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(account.this,Main_Login.class);
                startActivity(intent);
            }
        });
        editText4=findViewById(R.id.textView35);
        editText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(account.this,PostedAds.class);
                startActivity(intent);
            }
        });

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

    }
}
