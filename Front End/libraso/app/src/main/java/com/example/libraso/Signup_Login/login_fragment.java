package com.example.libraso.Signup_Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraso.MainActivity;
import com.example.libraso.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class login_fragment extends Fragment {

private Button login;
private EditText loginemail;
private EditText password;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ls_login_fragment, container, false);
        login=view.findViewById(R.id.btn_login);
        loginemail=view.findViewById(R.id.login_email);
        password=view.findViewById(R.id.login_password);
login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
check_login(loginemail.getText().toString(),password.getText().toString());

    }
});
        return view;

    }

    void check_login(String username,String password)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

        String url = "https://libraso.herokuapp.com/auth/login";
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
// {"user":{"id":2,"password":"pbkdf2_sha256$320000$7XyTW6fcLjVIVKRsTO8U9X$Of2CGFAF/XB3SsD6jxJyD82+iYCd31t6lj0URuaD7ks=","last_login":null,"is_superuser":false,"first_name":"Kinshuk","last_name":"Chopra","username":"kinshuk","is_staff":false,"email":"Kinshuk@gmail.com","gender":"M","user_type":"PR","groups":[],"user_permissions":[]},"token":"7f32450e6122550e635894cb7e652051c473c236fbed42e213af127c91bec26a"}
                    startActivity(intent);


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
                MyData.put("username", username);
                MyData.put("password", password);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

    }

    }


