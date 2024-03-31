package com.example.libraryapp;

import java.util.ArrayList;

public class User {
    private String userName;
    private String password;
    private String userID;
    private ArrayList<String> books;

    User(String userName, String password, String userID, ArrayList<String> books) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
        this.books = books;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserID() {
        return userID;
    }

    public ArrayList<String> getBooks() {
        return books;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setBooks(ArrayList<String> books) {
        this.books = books;
    }
}
