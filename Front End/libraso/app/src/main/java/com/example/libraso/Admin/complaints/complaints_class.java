package com.example.libraso.Admin.complaints;

public class complaints_class {
    private String Title;
    private String descritption;
    private int userid;
    private String Username;
    public int id;
    public complaints_class(String title, String descritption, int userid, String username) {
        Title = title;
        this.descritption = descritption;
        this.userid = userid;
        Username = username;
    }

    public complaints_class() {

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescritption() {
        return descritption;
    }

    public void setDescritption(String descritption) {
        this.descritption = descritption;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
