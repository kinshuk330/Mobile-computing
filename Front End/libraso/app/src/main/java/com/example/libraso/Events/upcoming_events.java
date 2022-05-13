package com.example.libraso.Events;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraso.MainActivity;
import com.example.libraso.R;
import com.example.libraso.books;
import com.example.libraso.show_book_grid;
import com.example.libraso.show_holds.Hold;
import com.example.libraso.show_holds.hold_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link upcoming_events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class upcoming_events extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CalendarView calendar;
    private event_adapter adapter;
    private RecyclerView recycler;
    private ArrayList<Event> event_list;
    private ArrayList<String> titles;
    private ArrayList<String> StartTime;
    private ArrayList<String> EndTime;
    private ArrayList<String> Venue;
    private ArrayList<Bitmap> images;
    private long eventOccursOn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public upcoming_events() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment upcoming_events.
     */
    // TODO: Rename and change types and number of parameters
    public static upcoming_events newInstance(String param1, String param2) {
        upcoming_events fragment = new upcoming_events();
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

        titles = new ArrayList<>();
        StartTime = new ArrayList<>();
        EndTime = new ArrayList<>();
        Venue = new ArrayList<>();
        images = new ArrayList<>();
        event_list = new ArrayList<>();

        View view=inflater.inflate(R.layout.upcoming_events, container, false);
        recycler = view.findViewById(R.id.recyclerViewevent);
        calendar=view.findViewById(R.id.calendarViewevent);

        adapter = new event_adapter(getContext(),event_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setHasFixedSize(true);

        recycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchlist();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                titles = new ArrayList<>();
                images = new ArrayList<>();
                event_list.clear();
                adapter.notifyDataSetChanged();

                Calendar c = Calendar.getInstance();
                c.set(i, i1, i2);
                eventOccursOn = c.getTimeInMillis();
                System.out.println("SELECTED DATE CHANGE");
                System.out.println(event_list.size());
                fetchlist();
            }
        });
    }

    public void fetchlist(){


        getActivity().getApplicationContext().sendBroadcast(new Intent("START_LOADING").setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES));
        fetch_Books();


    }

    void fetch_Books()
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this.getContext());
        java.util.Date time= new java.util.Date((long)eventOccursOn);
        SimpleDateFormat ft =
                new SimpleDateFormat ("yyyy-MM-dd ");
        if (time.equals(new java.util.Date((long)0)))
        {
            Log.i("asd","entered");
            time= new java.util.Date((long)calendar.getDate());
        }
        String val = ft.format(time);
        System.out.println("asd" + val);

        String url = "https://libraso.herokuapp.com/event/" + val;
        System.out.println(url);
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray obj  = object.getJSONArray("data");

                    for (int i = 0; i <obj.length() ; i++) {
                        JSONObject tempobj=obj.getJSONObject(i);
                        titles.add(obj.getJSONObject(i).getString("title"));

                        images.add(new upcoming_events.Download_image().execute(obj.getJSONObject(i).getString("image_url")).get());



                        Event t = new Event();
                        t.setTitle(tempobj.getString("title"));
                        t.setStart_time(tempobj.getString("start_time"));
                        t.setEnd_time(tempobj.getString("end_time"));
                        t.setVenue(tempobj.getString("venue"));
                        t.setImage(images.get(images.size()-1));
                        event_list.add(t);
                        System.out.println("reciieved response");


                    }
                    getActivity().getApplicationContext().sendBroadcast(new Intent("STOP_LOADING").setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES));
                    adapter.notifyDataSetChanged();
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
                getActivity().getApplicationContext().sendBroadcast(new Intent("STOP_LOADING").setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES));

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
//            ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//            if (networkInfo == null || !networkInfo.isConnected())
//            { //if connected to wifi then start service
//                cancel(true);
//                Log.i(TAG, "onPreExecute: is cancelled  "+isCancelled());
//            }


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