package com.example.libraso;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class description_book extends Fragment {
    Dialog dialog;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = new Dialog(getContext());
        return inflater.inflate(R.layout.description_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onHold = view.findViewById(R.id.onHold);
        imageView.findViewById(R.id.imageView);
        book_title.findViewById(R.id.book_title);
        by.findViewById(R.id.By);
        author.findViewById(R.id.author);
        imageView4.findViewById(R.id.imageView4);
        rating.findViewById(R.id.rating);
        edition.findViewById(R.id.edition);
        ISBN.findViewById(R.id.ISBN);
        description.findViewById(R.id.description);

        int position=getArguments().getInt("position");





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
}
