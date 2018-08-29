package com.example.alina.seprojecttracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class login extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private EditText etPassword, etEmail;
    private TextView errorpassword;
    public String statusofuser="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Alina", "hereee");
        setContentView(R.layout.activity_login);
        etPassword=(EditText)findViewById(R.id.password);
        etEmail=(EditText)findViewById(R.id.username);
        errorpassword=(TextView)findViewById(R.id.errorpassword);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Alina", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Alina", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    public void onClickLoginButton(View v) {

        final String password = etPassword.getText().toString();
        final String email=etEmail.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Alina", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("Alina", "signInWithEmail", task.getException());
                            errorpassword.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            //Log.d("Alina","hererere");
                            Toast.makeText(getApplicationContext(), "Authentication Successful.",
                                    Toast.LENGTH_SHORT).show();
                            //Log.d("Alina","hererere");
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                            //Log.d("Alina","hererere");
                            String userEmail = currentFirebaseUser.getEmail();

                            final SharedPreferences.Editor editor = getSharedPreferences("SEProjectTracker", MODE_PRIVATE).edit();
                            userEmail = userEmail.replace(".", "_");
                            editor.putString("identifierString", userEmail);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users").child(userEmail).child("status");
                            // Read from the database
                            Log.d("Alina",userEmail);

                            myRef.addValueEventListener(new ValueEventListener()
                            {
                                String tempValue;

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.
                                    String value = dataSnapshot.getValue(String.class);
                                    tempValue=value;
                                    statusofuser= value;
                                    Log.d("Alina", "Value is: " + value);
                                    editor.commit();
                                    Log.d("Alina", "gotsofar");
                                    Log.d("Alina", statusofuser);
                                    if(statusofuser.equals("Employer")) {
                                        Log.d("Alina","heyyy");
                                        Intent i = new Intent(getApplicationContext(), NavigationScreen.class);
                                        startActivity(i);
                                    }
                                    else if(statusofuser.equals("Employee"))
                                    {
                                        Intent i = new Intent(getApplicationContext(), NavigationScreenEmployee.class);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.d("Alina", "Failed to read value.", error.toException());
                                }

                            });

                        }

                        // ...
                    }
                });





    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }



    public void returntoMain(){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);

    }


}
