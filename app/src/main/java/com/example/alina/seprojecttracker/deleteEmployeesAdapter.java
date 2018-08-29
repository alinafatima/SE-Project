package com.example.alina.seprojecttracker;

/**
 * Created by Alina on 11-May-17.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arshad on 5/9/2017.
 */

public class deleteEmployeesAdapter extends RecyclerView.Adapter<deleteEmployeesAdapter.MyViewHolderE> {
    private Context context;
    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();

    public deleteEmployeesAdapter(Context context, List<Information> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public MyViewHolderE onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row_delete_employees, parent, false);
        MyViewHolderE holder = new MyViewHolderE(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(deleteEmployeesAdapter.MyViewHolderE holder, int position) {
        final Information current = data.get(position);
        holder.title.setText(current.title);

        holder.cbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current.isChecked){
                    current.setChecked(false);
                }else{
                    current.setChecked(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolderE extends RecyclerView.ViewHolder{
        TextView title;
        CheckBox cbDelete;
        public MyViewHolderE(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.employees);
            cbDelete = (CheckBox) itemView.findViewById(R.id.cbDelete);
        }
    }

}
