package com.example.libraso.Events;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraso.R;
import com.example.libraso.show_holds.Hold;

import java.util.ArrayList;

public class event_adapter extends RecyclerView.Adapter<event_adapter.ViewHolder>

{
    private Context context;
    private ArrayList<Event> event_list;
    event_adapter(Context context, ArrayList<Event> list) {
        this.context = context;
        this.event_list = list;
//        this.fm = fm;)
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_grid,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.event_venue.setText(event_list.get(position).getVenue());
        holder.event_title.setText(event_list.get(position).getTitle());
        holder.event_starttime.setText(event_list.get(position).getStart_time());
        holder.event_endtime.setText(event_list.get(position).getEnd_time());
//        holder.event_venue.setText(event_list.get(position).get());

    }

    @Override
    public int getItemCount() {
        return event_list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView event_image;
        TextView event_title;
        TextView event_starttime;
        TextView event_endtime;
        TextView event_venue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            event_image=itemView.findViewById(R.id.event_image);
            event_title=itemView.findViewById(R.id.event_title);
            event_starttime=itemView.findViewById(R.id.event_starttime);
            event_endtime=itemView.findViewById(R.id.event_endtime);
            event_venue=itemView.findViewById(R.id.event_venue);
            itemView.setOnClickListener(this);



        }


        @Override
        public void onClick(View view) {
            System.out.println("CLICK REGISTERED !!!!!   "+getAdapterPosition());


        }
    }
}