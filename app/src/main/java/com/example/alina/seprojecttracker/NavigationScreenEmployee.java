package com.example.alina.seprojecttracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NavigationScreenEmployee extends AppCompatActivity {
    private afterloginAdapterEmployee adapter;
    private RecyclerView afterloginEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation_screen);

        afterloginEmployee = (RecyclerView)findViewById(R.id.afterlogin);

        adapter = new afterloginAdapterEmployee(getApplicationContext(), getData());
        afterloginEmployee.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        afterloginEmployee.setAdapter(adapter);
    }

    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        String[] titles = {"Map View", "Profile"};
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
