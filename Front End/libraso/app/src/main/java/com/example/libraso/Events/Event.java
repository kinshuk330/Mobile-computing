package com.example.libraso.Events;

import android.graphics.Bitmap;

import com.example.libraso.books;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Event {
private String start_time;
private String end_time;
private String title;
private String venue;
private String description;
private Bitmap image;

Event(String description,String start_time,String end_time,String title,String venue, Bitmap image)

{
    this.description=description;
    this.end_time=end_time;
    this.start_time=start_time;
    this.title=title;
    this.venue=venue;
    this.image=image;
}

Event()
{

}


    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venus) {
        this.venue = venus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}

