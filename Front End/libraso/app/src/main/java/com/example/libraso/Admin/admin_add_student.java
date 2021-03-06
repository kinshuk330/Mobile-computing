package com.example.libraso.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraso.MainActivity;
import com.example.libraso.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class admin_add_student extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button signup;
    private EditText user_name;
    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private RadioGroup gender;
    private RadioButton gender_button;
    private String selected_gender="";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_add_student() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_student.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_add_student newInstance(String param1, String param2) {
        admin_add_student fragment = new admin_add_student();
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
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);
        signup=view.findViewById(R.id.submit);
        user_name=view.findViewById(R.id.signup_user_name);
        first_name=view.findViewById(R.id.title_add_book);
        last_name=view.findViewById(R.id.author_add_book);
        email=view.findViewById(R.id.edition_add_book);
        password=view.findViewById(R.id.description_add_book);
        confirm_password=view.findViewById(R.id.signup_confirm_password);
        gender=view.findViewById(R.id.signup_gender);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!password.getText().toString().equals(confirm_password.getText().toString())){
                    Toast.makeText(getContext(),"Password doesn't match",Toast.LENGTH_SHORT).show();
                    return;
                }
                int selectedRadioButtonId = gender.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1){

                    if(selectedRadioButtonId==1){
                        selected_gender = "M";}
                    if(selectedRadioButtonId==2){
                        selected_gender="F";
                    }

                } else {
                    Toast.makeText(getContext(),"Please select gender",Toast.LENGTH_SHORT).show();
                    return;
                }

                check_signup(
                        user_name.getText().toString(),
                        selected_gender,
                        first_name.getText().toString(),
                        last_name.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString()
                );
            }
        });
        return view;
    }

    void check_signup(String username,String gender,String first_name, String last_name, String email,String password)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

        String url = "https://libraso.herokuapp.com/auth/register";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Toast.makeText(getActivity().getApplicationContext(), "student details submitted!!!!", Toast.LENGTH_LONG).show();

                System.out.println(response);

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                System.out.println(error);
                Toast.makeText(getActivity().getApplicationContext(), "invalid credentials!!!!", Toast.LENGTH_LONG).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", username);
                MyData.put("first_name", first_name);
                MyData.put("last_name", last_name);
                MyData.put("email", email);
                MyData.put("gender", gender);
                MyData.put("user_type", "ST");
                MyData.put("password", password);
                MyData.put("is_admin","false");
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

    }
}