package com.example.alina.seprojecttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Employees extends AppCompatActivity implements OnClickListener {

    private employeesAdapter adapter;
    private RecyclerView employees;

    final List<Information> datanew = new ArrayList<>();

    Button goNavigation;
    Button addEmployee;
    Button deleteEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        employees = (RecyclerView) findViewById(R.id.employees);

        adapter = new employeesAdapter(getApplicationContext(), datanew);

        employees.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        employees.setAdapter(adapter);

        goNavigation = (Button) findViewById(R.id.navigationbutton);
        goNavigation.setOnClickListener(this);

        addEmployee = (Button) findViewById(R.id.addbutton);
        addEmployee.setOnClickListener(this);

        deleteEmployee = (Button) findViewById(R.id.deletebutton);
        deleteEmployee.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (datanew != null) {
            datanew.clear();
            getData();
        }
    }

    public List<Information> getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user myuser = new user();
                    myuser = snapshot.getValue(user.class);

                    Information current = new Information();
                    Log.d("Rabiah", "Value ided: " + myuser.username);
                    current.title = myuser.username;
                    datanew.add(current);
                    Log.d("Rabiah", "Value is: " + myuser.username);

                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("rabiah", "Failed to read value.", error.toException());


            }
        });
        List<Information> data = new ArrayList<>();

        return datanew;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.navigationbutton) {
            Intent i = new Intent(this, NavigationScreen.class);
            startActivity(i);
        }
        if (v.getId() == R.id.addbutton) {
            Intent i = new Intent(this, AddEmployee.class);
            startActivity(i);
        }
        if (v.getId() == R.id.deletebutton) {
            Intent i = new Intent(this, DeleteEmployee.class);
            startActivity(i);
        }
    }

}