package com.example.libraso;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class description_book extends Fragment {
    Dialog dialog, dialog2;
    Button onHold;
    ImageView imageView;
    TextView book_title;
    TextView by;
    TextView author;
    ImageView imageView4;
    TextView rating;
    TextView edition;
    TextView ISBN;
    TextView description;
    books Book;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = new Dialog(getContext());
        dialog2 = new Dialog(getContext());
        return inflater.inflate(R.layout.description_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onHold = view.findViewById(R.id.onHold);
        imageView=view.findViewById(R.id.imageView);
        book_title=view.findViewById(R.id.book_title);
        author=view.findViewById(R.id.author);
        rating=view.findViewById(R.id.rating);
        edition=view.findViewById(R.id.edition);

        ISBN=view.findViewById(R.id.ISBN);
        description=view.findViewById(R.id.description);

        int position=getArguments().getInt("position");
        Book=show_book_grid.Book_list.get(position);
        imageView.setImageBitmap(Book.getImage());
        book_title.setText(Book.getTitle());




        author.setText(Book.getAuthor());
        rating.setText(String.valueOf(Book.getRating()));




        String text = "ISBN - " + Book.getISBN();
        SpannableString ss = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ISBN.setText(ss);


        text = "Description - " + Book.getDescription();
        ss = new SpannableString(text);
        boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        description.setText(ss);

        text = "Edition - " + String.valueOf(Book.getEdition());
        ss = new SpannableString(text);
        boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edition.setText(ss);


        onHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(view);
            }
        });
    }

    public void ShowPopup(View v){
        dialog.setContentView(R.layout.issue_popup);
        Button yes = (Button) dialog.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ///   Place your code here
                issue_book_check(v);



            }
        });

        Button no = (Button) dialog.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void Showconfirm(View v){
        dialog2.setContentView(R.layout.confirmation_popup);
        Button close = (Button) dialog2.findViewById(R.id.close_pop);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });

        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.show();
    }

    void issue_book_check(View v)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

        String url = "https://libraso.herokuapp.com/Issued";
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                try {
                    int count=0;
                    JSONArray obj = new JSONArray(response);

                    for (int i = 0; i <obj.length() ; i++) {
                        JSONObject tempobj=obj.getJSONObject(i);
                        if (tempobj.getInt("user_id")==MainActivity.userid)
                        {
                            count+=1;
                            if (tempobj.getString("book_id").equals(Book.getISBN()))
                            {
                                Toast toast=Toast.makeText(getContext(),"Sorry this book has already been placed on hold",Toast.LENGTH_SHORT);
                                toast.setMargin(50,50);
                                toast.show();
                                return;

                            }
                        }

                    }
                    if (count>=3) {
                        Toast toast=Toast.makeText(getContext(),"Cannot place more than 3 books on field",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                        return;
                    }

                    issue_book();
                    Showconfirm(v);




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
    void issue_book()
    {
        {
            RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

            String url = "https://libraso.herokuapp.com/Issued/";
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    System.out.println(response);
                    try {
                        JSONObject obj = new JSONObject(response);
//


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
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    long date_week = System.currentTimeMillis() + 7 * 24 * 3600 * 1000;
                    Date newDate = new Date(date_week);


                    MyData.put("due_date", dateFormat.format(date).toString());
                    MyData.put("issued_date", dateFormat.format(newDate).toString());
                    MyData.put("user_id", String.valueOf(MainActivity.userid));
                    MyData.put("book_id", Book.getISBN());
                    return MyData;
                }
            };
            MyRequestQueue.add(MyStringRequest);

        }

    }
}
