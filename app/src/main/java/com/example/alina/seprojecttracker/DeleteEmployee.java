package com.example.alina.seprojecttracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteEmployee extends AppCompatActivity {

    private deleteEmployeesAdapter adapter;
    private RecyclerView employees;
    private Button btnDelete;

    final List<Information> datanew = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);

        employees = (RecyclerView)findViewById(R.id.employees);
        btnDelete = (Button) findViewById(R.id.pressdelete);

        adapter = new deleteEmployeesAdapter(getApplicationContext(), datanew);

        employees.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        employees.setAdapter(adapter);

        getData();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                for (int i = 0; i < datanew.size(); i++){
                    if (datanew.get(i).isChecked){
                        String email = datanew.get(i).getEmail();
                        email = email.replace(".", "_");
                        myRef.child(email).removeValue();
                    }
                }

                Toast.makeText(getApplicationContext(), "Employees deleted successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public  List< Information> getData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.d("Rabiah", "Value idesd");

                    user myuser = new user();


                    myuser= snapshot.getValue(user.class);

                    Information current = new Information();
                    Log.d("Rabiah", "Value ided: " + myuser.username);
                    current.title=myuser.username;
                    current.setEmail(myuser.email);
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
        /*
        String[] titles = {"Abeera Ayesha Riaz", "Anza Qadir", "Fatima Javaid", "Rabiah Arshad", "Fatima Bashir", "Muhammad Usman"};
        for(int i = 0; i<titles.length;i++){
            Information current = new Information();
            current.title=titles[i];
            data.add(current);
        }
        */
        return datanew;
    }

}