package com.example.libraryapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BooksDatabase extends SQLiteOpenHelper {
    private static final int VERSION_OF_THE_DATABASE = 1;
    private static final String NAME_OF_THE_TABLE = "books_database";
    private static final String NAME_OF_THE_DATABASE = "books_database_updated.db";

    private static final String COLUMN_ISBN = "ISBN";
    private static final String COLUMN_IS_AVAILABLE = "isAvailable";
    private static final String COLUMN_RENTED_USERS_ID = "rentedUsersID";
    private static final String COLUMN_NAME_OF_THE_BOOK = "nameOfTheBook";
    private static final String COLUMN_NAME_OF_THE_AUTHOR = "authorOfTheBook";
    private static final String COLUMN_LOCATION_OF_THE_BOOK = "locationOfTheBook";

    private Cursor cursor = null;
    private ContentValues values = null;
    private SQLiteDatabase database = null;

    public BooksDatabase(Context context) {
        super(context, NAME_OF_THE_DATABASE, null, VERSION_OF_THE_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE = "CREATE TABLE " + NAME_OF_THE_TABLE + "("
                + COLUMN_NAME_OF_THE_BOOK + " TEXT,"
                + COLUMN_ISBN + " TEXT PRIMARY KEY,"
                + COLUMN_NAME_OF_THE_AUTHOR + " TEXT,"
                + COLUMN_LOCATION_OF_THE_BOOK + " INTEGER,"
                + COLUMN_IS_AVAILABLE + " INTEGER,"
                + COLUMN_RENTED_USERS_ID + " TEXT" + ")";
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + NAME_OF_THE_TABLE);
        onCreate(database);
    }

    public void addAllBooksToDatabase() {
        ArrayList<Book> listOfAllBooks = Book.listOfAllBooks();
        for (Book book : listOfAllBooks) {
            addABook(book);
        }
    }

    public void addABook(Book book) {
        values = new ContentValues();
        database = this.getWritableDatabase();

        values.put(COLUMN_NAME_OF_THE_BOOK, book.getNameOfTheBook());
        values.put(COLUMN_ISBN, book.getISBN());
        values.put(COLUMN_NAME_OF_THE_AUTHOR, book.getAuthorOfTheBook());
        values.put(COLUMN_LOCATION_OF_THE_BOOK, book.getLocationOfTheBook());
        values.put(COLUMN_IS_AVAILABLE, book.isAvailable() ? 1 : 0);

        String rentedUsersID = "";
        if(book.getRentedByUsersID() == null){
            values.put(COLUMN_RENTED_USERS_ID, rentedUsersID);
        }
        else {
            values.put(COLUMN_RENTED_USERS_ID, book.getRentedByUsersID());
        }

        if (!doesTheBookExists(book.getISBN())) {
            database.insert(NAME_OF_THE_TABLE, null, values);
        }
        database.close();
    }

    public boolean doesTheBookExists(String IMEI) {
        database = this.getReadableDatabase();
        cursor = database.query(NAME_OF_THE_TABLE,
                new String[]{COLUMN_ISBN}, COLUMN_ISBN + "= ?",
                new String[]{IMEI}, null, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    @SuppressLint("Range")
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + NAME_OF_THE_TABLE;

        database = this.getWritableDatabase();
        cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setNameOfTheBook(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OF_THE_BOOK)));
                book.setISBN(cursor.getString(cursor.getColumnIndex(COLUMN_ISBN)));
                book.setAuthorOfTheBook(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OF_THE_AUTHOR)));
                book.setLocationOfTheBook(cursor.getInt(cursor.getColumnIndex(COLUMN_LOCATION_OF_THE_BOOK)));
                book.setAvailable(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_AVAILABLE)) == 1);

                String rentedUsersID = cursor.getString(cursor.getColumnIndex(COLUMN_RENTED_USERS_ID));
                if(rentedUsersID == null || rentedUsersID.equals("")){
                    book.setRentedByUsersID(null);
                }
                else{
                    book.setRentedByUsersID(rentedUsersID);
                }

                books.add(book);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return books;
    }

    public Book getABookByISBNNumber(String ISBN){
        List<Book> books = getAllBooks();

        for(Book book : books){
            if(book.getISBN().equals(ISBN)){
                return book;
            }
        }
        return null;
    }

    public void updateTheBookDetails(Book book) {
        database = this.getWritableDatabase();
        values = new ContentValues();

        values.put(COLUMN_NAME_OF_THE_BOOK, book.getNameOfTheBook());
        values.put(COLUMN_NAME_OF_THE_AUTHOR, book.getAuthorOfTheBook());
        values.put(COLUMN_LOCATION_OF_THE_BOOK, book.getLocationOfTheBook());
        values.put(COLUMN_IS_AVAILABLE, book.isAvailable() ? 1 : 0);

        String rentedUsersID = "";
        if(book.getRentedByUsersID() == null){
            values.put(COLUMN_RENTED_USERS_ID, rentedUsersID);
        }
        else {
            values.put(COLUMN_RENTED_USERS_ID, book.getRentedByUsersID());
        }

        values.put(COLUMN_RENTED_USERS_ID, book.getRentedByUsersID());

        database.update(NAME_OF_THE_TABLE, values, COLUMN_ISBN + " = ?", new String[]{book.getISBN()});
        database.close();
    }
}