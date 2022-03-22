package com.example.libraso;

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

import java.util.BitSet;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<String> titles;
    private List<Bitmap> images;
//    private FragmentManager fm;

    public MyAdapter(Context context, List<String> titles,List<Bitmap> images){
        this.context = context;
        this.titles = titles;
        this.images = images;
//        this.fm = fm;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(titles.get(position));
        holder.imageView.setImageBitmap(images.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            System.out.println("CLICK REGISTERED !!!!!   "+getAdapterPosition());
            Bundle bundle=new Bundle();
            bundle.putInt("position",getAdapterPosition());
            description_book new_fragment=new description_book();

            new_fragment.setArguments(bundle);

            MainActivity.fm.beginTransaction().replace(R.id.fragment_container,new_fragment).commit();


        }
    }
}
