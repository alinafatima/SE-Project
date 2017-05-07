package com.example.acer.tasks;

import android.support.v7.app.AppCompatActivity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class Tasks extends AppCompatActivity implements OnItemClickListener {
    String[] tasks_given;
    TypedArray profile_pic;
    List<com.example.acer.tasks.RowItem> rowItems;
    ListView mylistview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        rowItems = new ArrayList<com.example.acer.tasks.RowItem>();
        tasks_given = getResources().getStringArray(R.array.tasks_given);
        profile_pic = getResources().obtainTypedArray(R.array.profile_pics);

        for(int i=0; i< tasks_given.length;i++){
            com.example.acer.tasks.RowItem item = new com.example.acer.tasks.RowItem(tasks_given[i],profile_pic.getResourceId(i,-1));
            rowItems.add(item);
        }
        mylistview = (ListView) findViewById(R.id.list_view);
        com.example.acer.tasks.CustomAdapter adapter = new com.example.acer.tasks.CustomAdapter(this,rowItems);
        mylistview.setAdapter(adapter);
        mylistview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String tasks = rowItems.get(position).getTasks_given();
        Toast.makeText(getApplicationContext(), "" + tasks, Toast.LENGTH_SHORT).show();
    }
}
