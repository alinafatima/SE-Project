package com.example.acer.hlloworld;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.RowId;
import java.util.List;

/**
 * Created by Acer on 4/23/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<RowItem> rowItems;

    CustomAdapter(Context context, List<RowItem> rowItems){
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {return rowItems.size();}

    @Override
    public Object getItem(int position) {return rowItems.get(position);}

    @Override
    public long getItemId(int position) {return rowItems.indexOf(getItem(position));}

    /* private view holder*/
    private class ViewHolder{
        ImageView profile_pic;
        TextView notifications;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item,null);
            holder = new ViewHolder();

            holder.notifications = (TextView) convertView.findViewById(R.id.notifications);
            holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_pic);
            RowItem row_pos = rowItems.get(position);
            holder.profile_pic.setImageResource(row_pos.getProfile_pic_id());
            holder.notifications.setText(row_pos.getNotifications());
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
