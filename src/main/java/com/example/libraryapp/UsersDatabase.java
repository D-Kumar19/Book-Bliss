package com.example.libraryapp;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import android.text.TextUtils;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsersDatabase extends SQLiteOpenHelper {
    private static final int VERSION_OF_THE_DATABASE = 1;
    private static final String NAME_OF_THE_TABLE = "user_details_database";
    private static final String NAME_OF_THE_DATABASE = "users_database_database";

    private static final String COLUMN_USER_ID = "userID";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_LIST_OF_BOOKS = "listOfBooks";

    private Cursor cursor = null;
    private String selectQuery = null;
    private SQLiteDatabase database = null;
    private ContentValues values = new ContentValues();

    public UsersDatabase(Context context) {
        super(context, NAME_OF_THE_DATABASE, null, VERSION_OF_THE_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + NAME_OF_THE_TABLE + "("
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_USER_ID + " TEXT PRIMARY KEY,"
                + COLUMN_LIST_OF_BOOKS + " TEXT" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_OF_THE_TABLE);
        onCreate(db);
    }

    public void addUserDetails(User user) {
        values = new ContentValues();
        database = this.getWritableDatabase();

        values.put(COLUMN_USERNAME, user.getUserName());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_ID, user.getUserID());

        String booksRentedByUser = "";
        if (user.getBooks() == null){
            values.put(COLUMN_LIST_OF_BOOKS, booksRentedByUser);
        }
        else {
            booksRentedByUser = ", " + TextUtils.join(", ", user.getBooks());
            values.put(COLUMN_LIST_OF_BOOKS, booksRentedByUser);
        }

        database.insert(NAME_OF_THE_TABLE, null, values);
        database.close();
    }

    public Map<String, String> getUsersIDsAndPasswords() {
        Map<String, String> usersIDAndPasswords = new HashMap<>();
        selectQuery = "SELECT " + COLUMN_USER_ID + ", " + COLUMN_PASSWORD + " FROM " + NAME_OF_THE_TABLE;

        database = this.getReadableDatabase();
        cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                usersIDAndPasswords.put(cursor.getString(0), cursor.getString(1));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return usersIDAndPasswords;
    }

    public boolean checkUsersNamesAndPasswords(String userName, String password) {
        selectQuery = "SELECT " + COLUMN_USERNAME + ", " + COLUMN_PASSWORD + ", " + COLUMN_USER_ID + " FROM " + NAME_OF_THE_TABLE;

        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(0).equals(userName) && cursor.getString(1).equals(password)){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return false;
    }

    public boolean checkUsersNameAndUserIDs(String userName, String userID) {
        selectQuery = "SELECT " + COLUMN_USERNAME + ", " + COLUMN_USER_ID + " FROM " + NAME_OF_THE_TABLE;

        database = this.getReadableDatabase();
        cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if(userName.equals(cursor.getString(0)) && userID.equals(cursor.getString(1))){
                    return true;
                }
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return false;
    }

    @SuppressLint("Range")
    public String getUsersPassword(String userID) {
        database = this.getReadableDatabase();
        cursor = database.query(NAME_OF_THE_TABLE, new String[]{COLUMN_PASSWORD},
                COLUMN_USER_ID + "=?", new String[]{userID}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
    }

    public void updateUsersPassword(String userID, String usersNewPassword) {
        database = this.getWritableDatabase();
        values = new ContentValues();

        values.put(COLUMN_PASSWORD, usersNewPassword);
        database.update(NAME_OF_THE_TABLE, values, COLUMN_USER_ID + " = ?", new String[]{userID});
        database.close();
    }

    public User getUsersDetails(String userID) {
        selectQuery = "SELECT * FROM " + NAME_OF_THE_TABLE + " WHERE " + COLUMN_USER_ID + " = '" + userID + "'";

        User user = null;
        database = this.getReadableDatabase();
        cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            String rentedBooks = cursor.getString(3);
            ArrayList<String> rentedBooksList = null;

            if(rentedBooks != null && rentedBooks.length() != 0) {
                String[] rentedBooksArray = rentedBooks.split(", ");
                rentedBooksList = new ArrayList<>();

                Collections.addAll(rentedBooksList, rentedBooksArray);
            }

            user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), rentedBooksList);
        }
        cursor.close();
        database.close();

        return user;
    }

    public void updateUserByAddingABook(String userID, String ISBN) {
        database = this.getWritableDatabase();
        cursor = database.rawQuery("SELECT * FROM " + NAME_OF_THE_TABLE + " WHERE " + COLUMN_USER_ID + "=?", new String[]{userID});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String listOfBooks = cursor.getString(cursor.getColumnIndex(COLUMN_LIST_OF_BOOKS));
            if (listOfBooks == null || listOfBooks.equals("")) {
                listOfBooks = ISBN;
            }
            else {
                listOfBooks = listOfBooks + ", " + ISBN;
            }

            values = new ContentValues();
            values.put(COLUMN_LIST_OF_BOOKS, listOfBooks);
            database.update(NAME_OF_THE_TABLE, values, COLUMN_USER_ID + " = ?", new String[]{userID});
        }
    }

    public void updateUserByRemovingABook(String userID, String ISBN) {
        database = this.getWritableDatabase();
        cursor = database.rawQuery("SELECT * FROM " + NAME_OF_THE_TABLE + " WHERE " + COLUMN_USER_ID + "=?", new String[]{userID});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String listOfBooks = cursor.getString(cursor.getColumnIndex(COLUMN_LIST_OF_BOOKS));
            if (listOfBooks != null && !listOfBooks.isEmpty()) {
                String[] ISBNs = listOfBooks.split(", ");
                StringBuilder updatedList = new StringBuilder();

                for (String usersISBN : ISBNs) {
                    if (!usersISBN.equals(ISBN)) {
                        updatedList.append(usersISBN).append(", ");
                    }
                }

                if (updatedList.length() > 0) {
                    updatedList.deleteCharAt(updatedList.length() - 1);
                    updatedList.deleteCharAt(updatedList.length() - 1);
                }

                values = new ContentValues();
                values.put(COLUMN_LIST_OF_BOOKS, updatedList.toString());
                database.update(NAME_OF_THE_TABLE, values, COLUMN_USER_ID + " = ?", new String[]{userID});
            }
        }
    }
}