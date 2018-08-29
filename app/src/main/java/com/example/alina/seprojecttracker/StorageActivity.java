package com.example.alina.seprojecttracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        Spinner spinner = (Spinner) findViewById(R.id.spTesting);

        String name = " ";
        String email = " ";

        SharedPreferences prefs = getSharedPreferences("SEProjectTracker", MODE_PRIVATE);
        name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        email = prefs.getString("identifierString", "no email");
        Log.d("Alina", name);

        final List<String> items = new ArrayList<String>();

        items.add("dasdsa");
        items.add("saddasdsa");
        items.add("dassaddsa");
        items.add("dasdssada");
        items.add("dasdsaasda");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Yahoooo!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Alas!", Toast.LENGTH_LONG).show();
            }
        });


    }
}
