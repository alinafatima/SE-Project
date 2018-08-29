package com.example.alina.seprojecttracker;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
public class afterloginAdapter extends RecyclerView.Adapter<afterloginAdapter.MyViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();

    public afterloginAdapter(Context context, List<Information> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Information current = data.get(position);
        holder.title.setText(current.title);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent; //new Intent(context, Login.class);
                switch (holder.getAdapterPosition()) {
                    case 0:
                        intent = new Intent(context, HomeActivityEmployer.class);
                        break;
                    case 1:
                        intent = new Intent(context, Profile.class);
                        break;
                    case 2:
                        intent = new Intent(context, Employees.class);
                        break;
                    default:
                        intent = new Intent(context, login.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.navigation);

        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent());
        }
    }
}
