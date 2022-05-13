package com.example.libraso.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraso.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class admin_add_workshop extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CalendarView calendar;
    private long eventOccursOn;
    private EditText start_time, end_time, title, venue, description, image_url;
    private Button Submit;

    public admin_add_workshop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_workshop.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_add_workshop newInstance(String param1, String param2) {
        admin_add_workshop fragment = new admin_add_workshop();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_add_workshop, container, false);
        calendar=view.findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar c = Calendar.getInstance();
                c.set(i, i1, i2);
                eventOccursOn = c.getTimeInMillis();
                System.out.println("SELECTED DATE CHANGE");
            }
        });
        start_time = view.findViewById(R.id.start_time);
        end_time = view.findViewById(R.id.end_time);
        title = view.findViewById(R.id.title);
        venue = view.findViewById(R.id.venue);
        description = view.findViewById(R.id.description);
        image_url = view.findViewById(R.id.image);
        Submit=view.findViewById(R.id.submit);

        java.util.Date time= new java.util.Date((long)eventOccursOn);
        SimpleDateFormat ft =
                new SimpleDateFormat ("yyyy-MM-dd ");
        if (time.equals(new java.util.Date((long)0)))
        {
            Log.i("asd","entered");
            time= new java.util.Date((long)calendar.getDate());
        }
        String time_string = ft.format(time);
        System.out.println(time_string);
        
        
        
        
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                java.util.Date time= new java.util.Date((long)eventOccursOn);
                SimpleDateFormat ft =
                        new SimpleDateFormat ("yyyy-MM-dd");
                if (time.equals(new java.util.Date((long)0)))
                {
                    Log.i("asd","entered");
                    time= new java.util.Date((long)calendar.getDate());
                }
                String time_string = ft.format(time);
                System.out.println(time_string);

                add_workshop(time_string);
            }
        });
        return view;
    }

    private void add_workshop(String workshop_date)
        {
            RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

            String url = "https://libraso.herokuapp.com/Event/";
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity().getApplicationContext(), "Workshop details submitted!!!!", Toast.LENGTH_LONG).show();

                }}, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    //This code is executed if there is an error.
                    System.out.println(error);
                    Toast.makeText(getActivity().getApplicationContext(), "Workshop details are incomplete!!!!", Toast.LENGTH_LONG).show();


                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put("start_time", start_time.getText().toString());
                    MyData.put("end_time", end_time.getText().toString());
                    MyData.put("title",title.getText().toString());
                    MyData.put("venue", venue.getText().toString());
                    MyData.put("date",workshop_date );
                    MyData.put("description", description.getText().toString());
                    MyData.put("image_url", image_url.getText().toString());
                    return MyData;
                }
            };
            MyRequestQueue.add(MyStringRequest);

        }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
    }
}