package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class additems extends AppCompatActivity  {

    private static final int PICK_IMAGE_REQUEST = 1;

    private void clearcontorls(){
        title.setText("");
        price.setText("");
        description.setText("");
        image.clearFocus();

    }

    Button choose,view;
    ProgressBar progressBar;
    private Uri uploadUri;
    private StorageTask mUploadtask;
    StorageReference stref;
    DatabaseReference dbref;
    EditText title,price,description;
    ImageView image;
    BottomNavigationView bottomnav;
    Button button11;
    final String str = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additems);

        choose = findViewById(R.id.choose);
        progressBar = findViewById(R.id.progressBar);
        image = findViewById(R.id.upload); //Image
        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        view = findViewById(R.id.view);
        description = findViewById(R.id.description);
        bottomnav = (BottomNavigationView) findViewById(R.id.bottomnav);
        button11 = findViewById(R.id.button11) ;

        stref = FirebaseStorage.getInstance().getReference("uploads");
        dbref = FirebaseDatabase.getInstance().getReference("uploads");

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

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });



        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 if (TextUtils.isEmpty(price.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter Title", Toast.LENGTH_SHORT).show();

                 else if (TextUtils.isEmpty(description.getText().toString()))
                     Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_SHORT).show();

                 else if (TextUtils.isEmpty(title.getText().toString()))
                     Toast.makeText(getApplicationContext(), "Please Enter Price!", Toast.LENGTH_SHORT).show();

                else if(mUploadtask != null && mUploadtask.isInProgress()){
                    Toast.makeText(additems.this,"Upload in progress",Toast.LENGTH_SHORT).show();
                }


                else {
                    uploadFile();
                }
//                Intent intent = new Intent(additems.this,PostedAds.class);
//                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openImageActivity();
            }
        });


    }
    private void openFileChooser(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                    && data!= null && data.getData() != null){
                uploadUri = data.getData();

            Picasso.with(this).load(uploadUri).into(image);
          //  upload.setImageURI(uploadUri);
        }
    }

    private String getFileExtention(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile(){

         if(uploadUri != null){
            StorageReference fileref   = stref.child(System.currentTimeMillis()
                    + "." + getFileExtention(uploadUri));
            mUploadtask=fileref.putFile(uploadUri)
                     .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         private static final String TAG ="additemsActivity";
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },500);

                    Toast.makeText(additems.this,"Upload Successful",Toast.LENGTH_LONG).show();
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString());
                    Upload postitem = new Upload(title.getText().toString().trim(),description.getText().toString().trim(),price.getText().toString().trim(),
                                    downloadUrl.toString());

                    String uploadId = dbref.push().getKey();
                    dbref.child(uploadId).setValue(postitem);
                    clearcontorls();

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                             Toast.makeText(additems.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });

        }
         else{
             Toast.makeText(this,"No file Selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void openImageActivity(){
        Intent intent = new Intent(additems.this,PostedAds.class);
        startActivity(intent);

    }

}
