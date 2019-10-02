package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PostedAds extends AppCompatActivity implements ImageAdapter.OnItemClickListner {
    private RecyclerView mrecyclerView;
    private ImageAdapter mAdapter;

    private FirebaseStorage mstrorage;
    private DatabaseReference dbref;
    private List<Upload> mUpload;
    private ValueEventListener mdblistner;
    final String str = null;






    BottomNavigationView bottomnav;
    private Object Bitmap_bitmap;
    public static final String EXTRA_URL ="imageurl";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_ads);


        mrecyclerView = findViewById(R.id.recycler_view);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mUpload = new ArrayList<>();
        mAdapter = new ImageAdapter(PostedAds.this,mUpload);
        mrecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(PostedAds.this  );


        mstrorage = FirebaseStorage.getInstance();
       dbref = FirebaseDatabase.getInstance().getReference("uploads");
        mdblistner= dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mUpload.clear();

                    for (DataSnapshot postsnapshot : dataSnapshot.getChildren()){
                         Upload upload = postsnapshot.getValue(Upload.class);
                         upload.setMkey(postsnapshot.getKey());
                         mUpload.add(upload);
                    }


                   mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PostedAds.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(getApplicationContext(), PostedAds.class);
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

    @Override
    public void onItemClick(int position) {
      //  Toast.makeText(this,"Just click at position: "+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        switch (position){
            case 0:
                Intent intent = new Intent(PostedAds.this,Item1.class);
                intent.putExtra("image",mUpload.get(position).getmImageUrl());
                startActivity(intent);
                break;

            case 1:
                Intent intent1 = new Intent(PostedAds.this,Item2.class);
                intent1.putExtra("image",mUpload.get(position).getmImageUrl());
                startActivity(intent1);
                break;

            case 2:
                Intent intent2 = new Intent(PostedAds.this,Item3.class);
                intent2.putExtra("image",mUpload.get(position).getmImageUrl());
                startActivity(intent2);
                break;

            case 3:
                Intent intent3 = new Intent(PostedAds.this,Item4.class);
                intent3.putExtra("image",mUpload.get(position).getmImageUrl());
                startActivity(intent3);
                break;


        }
        Toast.makeText(this,"Item Clicked: "+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateClick(int position) {
        switch (position){
            case 0:
//                Intent intent = new Intent(PostedAds.this,Category_Men.class);
//                intent.putExtra("image",mUpload.get(position).getmImageUrl());
//                startActivity(intent);
                break;
            case 1:
//                Intent intent1 = new Intent(PostedAds.this,Category_Men.class);
//                intent1.putExtra("image",mUpload.get(position).getmImageUrl());
//                startActivity(intent1);
                break;
            case 2:
//                Intent intent2 = new Intent(PostedAds.this,Category_Men.class);
//                intent2.putExtra("image",mUpload.get(position).getmImageUrl());
//                startActivity(intent2);
                break;

            case 3:
                Intent intent3 = new Intent(PostedAds.this,Category_Men.class);
                intent3.putExtra("image",mUpload.get(position).getmImageUrl());
                startActivity(intent3);
                break;
        }
    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem = mUpload.get(position);
        final String selecedKEy = selectedItem.getMkey();

        StorageReference imageref = mstrorage.getReferenceFromUrl(selectedItem.getmImageUrl());
        imageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                dbref.child(selecedKEy).removeValue();
                Toast.makeText(PostedAds.this,"Item Deleted",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbref.removeEventListener(mdblistner);
    }
}
