package com.example.libraso.show_holds;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraso.MyAdapter;
import com.example.libraso.R;
import com.example.libraso.books;
import com.example.libraso.show_book_grid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class show_all_holds extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<String> titles;
    private ArrayList<Bitmap> images;
    private hold_adapter adapter;
    static ArrayList<Hold> hold_list;
    private FragmentManager fm;

//    private Context context;

    public show_all_holds(FragmentManager fm){


        this.fm = fm;
    }
    public show_all_holds()
    {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_all_holds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fetchlist();

        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new hold_adapter(getContext(),hold_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);


    }

    public void fetchlist(){
        hold_list=new ArrayList<Hold>();

        fetch_holds();

        for (Hold temp: hold_list)
        {
            temp.setBook(fetch_Books(temp.getBook_id()));

        }
        System.out.println(hold_list.get(0));
        adapter.notifyDataSetChanged();

    }

    void fetch_holds()
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

        String url = "https://libraso.herokuapp.com/Issued";
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                try {
                    System.out.println(response);
                    JSONArray obj = new JSONArray(response);

                    for (int i = 0; i <obj.length() ; i++) {
                        JSONObject tempobj=obj.getJSONObject(i);
                        Hold temp=new Hold(
                                tempobj.getInt("id"),
                                tempobj.getString("due_date"),
                                tempobj.getString("issued_date"),
                                tempobj.getInt("user_id"),
                                tempobj.getString("book_id"),
                                null



                        );
                        hold_list.add(temp);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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

    books fetch_Books(String ISBN)
    {
        books temp=new books();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

        String url = "https://libraso.herokuapp.com/books/"+ISBN;
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                try {
                    JSONObject tempobj = new JSONObject(response);



                        adapter.notifyDataSetChanged();

                        temp.setTitle(tempobj.getString("title"));
                        temp.setImage(new Download_image().execute(tempobj.getString("book_Image_url")).get());
                        temp.setAuthor(tempobj.getString("author"));
                        temp.setRating((float) tempobj.getDouble("rating"));
                        temp.setBooks_available(tempobj.getInt("books_available"));
                        temp.setEdition(tempobj.getInt("edition"));
                        temp.setDescription(tempobj.getString("description"));
                        temp.setISBN(tempobj.getString("ISBN"));
                        temp.setBook_url("https://libraso.herokuapp.com/books/"+temp.getISBN()+"/");


                    } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                } catch (ExecutionException executionException) {
                    executionException.printStackTrace();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
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
return temp;
    }
    class Download_image extends AsyncTask<String, Void, Bitmap>
    {
        String TAG ="Downlad_webpage";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected())
            { //if connected to wifi then start service
                cancel(true);
                Log.i(TAG, "onPreExecute: is cancelled  "+isCancelled());
            }


        }

        @Override
        protected Bitmap doInBackground(String... strings) {
// params comes from the execute() call: params[0] is the url.
            String response;
            String string_body=null;
            String string_title=null;
            String img_url=strings[0];

            Bitmap bitmap = null;
            try {
                InputStream in = null;



                URL url = new URL(img_url);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds*/);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                in = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);








            }



            catch (IOException e) {

                System.out.println("Unable to retrieve web page. URL may be invalid"+e);

            }
            return bitmap  ;
        }




        protected void onPostExecute(Bitmap result) {
//            Intent intent = new Intent("Sent_from_Download"); //FILTER is a string to identify this intent
//
//            intent.putExtra("data", result);
//            Log.i("bk", "sent broadcast: ");
//
//
//            sendBroadcast(intent);


        }





    }

}





