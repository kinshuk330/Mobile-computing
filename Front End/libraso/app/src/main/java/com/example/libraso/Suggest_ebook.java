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
import android.widget.Toast;

public class Suggest_ebook extends Fragment {
    private Button submit;
    private EditText title;
    private EditText description;
    private RadioGroup r_group;
    private String selected_type;


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
        title= view.findViewById(R.id.suggestion_title);
        description= view.findViewById(R.id.suggestion_description);
        r_group= view.findViewById(R.id.suggestion_radiogroup);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioButtonId = r_group.getCheckedRadioButtonId();

                if (selectedRadioButtonId != -1){
                    if(selectedRadioButtonId==1){
                        selected_type = "B";
                        description.setHint("ISBN No: 12345678, Author Name: Mary Janes, Price: 5xxx, Edition: 2, description: xyz, etc.");}
                    if(selectedRadioButtonId==2){
                        selected_type="J";
                        description.setHint("Article Abstract: xyz, Published Author : Mary Janes, Date of Release: 02-05-1991 , URL: www.xyz.com");}


                } else {
                    Toast.makeText(getContext(),"Please select type",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(title.getText().toString().trim()==null || description.getText().toString().trim()==null){
                    Toast.makeText(getContext(),"Please fill all details",Toast.LENGTH_SHORT).show();
                    return;
                }


                Toast.makeText(getContext(),"Suggestion filled",Toast.LENGTH_SHORT).show();
                title.setText("");
                description.setText("");
                description.setHint("description");
                r_group.clearCheck();

            }
        });
        return view;
    }
}