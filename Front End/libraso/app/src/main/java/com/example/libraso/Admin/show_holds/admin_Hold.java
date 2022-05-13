package com.example.libraso.Admin.show_holds;

import com.example.libraso.books;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class admin_Hold {
private int id;
private String due_date;
private String issued_date;
private int user_id;
private String book_id;
private books Book;

admin_Hold(int id, String dd, String issued_d, int user_id, String book_id, books temp) throws ParseException {
    this.id=id;
    SimpleDateFormat output = new SimpleDateFormat("dd/mm/yyyy");

    SimpleDateFormat input=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//    this.due_date=dt1.format(formatter1.parse(dd));
//    this.issued_date=dt1.format(formatter1.parse(issued_d));
    this.due_date= output.format(input.parse(dd));
    this.issued_date=output.format(input.parse(issued_d));
    this.user_id=user_id;
    this.book_id=book_id;
    this.Book=temp;


}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getIssued_date() {
        return issued_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public books getBook() {
        return Book;
    }

    public void setBook(books book) {
        Book = book;
    }
}

// {
//        "id": 2,
//        "due_date": "2022-03-23T10:03:50Z",
//        "issued_date": "2022-03-30T10:03:50Z",
//        "user_id": 2,
//        "book_id": "5546213973594"
//    },