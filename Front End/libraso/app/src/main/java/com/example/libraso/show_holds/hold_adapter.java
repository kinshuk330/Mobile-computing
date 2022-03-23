package com.example.libraso.show_holds;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraso.MainActivity;
import com.example.libraso.MyAdapter;
import com.example.libraso.R;
import com.example.libraso.description_book;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class hold_adapter extends RecyclerView.Adapter<hold_adapter.ViewHolder>

{
    private Context context;
    private ArrayList<Hold> hold_list;
    hold_adapter(Context context, ArrayList<Hold> list) {
        this.context = context;
        this.hold_list = list;
//        this.fm = fm;)
    }
    @NonNull
    @Override
    public hold_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.holds_grid,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.hold_book_ISBN.setText(hold_list.get(position).getBook_id());
        holder.hold_book_name.setText(hold_list.get(position).getBook().getTitle());
        holder.hold_book_date.setText(hold_list.get(position).getIssued_date().toString());
        holder.hold_book_due.setText(hold_list.get(position).getDue_date().toString());
        holder.hold_book_image.setImageBitmap(hold_list.get(position).getBook().getImage());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView hold_book_image;
        TextView hold_book_name;
        TextView hold_book_date;
        TextView hold_book_due;
        TextView hold_book_ISBN;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hold_book_image = itemView.findViewById(R.id.hold_book_image);
            hold_book_name = itemView.findViewById(R.id.hold_book_name);
            hold_book_date = itemView.findViewById(R.id.hold_book_date);
            hold_book_due = itemView.findViewById(R.id.hold_book_due);
            hold_book_ISBN = itemView.findViewById(R.id.hold_book_ISBN);


        }


        @Override
        public void onClick(View view) {
            System.out.println("CLICK REGISTERED !!!!!   "+getAdapterPosition());
            Bundle bundle=new Bundle();
            bundle.putInt("position",getAdapterPosition());
            description_book new_fragment=new description_book();

            new_fragment.setArguments(bundle);

            MainActivity.fm.beginTransaction().add(R.id.fragment_container,new_fragment).addToBackStack(null).commit();


        }
    }
}