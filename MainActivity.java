package com.example.acer.hlloworld;

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

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    String[] notifications;
    TypedArray profile_pic;
    List<RowItem> rowItems;
    ListView mylistview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        rowItems = new ArrayList<RowItem>();
        notifications = getResources().getStringArray(R.array.notifications);
        profile_pic = getResources().obtainTypedArray(R.array.profile_pics);

        for(int i=0; i< notifications.length;i++){
            RowItem item = new RowItem(notifications[i],profile_pic.getResourceId(i,-1));
            rowItems.add(item);
        }
        mylistview = (ListView) findViewById(R.id.list_view);
        CustomAdapter adapter = new CustomAdapter(this,rowItems);
        mylistview.setAdapter(adapter);
        mylistview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String notifications = rowItems.get(position).getNotifications();
        Toast.makeText(getApplicationContext(), "" + notifications, Toast.LENGTH_SHORT).show();
    }
}
