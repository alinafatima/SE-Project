package com.example.alina.seprojecttracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class SignUpEmployer extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;


    private EditText etEmail, etName, etPassword, etDesignation, etCompanyName, etPhoneNumber, etconfirmPassword;
    private TextView errorname,errorReEnterPassword, errorEmail, errorDesignation, errorCompanyName, errorPhoneNumber, errorPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_employer);

        etEmail = (EditText) findViewById(R.id.email);
        etName = (EditText) findViewById(R.id.fname);
        etPassword = (EditText) findViewById(R.id.password);
        etconfirmPassword = (EditText) findViewById(R.id.reEnterpassword);
        etCompanyName = (EditText) findViewById(R.id.company);
        etPhoneNumber = (EditText) findViewById(R.id.number);
        etDesignation = (EditText) findViewById(R.id.designation);

        errorname=(TextView)findViewById(R.id.errorname);
        errorEmail=(TextView)findViewById(R.id.erroremail);
        errorPassword=(TextView)findViewById(R.id.errorpassword);
        errorReEnterPassword=(TextView)findViewById(R.id.errorReEnterPassword);
        errorCompanyName=(TextView)findViewById(R.id.errorcname);
        errorDesignation=(TextView)findViewById(R.id.errordesignation);
        errorPhoneNumber=(TextView)findViewById(R.id.errornumber);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    public void btnignupOnClick(View view) {
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String myname = etName.getText().toString();
        final String confirmPassword = etconfirmPassword.getText().toString();
        final String companyname = etCompanyName.getText().toString();
        final String designation = etDesignation.getText().toString();
        final String phoneNumber = etPhoneNumber.getText().toString();
        final String Status = "Employer";
        Log.d("Alina", password);
        Log.d("Alina", confirmPassword);
        int errors = 0;
        if (myname.matches("")) {
            errorname.setVisibility(View.VISIBLE);
            errors = 1;
        }
        else
        {
            errorname.setVisibility(View.GONE);
        }

        if (password.matches("") || password.length() < 8) {
            errorPassword.setVisibility(View.VISIBLE);
            errors = 1;
        }
        else
        {
            errorPassword.setVisibility(View.GONE);
        }
        if (email.matches("")) {
            errorEmail.setVisibility(View.VISIBLE);
            errors = 1;
        }
        else
        {
            errorEmail.setVisibility(View.GONE);
        }

        if (companyname.matches("")) {
            errorCompanyName.setVisibility(View.VISIBLE);
            errors = 1;
        }
        else
        {
            errorCompanyName.setVisibility(View.GONE);
        }

        if (designation.matches(""))
        {
            errorDesignation.setVisibility(View.VISIBLE);
            errors = 1;
        }
        else
        {
            errorDesignation.setVisibility(View.GONE);
        }


        if(phoneNumber.matches("")) {
            errorPhoneNumber.setVisibility(View.VISIBLE);
            errors = 1;
        }
        else
        {
            errorPhoneNumber.setVisibility(View.GONE);
        }

        if (password.equals( confirmPassword))
        {
            errorReEnterPassword.setVisibility(View.GONE);
        }
        else
        {    errorReEnterPassword.setVisibility(View.VISIBLE);
            errors = 1;

        }
        if (errors == 0) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Alina", "createUserWithEmail:onComplete:" + task.isSuccessful());
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication Success",
                                        Toast.LENGTH_SHORT).show();

                                FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
                                String userEmsil = currentFirebaseUser.getEmail();
                                SharedPreferences.Editor editor = getSharedPreferences("SEProjectTracker", MODE_PRIVATE).edit();
                                userEmsil = userEmsil.replace(".", "_");
                                editor.putString("identifierString", userEmsil);
                                editor.putString("name", myname);
                                editor.commit();


                                writeNewUser(userEmsil, myname, email, password, designation, companyname, phoneNumber, Status);
                                Intent i = new Intent(getApplicationContext(), NavigationScreen.class);
                                startActivity(i);

                            }

                            // ...
                        }
                    });

        }
    }

    private void writeNewUser(String userId, String name, String email, String myPassword, String myDesignation, String mycompanyname, String myPhone, String status)
    {
        user User = new user(name, email, myPassword, myDesignation, mycompanyname, myPhone, status);

        mDatabase.child("users").child(userId).setValue(User);
    }



    public void returntoMain(){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);

    }
}
