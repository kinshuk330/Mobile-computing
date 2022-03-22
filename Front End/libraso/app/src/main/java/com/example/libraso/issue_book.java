package com.example.libraso;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class issue_book extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<String> titles;
    private ArrayList<Integer> images;
    private MyAdapter adapter;
    private FragmentManager fm;

//    private Context context;

    public issue_book(FragmentManager fm){
        this.fm = fm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.issue_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fetchlist();
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new MyAdapter(getContext(),titles,images,fm);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

    }

    public void fetchlist(){
        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("heelo");
        titles.add("yellow");
        titles.add("dirty");
        titles.add("fellow");
        titles.add("fellow");
        titles.add("fellow");
        titles.add("fellow");

        images.add(R.drawable.ic_fine);
        images.add(R.drawable.ic_fine);
        images.add(R.drawable.ic_fine);
        images.add(R.drawable.ic_fine);
        images.add(R.drawable.ic_fine);
        images.add(R.drawable.ic_fine);
        images.add(R.drawable.ic_fine);


    }
}
