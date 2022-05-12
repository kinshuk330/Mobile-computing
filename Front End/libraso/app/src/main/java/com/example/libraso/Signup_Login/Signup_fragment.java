package com.example.libraso.Signup_Login;

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


public class Signup_fragment extends Fragment {

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ls_signup_fragment, container, false);
        signup=view.findViewById(R.id.btn_signup);
        user_name=view.findViewById(R.id.signup_user_name);
        first_name=view.findViewById(R.id.signup_first_name);
        last_name=view.findViewById(R.id.signup_last_name);
        email=view.findViewById(R.id.signup_email);
        password=view.findViewById(R.id.signup_password);
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
                System.out.println(response);
                try {
                    JSONObject obj = new JSONObject(response);
//                    JSONObject user=obj.getJSONObject("user");
//                    int user_id=user.getInt("id");
//                    String user_first_name=user.getString("first_name");
//                    String user_last_name=user.getString("last_name");
//                    String user_username=user.getString("username");
//                    String user_email=user.getString("email");
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("User_details",obj.toString());
                    String path= getActivity().getApplicationContext().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/isuserloged.txt";
                    FileOutputStream writer = null;
                    try {
                        writer = new FileOutputStream(path, false);
                        writer.write(String.valueOf(obj.getJSONObject("user").toString()).getBytes());
                        writer.close();
                        System.out.println("Successfully saved");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
// {"user":{"id":2,"password":"pbkdf2_sha256$320000$7XyTW6fcLjVIVKRsTO8U9X$Of2CGFAF/XB3SsD6jxJyD82+iYCd31t6lj0URuaD7ks=","last_login":null,"is_superuser":false,"first_name":"Kinshuk","last_name":"Chopra","username":"kinshuk","is_staff":false,"email":"Kinshuk@gmail.com","gender":"M","user_type":"PR","groups":[],"user_permissions":[]},"token":"7f32450e6122550e635894cb7e652051c473c236fbed42e213af127c91bec26a"}
                    startActivity(intent);
                    getActivity().finish();
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                System.out.println(error);
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    JSONArray errors = data.getJSONArray("errors");
                    JSONObject jsonMessage = errors.getJSONObject(0);
                    String message = jsonMessage.getString("message");
                    Toast.makeText(getActivity().getApplicationContext(), "Fill all details carefully !!", Toast.LENGTH_LONG).show();
                    System.out.println(message);
                } catch (JSONException e) {
                } catch (UnsupportedEncodingException errorr) {
                }
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
                MyData.put("is_admin","F");
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

    }
}