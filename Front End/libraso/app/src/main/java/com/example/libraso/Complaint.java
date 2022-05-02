package com.example.libraso;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Complaint extends Fragment {
    private Button submit;
    private EditText title;
    private EditText description;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);
        submit=view.findViewById(R.id.submit_complain);
        title=view.findViewById(R.id.complaint_title);
        description=view.findViewById(R.id.complaint_description);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raise_complaint();
            }
        });
        return view;

    }
    void raise_complaint()
    {
        {
            RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

            String url = "https://libraso.herokuapp.com/complaint/";
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    System.out.println(response);
                    try {
                        JSONObject obj = new JSONObject(response);
//
                        Toast toast=Toast.makeText(getContext(),"Complaint has been raised",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                        title.setText("");
                        description.setText("");

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
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();

                    MyData.put("title", title.getText().toString());
                    MyData.put("description", description.getText().toString());
                    MyData.put("user_id", String.valueOf(MainActivity.userid));
                    return MyData;
                }
            };

            MyRequestQueue.add(MyStringRequest);

        }

    }
}