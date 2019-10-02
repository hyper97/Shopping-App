package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_Password extends AppCompatActivity {

    private EditText send_email, phone;
    private Button button2;
    private final String CHANNEL_ID = "password";
    private final int NOTIFICATION_ID = 234;
    FirebaseAuth firebaseAuth;

    //Create firebase autentication beacuse this is in fire base authentication


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);


       // phone = findViewById(R.id.phone);
        send_email = findViewById(R.id.email);
        button2 = findViewById(R.id.button2);
        firebaseAuth = FirebaseAuth.getInstance();
      //  getSupportActionBar().setTitle("Reset Password");
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String email = send_email.getText().toString().trim();

                if (email.equals("")) {
                    Toast.makeText(Forget_Password.this, "Please Enter your Email", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                 Toast.makeText(Forget_Password.this, "Password Reset Email Sent", Toast.LENGTH_SHORT).show();
                                finish();
                                notficationcall(view);
                                 startActivity(new Intent(Forget_Password.this, LoginActivity.class));

                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(Forget_Password.this, "Error in sending the password reset", Toast.LENGTH_SHORT).show();
                             }
                        }
                    });
                }
            }
        });

    }

   public void notficationcall(View view) {

       NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
       builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
       builder.setContentTitle("Notification form Wings");
       builder.setContentText("Password Reser Email has been sent");
       builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

       NotificationManagerCompat notificationmanagercompact = NotificationManagerCompat.from(this);
       notificationmanagercompact.notify(NOTIFICATION_ID, builder.build());


   }

}


