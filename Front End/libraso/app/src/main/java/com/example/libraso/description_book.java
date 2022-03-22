package com.example.libraso;

import android.app.Dialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        books Book=show_book_grid.Book_list.get(position);
        imageView.setImageBitmap(Book.getImage());
        book_title.setText(Book.getTitle());




        author.setText(Book.getAuthor());
        rating.setText(String.valueOf(Book.getRating()));




        String text = "ISBN - " + Book.getISBN();
        SpannableString ss = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ISBN.setText(ss);


        text = "Description - " + Book.getISBN();
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
                Showconfirm(v);
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
}
