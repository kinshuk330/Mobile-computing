package com.example.libraso.Admin.complaints;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraso.Events.Event;
import com.example.libraso.Events.event_adapter;
import com.example.libraso.R;
import com.example.libraso.books;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        View v = LayoutInflater.from(context).inflate(R.layout.complaint_grid,parent,false);
        return new complaint_adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull complaint_adapter.ViewHolder holder, int position) {
        holder.complaint_title.setText("Title:"+complaint_list.get(position).getTitle());
        holder.complaint_name.setText("Name:"+complaint_list.get(position).getUsername());
        holder.complaint_description.setText("Description:"+complaint_list.get(position).getDescritption());
        holder.complaint_resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_complaint(complaint_list.get(position),position);

                complaint_list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    private void update_complaint(complaints_class temp,int position) {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);

        String url = "https://libraso.herokuapp.com/complaint/resolve/"+String.valueOf(temp.id);
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Toast.makeText(context, "Issue resolved !!!!", Toast.LENGTH_LONG).show();
                complaint_list.remove(position);
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