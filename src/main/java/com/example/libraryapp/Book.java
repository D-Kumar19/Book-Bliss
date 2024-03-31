package com.example.libraryapp;

import java.util.ArrayList;

public class Book {
    private String nameOfTheBook;
    private String ISBN;
    private String authorOfTheBook;
    private int locationOfTheBook;
    private boolean isAvailable;
    private String rentedByUsersID;

    public Book(){
        this.nameOfTheBook = "";
        this.ISBN = "";
        this.authorOfTheBook = "";
        this.locationOfTheBook = -1;
        this.isAvailable = false;
        this.rentedByUsersID = "";
    }

    public Book(String nameOfTheBook, String ISBN, String authorOfTheBook, int locationOfTheBook, boolean isAvailable, String rentedByUsersID) {
        this.nameOfTheBook = nameOfTheBook;
        this.ISBN = ISBN;
        this.authorOfTheBook = authorOfTheBook;
        this.locationOfTheBook = locationOfTheBook;
        this.isAvailable = isAvailable;
        this.rentedByUsersID = rentedByUsersID;
    }

    public String getNameOfTheBook() {
        return nameOfTheBook;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAuthorOfTheBook() {
        return authorOfTheBook;
    }

    public int getLocationOfTheBook() {
        return locationOfTheBook;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getRentedByUsersID() {
        return rentedByUsersID;
    }

    public void setNameOfTheBook(String nameOfTheBook) {
        this.nameOfTheBook = nameOfTheBook;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAuthorOfTheBook(String authorOfTheBook) {
        this.authorOfTheBook = authorOfTheBook;
    }

    public void setLocationOfTheBook(int locationOfTheBook) {
        this.locationOfTheBook = locationOfTheBook;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setRentedByUsersID(String rentedByUsersID) {
        this.rentedByUsersID = rentedByUsersID;
    }

    public static ArrayList<Book> listOfAllBooks() {
        Book book1 = new Book("Zero to One", "9780804139298", "Peter Thiel", R.drawable.book1, true, null);
        Book book2 = new Book("Harry Potter Es a Fonix Rendje", "9639307882", "J.K.Rowling", R.drawable.book2, true, null);
        Book book3 = new Book("Rich Dad Poor Dad: What the Rich Teach Their Kids About Money That the Poor and Middle Class Do Not!", "1612680194", "Robert T. Kiyosaki", R.drawable.book3, true, null);
        Book book4 = new Book("The Intelligent Investor", "9780060555665", "Benjamin Graham", R.drawable.book4, true, null);
        Book book5 = new Book("Think and Grow Rich", "1585424331", "Napoleon Hill", R.drawable.book5, true, null);
        Book book6 = new Book("Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones", "0735211299", "James Clear", R.drawable.book6, true, null);
        Book book7 = new Book("Elon Musk: Tesla, SpaceX, and the Quest for a Fantastic Future", "006230125X", "Ashlee Vance", R.drawable.book7, true, null);

        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
        books.add(book7);

        return books;
    }
}