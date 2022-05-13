package com.example.libraso.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.libraso.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class admin_add_books extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText isbn, title, author,edition, desc, rating, no_books,image_url;
    private Button submit;

    public admin_add_books() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_books.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_add_books newInstance(String param1, String param2) {
        admin_add_books fragment = new admin_add_books();
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
View view=inflater.inflate(R.layout.fragment_add_books, container, false);
        isbn = view.findViewById(R.id.ISBN_add);
        title = view.findViewById(R.id.title_add_book);
        author = view.findViewById(R.id.author_add_book);
        edition = view.findViewById(R.id.edition_add_book);
        desc = view.findViewById(R.id.description_add_book);
        rating = view.findViewById(R.id.rating_add_book);
        no_books = view.findViewById(R.id.signup_confirm_password);
        submit = view.findViewById(R.id.submit);
        image_url=view.findViewById(R.id.imageurl_books);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_new_books();
//                isbn.setText("");
//                title.setText("");
//                author.setText("");
//                edition.setText("");
//                desc.setText("");
//                rating.setText("");
//                no_books.setText("");
//                image_url.setText("");

            }
        });
        return view;}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
    void add_new_books()
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

        String url = "https://libraso.herokuapp.com/books/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity().getApplicationContext(), "Book details submitted!!!!", Toast.LENGTH_LONG).show();

            }}, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                System.out.println(error);
                Toast.makeText(getActivity().getApplicationContext(), "Book details are incomplete!!!!", Toast.LENGTH_LONG).show();

                try {
                    String body = new String(error.networkResponse.data,"UTF-8");
                    System.out.println(body);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }



            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("ISBN", isbn.getText().toString());
                MyData.put("title", title.getText().toString());
                MyData.put("author",author.getText().toString());
                MyData.put("edition", edition.getText().toString());
                MyData.put("description", desc.getText().toString());
                MyData.put("subjects", "blank");
                MyData.put("rating", rating.getText().toString());
                MyData.put("books_available",no_books.getText().toString());
                MyData.put("book_Image_url",image_url.getText().toString());
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

    }
}