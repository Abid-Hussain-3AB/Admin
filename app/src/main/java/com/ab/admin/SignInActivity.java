package com.ab.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.ab.admin.Admin.AdminActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.IOException;


public class SignInActivity extends AppCompatActivity {
    Button SignIn;
    EditText etSignInName, etSignInPassword;
    private DatabaseReference aFirebaseDatabase;
    public static final String filename = "login";
    public static final String userName = "username";
    public static final String password = "password";
    public static final String nameq = "Abid";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        SignIn = findViewById(R.id.button4);
        etSignInName = findViewById(R.id.etSignInPhone);
        etSignInPassword = findViewById(R.id.etSignInPassword);
        sharedPreferences = getSharedPreferences(filename,Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName)){
            Intent intent = new Intent(SignInActivity.this, AdminActivity.class);
            startActivity(intent);
        }
        aFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
        SignIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (IsInterConnected())
                {
                    String Phone = etSignInName.getText().toString();
                String UPassword = etSignInPassword.getText().toString();
                if ((TextUtils.isEmpty(etSignInName.getText().toString()))) {
                    Toast.makeText(SignInActivity.this, "Please enter User Name.", Toast.LENGTH_LONG).show();
                } else if ((TextUtils.isEmpty(etSignInPassword.getText().toString()))) {
                    Toast.makeText(SignInActivity.this, "Please enter Password.", Toast.LENGTH_LONG).show();
                } else {

                        aFirebaseDatabase.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(Phone)) {
                                    final String getpassword = "admin";
                                    if (getpassword.equals(UPassword)) {
                                        Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignInActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Wrong User Name or Password or Role", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(SignInActivity.this, "Wrong User Name or Password or Role", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
            }
                else {
                    Toast.makeText(SignInActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    Toast.makeText(SignInActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    boolean IsInterConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null)
        {
            if (networkInfo.isConnected())
                 return true;
            else
                return false;
        }
        else {
            return false;
        }
    }
    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }
}