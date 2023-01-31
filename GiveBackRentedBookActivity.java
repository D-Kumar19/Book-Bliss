package com.example.libraryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;

public class GiveBackRentedBookActivity extends AppCompatActivity {

    private Intent intent = null;
    private Button giveBackBookButton;
    private String giveBackBookMainUserID, giveBackBookISBN;

    Book selectedBook = new Book();
    BooksDatabase booksDatabase = new BooksDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_back_rented_book);

        ImageView giveBackBookImageView = findViewById(R.id.giveBackBookImageView);

        giveBackBookButton = findViewById(R.id.giveBackBookButton);

        TextView giveBackBookMainUserIDTextView = findViewById(R.id.giveBackBookMainUserIDTextView);
        TextView giveBackBookNameTextView = findViewById(R.id.giveBackBookNameTextView);
        TextView giveBackBookAuthorNameTextView = findViewById(R.id.giveBackBookAuthorNameTextView);
        TextView giveBackBookISBNTextView = findViewById(R.id.giveBackBookISBNTextView);

        intent = getIntent();
        if(intent != null) {
            giveBackBookMainUserID = intent.getStringExtra("userID");
            giveBackBookISBN = intent.getStringExtra("ISBN");
        }

        selectedBook = booksDatabase.getABookByISBNNumber(giveBackBookISBN);

        String giveBackBookMainUserIDText = getString(R.string.main_userid_welcome_text, giveBackBookMainUserID);
        String giveBackBookNameText = getString(R.string.take_a_look_at_book_name, selectedBook.getNameOfTheBook());
        String giveBackBookAuthorNameText = getString(R.string.take_a_look_at_book_author_name, selectedBook.getAuthorOfTheBook());
        String giveBackBookISBNText = getString(R.string.take_a_look_at_book_isbn, selectedBook.getISBN());

        giveBackBookMainUserIDTextView.setText(giveBackBookMainUserIDText);
        giveBackBookNameTextView.setText(giveBackBookNameText);
        giveBackBookAuthorNameTextView.setText(giveBackBookAuthorNameText);
        giveBackBookISBNTextView.setText(giveBackBookISBNText);

        giveBackBookImageView.setImageResource(selectedBook.getLocationOfTheBook());
        if(!selectedBook.isAvailable()){
            giveBackBookButton.setEnabled(true);
        }
    }

    public void giveBackBookMethod(View view){
        UsersDatabase usersDatabase = new UsersDatabase(this);
        System.out.println(giveBackBookMainUserID + " " + giveBackBookISBN);
        usersDatabase.updateUserByRemovingABook(giveBackBookMainUserID, giveBackBookISBN);

        selectedBook.setAvailable(true);
        selectedBook.setRentedByUsersID(null);
        booksDatabase.updateTheBookDetails(selectedBook);

        giveBackBookButton.setEnabled(false);
        Toast.makeText(this, R.string.successfully_given_back_book, Toast.LENGTH_SHORT).show();
    }

    public void giveBackBookReturnMethod(View view){
        intent = new Intent(GiveBackRentedBookActivity.this, ViewMyRentedBooksActivity.class);
        intent.putExtra("userID", giveBackBookMainUserID);
        startActivity(intent);
    }
}