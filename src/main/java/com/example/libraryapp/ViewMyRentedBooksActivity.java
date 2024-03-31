package com.example.libraryapp;

import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import com.example.libraryapp.Book;
import com.example.libraryapp.BooksDatabase;
import com.example.libraryapp.GiveBackRentedBookActivity;
import com.example.libraryapp.MainLibraryPageActivity;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;
import java.util.concurrent.atomic.AtomicReference;

public class ViewMyRentedBooksActivity extends AppCompatActivity {

    private String myRentedBooksPageUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_rented_books);

        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
        if (intent.get() != null) {
            myRentedBooksPageUserID = intent.get().getStringExtra("userID");
        }

        TextView myRentedBooksMainUserIDTextView = findViewById(R.id.myRentedBooksMainUserIDTextView);

        ImageView myRentedBooksImageView1 = findViewById(R.id.myRentedBooksImageView1);
        ImageView myRentedBooksImageView2 = findViewById(R.id.myRentedBooksImageView2);
        ImageView myRentedBooksImageView3 = findViewById(R.id.myRentedBooksImageView3);
        ImageView myRentedBooksImageView4 = findViewById(R.id.myRentedBooksImageView4);

        Button myRentedBooksCheckBookButton1 = findViewById(R.id.myRentedBooksCheckBookButton1);
        Button myRentedBooksCheckBookButton2 = findViewById(R.id.myRentedBooksCheckBookButton2);
        Button myRentedBooksCheckBookButton3 = findViewById(R.id.myRentedBooksCheckBookButton3);
        Button myRentedBooksCheckBookButton4 = findViewById(R.id.myRentedBooksCheckBookButton4);

        String myRentedBooksPageUserIDText = getString(R.string.main_userid_welcome_text, myRentedBooksPageUserID);
        myRentedBooksMainUserIDTextView.setText(myRentedBooksPageUserIDText);

        ImageView[] myRentedBooksImageViews = {myRentedBooksImageView1, myRentedBooksImageView2, myRentedBooksImageView3, myRentedBooksImageView4};
        Button[] myRentedBooksTakeALookAtBookButtons = {myRentedBooksCheckBookButton1, myRentedBooksCheckBookButton2, myRentedBooksCheckBookButton3, myRentedBooksCheckBookButton4};

        UsersDatabase usersDatabase = new UsersDatabase(this);
        BooksDatabase booksDatabase = new BooksDatabase(this);
        ArrayList<String> rentedBooks = usersDatabase.getUsersDetails(myRentedBooksPageUserID).getBooks();

        for (int i = 0; i < myRentedBooksImageViews.length; i++) {
            if (i < rentedBooks.size()) {
                Book book = booksDatabase.getABookByISBNNumber(rentedBooks.get(i).trim());
                myRentedBooksImageViews[i].setImageResource(book.getLocationOfTheBook());
            }
            else {
                myRentedBooksImageViews[i].setImageResource(R.drawable.no_book_background);
                myRentedBooksTakeALookAtBookButtons[i].setEnabled(false);
            }
        }

        for (int i = 0; i < myRentedBooksImageViews.length; i++) {
            final int chosenBook = i;

            myRentedBooksTakeALookAtBookButtons[i].setOnClickListener(view -> {
                String selectedBookISBN = rentedBooks.get(chosenBook).trim();

                intent.set(new Intent(ViewMyRentedBooksActivity.this, GiveBackRentedBookActivity.class));
                intent.get().putExtra("userID", myRentedBooksPageUserID);
                intent.get().putExtra("ISBN", selectedBookISBN);
                startActivity(intent.get());
            });
        }
    }

    public void myRentedBooksCheckBookMethod(View view){

    }

    public void myRentedBooksReturnMethod(View view) {
        Intent intent = new Intent(ViewMyRentedBooksActivity.this, MainLibraryPageActivity.class);
        intent.putExtra("userID", myRentedBooksPageUserID);
        startActivity(intent);
    }

    public void myRentedBooksNextPageMethod(View view){

    }
}