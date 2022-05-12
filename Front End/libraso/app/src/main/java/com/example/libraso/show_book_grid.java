package com.example.libraso;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.example.libraso.Signup_Login.GoogleLogin;
import com.example.libraso.show_holds.hold_adapter;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class show_book_grid extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<String> titles;
    private ArrayList<Bitmap> images;
    private MyAdapter adapter;
    static ArrayList<books> Book_list;
    private FragmentManager fm;


    public show_book_grid(FragmentManager fm){


        this.fm = fm;
    }
    public show_book_grid()
    {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        fetchlist();
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new MyAdapter(getContext(),titles,images);

        Book_list=new ArrayList<books>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

    }

    public void fetchlist(){
        titles = new ArrayList<>();
        images = new ArrayList<>();
        getActivity().getApplicationContext().sendBroadcast(new Intent("START_LOADING").setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES));
        fetch_Books();


    }

    void fetch_Books()
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());

        String url = "https://libraso.herokuapp.com/books";
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                try {

                    JSONArray obj = new JSONArray(response);

                    for (int i = 0; i <obj.length() ; i++) {
                        JSONObject tempobj=obj.getJSONObject(i);
                        titles.add(obj.getJSONObject(i).getString("title"));

                        images.add(new Download_image().execute(obj.getJSONObject(i).getString("book_Image_url")).get());

                        adapter.notifyDataSetChanged();

                        books temp=new books();
                        temp.setTitle(tempobj.getString("title"));
                        temp.setImage(images.get(images.size()-1));
                        temp.setAuthor(tempobj.getString("author"));
                        temp.setRating((float) tempobj.getDouble("rating"));
                        temp.setBooks_available(tempobj.getInt("books_available"));
                        temp.setEdition(tempobj.getInt("edition"));
                        temp.setDescription(tempobj.getString("description"));
                        temp.setISBN(tempobj.getString("ISBN"));
                        temp.setBook_url("https://libraso.herokuapp.com/books/"+temp.getISBN()+"/");
                        Book_list.add(temp);


                    }
                    getActivity().getApplicationContext().sendBroadcast(new Intent("STOP_LOADING").setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES));

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
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





