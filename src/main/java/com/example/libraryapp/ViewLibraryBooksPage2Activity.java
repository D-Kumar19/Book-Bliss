package com.example.libraryapp;

import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import com.example.libraryapp.Book;
import com.example.libraryapp.BooksDatabase;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;
import java.util.concurrent.atomic.AtomicReference;

public class ViewLibraryBooksPage2Activity extends AppCompatActivity {

    private String viewLibraryBooksPage2UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_library_books_page2);

        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
        if (intent.get() != null) {
            viewLibraryBooksPage2UserID = intent.get().getStringExtra("userID");
        }

        TextView viewLibraryBooks2MainUserIDTextView = findViewById(R.id.viewLibraryBooks2MainUserIDTextView);

        ImageView viewLibraryBooks2ImageView1 = findViewById(R.id.viewLibraryBooks2ImageView1);
        ImageView viewLibraryBooks2ImageView2 = findViewById(R.id.viewLibraryBooks2ImageView2);
        ImageView viewLibraryBooks2ImageView3 = findViewById(R.id.viewLibraryBooks2ImageView3);
        ImageView viewLibraryBooks2ImageView4 = findViewById(R.id.viewLibraryBooks2ImageView4);

        Button viewLibraryBooks2TakeALookAtBookButton1 = findViewById(R.id.viewLibraryBooks2TakeALookAtBookButton1);
        Button viewLibraryBooks2TakeALookAtBookButton2 = findViewById(R.id.viewLibraryBooks2TakeALookAtBookButton2);
        Button viewLibraryBooks2TakeALookAtBookButton3 = findViewById(R.id.viewLibraryBooks2TakeALookAtBookButton3);
        Button viewLibraryBooks2TakeALookAtBookButton4 = findViewById(R.id.viewLibraryBooks2TakeALookAtBookButton4);

        String viewLibraryBooksPage2UserIDText = getString(R.string.main_userid_welcome_text, viewLibraryBooksPage2UserID);
        viewLibraryBooks2MainUserIDTextView.setText(viewLibraryBooksPage2UserIDText);

        ImageView[] viewLibraryBooks2ImageViews = {viewLibraryBooks2ImageView1, viewLibraryBooks2ImageView2, viewLibraryBooks2ImageView3, viewLibraryBooks2ImageView4};
        Button[] viewLibraryBooks2TakeALookAtBookButtons = {viewLibraryBooks2TakeALookAtBookButton1, viewLibraryBooks2TakeALookAtBookButton2, viewLibraryBooks2TakeALookAtBookButton3, viewLibraryBooks2TakeALookAtBookButton4};

        BooksDatabase booksDatabase = new BooksDatabase(this);
        List<Book> books = booksDatabase.getAllBooks();

        for (int i = 0; i < viewLibraryBooks2ImageViews.length; i++) {
            if (i + 4 < books.size()) {
                viewLibraryBooks2ImageViews[i].setImageResource(books.get(i + 4).getLocationOfTheBook());
            } else {
                viewLibraryBooks2ImageViews[i].setImageResource(R.drawable.no_book_background);
                viewLibraryBooks2TakeALookAtBookButtons[i].setEnabled(false);
            }
        }

        for (int i = 0; i < viewLibraryBooks2ImageViews.length; i++) {
            final int chosenBook = i;

            viewLibraryBooks2TakeALookAtBookButtons[i].setOnClickListener(view -> {
                String selectedBookISBN = books.get(chosenBook + 4).getISBN();

                intent.set(new Intent(ViewLibraryBooksPage2Activity.this, TakeALookAtBookActivity.class));
                intent.get().putExtra("userID", viewLibraryBooksPage2UserID);
                intent.get().putExtra("ISBN", selectedBookISBN);
                intent.get().putExtra("From", "ViewLibraryBooksPage2Activity");
                startActivity(intent.get());
            });
        }
    }

    public void takeALookAtBookMethod(View view){

    }

    public void viewLibraryBooks2PreviousPageMethod(View view) {
        Intent intent = new Intent(ViewLibraryBooksPage2Activity.this, ViewLibraryBooksPage1Activity.class);
        intent.putExtra("userID", viewLibraryBooksPage2UserID);
        startActivity(intent);
    }

    public void viewLibraryBooks2NextPageMethod(View view){

    }
}