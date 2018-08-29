package com.example.alina.seprojecttracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NavigationScreen extends AppCompatActivity {
    private afterloginAdapter adapter;
    private RecyclerView afterlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation_screen);

        afterlogin = (RecyclerView)findViewById(R.id.afterlogin);

        adapter = new afterloginAdapter(getApplicationContext(), getData());
        afterlogin.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        afterlogin.setAdapter(adapter);
    }

    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        String[] titles = {"Map View", "Profile",  "Employees"};
        for(int i = 0; i<titles.length;i++){
            Information current = new Information();
            current.title=titles[i];
            data.add(current);
        }
        return data;
    }

    @Override
    public void onBackPressed() {
    }
}
