package com.example.libraso;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Suggest_ebook extends Fragment {
    private Button submit;
    private EditText title;
    private EditText description;
    private RadioGroup r_group;
    private RadioButton selected_type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggest_ebook, container, false);
        submit= view.findViewById(R.id.submit_suggestion);
        title= view.findViewById(R.id.book_title);
        description= view.findViewById(R.id.suggestion_description);
        r_group= view.findViewById(R.id.suggestion_radiogroup);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}