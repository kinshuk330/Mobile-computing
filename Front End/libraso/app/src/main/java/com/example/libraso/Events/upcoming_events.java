package com.example.libraso.Events;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.libraso.R;
import com.example.libraso.show_holds.Hold;
import com.example.libraso.show_holds.hold_adapter;

import java.util.ArrayList;

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
        fetchevents();
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

    private void fetchevents() {
        //needs to add a check
        event_list=new ArrayList<>();
        Event temp=new Event("Blah","14:00","16:00","Grammarly","library");
        event_list.add(temp);

         temp=new Event("Blah","15:00","16:00","Grammarly","library");
        event_list.add(temp);

        temp=new Event("Blah","15:00","16:00","Grammarly","library");
        event_list.add(temp);

    }
}