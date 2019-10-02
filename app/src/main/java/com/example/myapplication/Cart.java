package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cart extends AppCompatActivity {

    TextView textView7,textView9,textView15,ctitle,cclr,cprice,csize;
    CheckBox ccheckbox;
    DatabaseReference dbref;
    Button button7;
    ImageButton button4;
    BottomNavigationView bottomnav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        ccheckbox = findViewById(R.id.ccheckbox);
        ctitle = findViewById(R.id.ctitle);
        cprice = findViewById(R.id.cprice);
        cclr = findViewById(R.id.cclr);
        csize = findViewById(R.id.csize);
        button4 = findViewById(R.id.button4);

        registerForContextMenu(ccheckbox);


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

        DatabaseReference redRef = FirebaseDatabase.getInstance().getReference().child("cart").child("Data");
        redRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    ctitle.setText(dataSnapshot.child("title").getValue().toString());
                    cclr.setText(dataSnapshot.child("color").getValue().toString());
                    csize.setText(dataSnapshot.child("size").getValue().toString());
                    cprice.setText(dataSnapshot.child("price").getValue().toString());

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


            button7=findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this,shipping.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("cart");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Data") && ccheckbox.isChecked()){
                            dbref = FirebaseDatabase.getInstance().getReference().child("cart").child("Data");
                            dbref.removeValue();
                            clearcontrols();
                            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();

                        }
                        else
                            Toast.makeText(getApplicationContext(), "Please select Item to delete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.cartmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem cup) {
        switch (cup.getItemId()){
            case R.id.cupdate:
                Intent intentup = new Intent(Cart.this,cartupdate.class);
                startActivity(intentup);
                Toast.makeText(this,"Update is selected",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(cup);
    }

    private void clearcontrols(){
        cprice.setText("");
        csize.setText("");
        ctitle.setText("");
        cclr.setText("");
    }
}
