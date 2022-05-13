package com.example.libraso.Admin.show_holds;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraso.R;
import com.example.libraso.books;
import com.example.libraso.show_holds.Hold;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class admin_hold_adapter extends RecyclerView.Adapter<admin_hold_adapter.ViewHolder>

{
    Dialog dialog, dialog2;
    private Boolean need_button;
    private Context context;
    private ArrayList<admin_Hold> hold_list;
    admin_hold_adapter(Context context, ArrayList<admin_Hold> list,boolean val) {
        need_button=val;
        this.context = context;
        this.hold_list = list;
//        this.fm = fm;)
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.holds_grid,parent,false);
        dialog = new Dialog(context);
        dialog2 = new Dialog(context);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.hold_book_ISBN.setText(hold_list.get(position).getBook_id());
        holder.hold_book_name.setText(hold_list.get(position).getBook().getTitle());
        holder.hold_book_date.setText("Hold placed on :"+hold_list.get(position).getIssued_date().toString());
        holder.hold_book_due.setText("Hold expires on :"+hold_list.get(position).getDue_date().toString());
        holder.hold_book_image.setImageBitmap(hold_list.get(position).getBook().getImage());

        holder.convert_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("testing working op");
                issue_to_hold(position);
            }
        });

    }
    private void issue_to_hold(int positiion) {
        books temp=new books();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);

        String url = "https://libraso.herokuapp.com/holds/"+String.valueOf(hold_list.get(positiion).getBook_id()+"/"+String.valueOf(hold_list.get(positiion).getUser_id()));
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Toast.makeText(context, "hold converted to issued!!!!", Toast.LENGTH_LONG).show();
                hold_list.remove(positiion);
                notifyDataSetChanged();


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                System.out.println(error);
            }
        }) ;

        MyRequestQueue.add(MyStringRequest);
    }


    @Override
    public int getItemCount() {
        return hold_list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView hold_book_image;
        TextView hold_book_name;
        TextView hold_book_date;
        TextView hold_book_due;
        TextView hold_book_ISBN;
        Button convert_issue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hold_book_image = itemView.findViewById(R.id.hold_book_image);
            hold_book_name = itemView.findViewById(R.id.hold_book_name);
            hold_book_date = itemView.findViewById(R.id.hold_book_date);
            hold_book_due = itemView.findViewById(R.id.hold_book_due);
            hold_book_date.setVisibility(View.GONE);
            hold_book_due.setVisibility(View.GONE);
            hold_book_ISBN = itemView.findViewById(R.id.hold_book_ISBN);
            convert_issue=itemView.findViewById(R.id.convert_book_issue);
            if(!need_button) {
                convert_issue.setVisibility(View.GONE);
                convert_issue.setEnabled(false);
            }

            itemView.setOnClickListener(this);


        }




        @Override
        public void onClick(View view) {
            System.out.println("CLICK REGISTERED !!!!!   "+getAdapterPosition());


        }


    }
}