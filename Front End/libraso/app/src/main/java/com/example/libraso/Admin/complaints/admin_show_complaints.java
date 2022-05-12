package com.example.libraso.Admin.complaints;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraso.Events.Event;
import com.example.libraso.Events.event_adapter;
import com.example.libraso.Events.upcoming_events;
import com.example.libraso.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class admin_show_complaints extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_show_complaints() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment show_complaints.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_show_complaints newInstance(String param1, String param2) {
        admin_show_complaints fragment = new admin_show_complaints();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
private ArrayList<complaints_class>  complaint_list;
    private complaint_adapter adapter;
    private RecyclerView recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_show_complaints, container, false);
        complaint_list = new ArrayList<>();

        recycler = view.findViewById(R.id.complaint_recyclerView);

        adapter = new complaint_adapter(getContext(),complaint_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setHasFixedSize(true);

        recycler.setAdapter(adapter);
        fetch_complaints();
        return  view;
    }
    void fetch_complaints()
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

        String url = "https://libraso.herokuapp.com/complaint/" ;
        System.out.println(url);
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                try {
                    JSONArray object = new JSONArray(response);
                    System.out.println(object.toString());
                    JSONArray obj  = object;

                    for (int i = 0; i <obj.length() ; i++) {
                        JSONObject tempobj=obj.getJSONObject(i);

                        if(tempobj.getBoolean("status"))
                            continue;
                        complaints_class t = new complaints_class();
                        t.setTitle(tempobj.getString("title"));
                        t.setDescritption(tempobj.getString("description"));
                        t.setUserid(tempobj.getInt("user_id"));
//                        fetch_username();
                        complaint_list.add(t);
                        System.out.println("reciieved response");


                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


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



}