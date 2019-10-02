package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.ui.login.LoginActivity;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    Button signup;
    EditText username, username2, password, password2, number;
    DatabaseReference dbref;
    FirebaseAuth firebaseAuth;

    User user, user1;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.username);//email
        username2 = findViewById(R.id.username2);//username
        password = findViewById(R.id.password);//Reenter_password
        password2 = findViewById(R.id.password2);//password
        number = findViewById(R.id.number); //mobile


        signup = findViewById(R.id.signup);//button

        user = new User();//Object of user class
        user1 = new User();
        firebaseAuth = firebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dbref = FirebaseDatabase.getInstance().getReference().child("User");
                //   try {
                if (TextUtils.isEmpty(username2.getText().toString()))
                    username2.setError("Enter Correct Username");

                else if (TextUtils.isEmpty(username.getText().toString()))
                    username.setError("Enter your Email");

                else if (TextUtils.isEmpty(password2.getText().toString()))
                    password2.setError("Enter your Password");

                else if (TextUtils.isEmpty(password.getText().toString()))
                    password.setError("Enter Your Address");

                else if (TextUtils.isEmpty(number.getText().toString()))
                    number.setError("Enter Number");
                else {


                    firebaseAuth.createUserWithEmailAndPassword(username.getText().toString(), password2.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        user.setUsername(username2.getText().toString().trim());
                                        user.setEmail(username.getText().toString().trim());
                                        user.setPassword(password2.getText().toString().trim());
                                        user.setMobile(Integer.parseInt(number.getText().toString().trim()));
                                        user.setAddress(password.getText().toString().trim());

                                        dbref.child("user1").setValue(user);

                                        Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(SignUp.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }

            }
        });


    }
}








//    }
//    private boolean validateEmail() {
//        String emailInput = username.getText().toString().trim();
//
//        if (emailInput.isEmpty()) {
//            username.setError("Field can't be empty");
//            return false;
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
//            username.setError("Please enter a valid email address");
//            return false;
//        } else {
//            username.setError(null);
//            return true;
//        }
//
//    }    }

//    private boolean validateUsername() {
//        String usernameInput = username2.getText().toString().trim();
//
//        if (usernameInput.isEmpty()) {
//            username2.setError("Field can't be empty");
//            return false;
//        } else if (usernameInput.length() > 15) {
//            username2.setError("Username too long");
//            return false;
//        } else {
//            username2.setError(null);
//            return true;
//        }
//    }
//
//    private boolean validatePassword() {
//        String passwordInput = password2.getText().toString().trim();
//
//        if (passwordInput.isEmpty()) {
//            password2.setError("Field can't be empty");
//            return false;
//        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
//            password2.setError("Password too weak");
//            return false;
//        } else {
//            password2.setError(null);
//            return true;
//        }
//
//    }
//    public void confirmInput(View v) {
//        if (!validateEmail() | !validateUsername() | !validatePassword()) {
//            return;
//        }
//
//        String input = "Email: " + username.getText().toString();
//        input += "\n";
//        input += "Username: " + username2.getText().toString();
//        input += "\n";
//        input += "Password: " + password2.getText().toString();
//
//        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
//    }

//        }
