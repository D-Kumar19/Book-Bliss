package com.example.libraryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.libraryapp.Book;
import com.example.libraryapp.BooksDatabase;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;

public class TakeALookAtBookActivity extends AppCompatActivity {
    private Intent intent = null;
    private Button takeALookAtBookRentMethod;
    private String takeALookAtBookMainUserID, takeALookAtBookISBN, takeALookAtBookFrom;


    BooksDatabase booksDatabase = new BooksDatabase(this);
    Book selectedBook = new Book();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_alook_at_book);

        ImageView takeALookAtBookImageView = findViewById(R.id.takeALookAtBookImageView);

        takeALookAtBookRentMethod = findViewById(R.id.takeALookAtBookRentButton);

        TextView takeALookAtBookMainUserIDTextView = findViewById(R.id.takeALookAtBookMainUserIDTextView);
        TextView takeALookAtBookNameTextView = findViewById(R.id.takeALookAtBookNameTextView);
        TextView takeALookAtBookAuthorNameTextView = findViewById(R.id.takeALookAtBookAuthorNameTextView);
        TextView takeALookAtBookISBNTextView = findViewById(R.id.takeALookAtBookISBNTextView);

        intent = getIntent();
        if(intent != null) {
            takeALookAtBookMainUserID = intent.getStringExtra("userID");
            takeALookAtBookISBN = intent.getStringExtra("ISBN");
            takeALookAtBookFrom = intent.getStringExtra("From");
        }

        selectedBook = booksDatabase.getABookByISBNNumber(takeALookAtBookISBN);

        String takeALookAtBookMainUserIDText = getString(R.string.main_userid_welcome_text, takeALookAtBookMainUserID);
        String takeALookAtBookNameText = getString(R.string.take_a_look_at_book_name, selectedBook.getNameOfTheBook());
        String takeALookAtBookAuthorNameText = getString(R.string.take_a_look_at_book_author_name, selectedBook.getAuthorOfTheBook());
        String takeALookAtBookISBNText = getString(R.string.take_a_look_at_book_isbn, selectedBook.getISBN());

        takeALookAtBookMainUserIDTextView.setText(takeALookAtBookMainUserIDText);
        takeALookAtBookNameTextView.setText(takeALookAtBookNameText);
        takeALookAtBookAuthorNameTextView.setText(takeALookAtBookAuthorNameText);
        takeALookAtBookISBNTextView.setText(takeALookAtBookISBNText);

        takeALookAtBookImageView.setImageResource(selectedBook.getLocationOfTheBook());
        if(!selectedBook.isAvailable()){
            takeALookAtBookRentMethod.setEnabled(false);
        }
    }

    public void takeALookAtBookRentMethod(View view){
        UsersDatabase usersDatabase = new UsersDatabase(this);
        usersDatabase.updateUserByAddingABook(takeALookAtBookMainUserID, takeALookAtBookISBN);

        selectedBook.setAvailable(false);
        selectedBook.setRentedByUsersID(takeALookAtBookMainUserID);

        booksDatabase.updateTheBookDetails(selectedBook);
        takeALookAtBookRentMethod.setEnabled(false);
        Toast.makeText(this, R.string.successfully_rented_a_book, Toast.LENGTH_SHORT).show();
    }

    public void takeALookAtBookReturnMethod(View view){
        if(takeALookAtBookFrom.equals("ViewLibraryBooksPage1Activity")) {
            intent = new Intent(TakeALookAtBookActivity.this, ViewLibraryBooksPage1Activity.class);
        }
        else{
            intent = new Intent(TakeALookAtBookActivity.this, ViewLibraryBooksPage2Activity.class);
        }

        intent.putExtra("userID", takeALookAtBookMainUserID);
        startActivity(intent);
    }
}