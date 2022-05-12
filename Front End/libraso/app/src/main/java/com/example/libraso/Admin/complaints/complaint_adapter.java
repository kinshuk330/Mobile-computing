package com.example.libraso.Admin.complaints;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraso.Events.Event;
import com.example.libraso.Events.event_adapter;
import com.example.libraso.R;

import java.util.ArrayList;

public class complaint_adapter extends RecyclerView.Adapter<complaint_adapter.ViewHolder>

{
    private Context context;
    private ArrayList<complaints_class> complaint_list;
    complaint_adapter(Context context, ArrayList<complaints_class> list) {
        this.context = context;
        this.complaint_list = list;
//        this.fm = fm;)
    }
    @NonNull
    @Override
    public complaint_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_grid,parent,false);
        return new complaint_adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull complaint_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.complaint_title.setText("Title:"+complaint_list.get(position).getTitle());
        holder.complaint_name.setText("Name:"+complaint_list.get(position).getUsername());
        holder.complaint_description.setText("Description:"+complaint_list.get(position).getDescritption());
        holder.complaint_resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaint_list.remove(position);
                notifyDataSetChanged();
                update_complaint();
            }
        });
    }

    private void update_complaint() {

    }

    @Override
    public int getItemCount() {
        return complaint_list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView complaint_title;
        TextView complaint_name;
        TextView complaint_description;
        Button complaint_resolve;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            complaint_title=itemView.findViewById(R.id.admin_complaint_title);
            complaint_description=itemView.findViewById(R.id.admin_complaint_description);
            complaint_name=itemView.findViewById(R.id.admin_complaint_name);
            complaint_resolve=itemView.findViewById(R.id.admin_complaint_resolve);
            itemView.setOnClickListener(this);



        }


        @Override
        public void onClick(View view) {
            System.out.println("CLICK REGISTERED !!!!!   "+getAdapterPosition());


        }
    }
}