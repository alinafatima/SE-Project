package com.example.alina.seprojecttracker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity{
    Button b1;
    Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String email = " ";
        SharedPreferences prefs = getSharedPreferences("SEProjectTracker", MODE_PRIVATE);
        email = prefs.getString("identifierString", "no email");
        DatabaseReference myRef = database.getReference("users").child(email);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String myname, mydesignation, mynumber, myemail;
                    user myuser = dataSnapshot.getValue(user.class);
                    myname=myuser.username;
                    mydesignation=myuser.designation;
                    mynumber=myuser.phoneNumber;
                    myemail=myuser.email;
                TextView namee=(TextView) findViewById(R.id.name);
                namee.setText(myname);
                TextView designat=(TextView) findViewById(R.id.designation);
                designat.setText(mydesignation);
                TextView num=(TextView) findViewById(R.id.number);
                num.setText(mynumber);
                TextView emaill=(TextView) findViewById(R.id.email);
                emaill.setText(myemail);



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("rabiah", "Failed to read value.", error.toException());


            }
        });


    }
    
}