package com.example.libraryapp;

import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;
import java.util.concurrent.atomic.AtomicReference;

public class ViewLibraryBooksPage1Activity extends AppCompatActivity {

    private Intent intent = null;
    private String viewLibraryBooksPage1UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_library_books_page1);

        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
        if (intent.get() != null) {
            viewLibraryBooksPage1UserID = intent.get().getStringExtra("userID");
        }

        TextView viewLibraryBooks1MainUserIDTextView = findViewById(R.id.viewLibraryBooks1MainUserIDTextView);

        ImageView viewLibraryBooks1ImageView1 = findViewById(R.id.viewLibraryBooks1ImageView1);
        ImageView viewLibraryBooks1ImageView2 = findViewById(R.id.viewLibraryBooks1ImageView2);
        ImageView viewLibraryBooks1ImageView3 = findViewById(R.id.viewLibraryBooks1ImageView3);
        ImageView viewLibraryBooks1ImageView4 = findViewById(R.id.viewLibraryBooks1ImageView4);

        Button viewLibraryBooks1TakeALookAtBookButton1 = findViewById(R.id.viewLibraryBooks1TakeALookAtBookButton1);
        Button viewLibraryBooks1TakeALookAtBookButton2 = findViewById(R.id.viewLibraryBooks1TakeALookAtBookButton2);
        Button viewLibraryBooks1TakeALookAtBookButton3 = findViewById(R.id.viewLibraryBooks1TakeALookAtBookButton3);
        Button viewLibraryBooks1TakeALookAtBookButton4 = findViewById(R.id.viewLibraryBooks1TakeALookAtBookButton4);

        String viewLibraryBooksPage1UserIDText = getString(R.string.main_userid_welcome_text, viewLibraryBooksPage1UserID);
        viewLibraryBooks1MainUserIDTextView.setText(viewLibraryBooksPage1UserIDText);

        ImageView[] viewLibraryBooks1ImageViews = {viewLibraryBooks1ImageView1, viewLibraryBooks1ImageView2, viewLibraryBooks1ImageView3, viewLibraryBooks1ImageView4};
        Button[] viewLibraryBooks1TakeALookAtBookButtons = {viewLibraryBooks1TakeALookAtBookButton1, viewLibraryBooks1TakeALookAtBookButton2, viewLibraryBooks1TakeALookAtBookButton3, viewLibraryBooks1TakeALookAtBookButton4};

        BooksDatabase booksDatabase = new BooksDatabase(this);
        List<Book> books = booksDatabase.getAllBooks();

        for (int i = 0; i < viewLibraryBooks1ImageViews.length; i++) {
            if(i < books.size()) {
                viewLibraryBooks1ImageViews[i].setImageResource(books.get(i).getLocationOfTheBook());
            }
            else{
                viewLibraryBooks1ImageViews[i].setImageResource(R.drawable.no_book_background);
                viewLibraryBooks1TakeALookAtBookButtons[i].setEnabled(false);
            }
        }

        for (int i = 0; i < viewLibraryBooks1ImageViews.length; i++) {
            final int chosenBook = i;

            viewLibraryBooks1TakeALookAtBookButtons[i].setOnClickListener(view -> {
                String selectedBookISBN = books.get(chosenBook).getISBN();

                intent.set(new Intent(ViewLibraryBooksPage1Activity.this, TakeALookAtBookActivity.class));
                intent.get().putExtra("userID", viewLibraryBooksPage1UserID);
                intent.get().putExtra("ISBN", selectedBookISBN);
                intent.get().putExtra("From", "ViewLibraryBooksPage1Activity");
                startActivity(intent.get());
            });
        }
    }

    public void takeALookAtBookMethod(View view){

    }

    public void viewLibraryBooks1ReturnMethod(View view) {
        intent = new Intent(ViewLibraryBooksPage1Activity.this, MainLibraryPageActivity.class);
        intent.putExtra("userID", viewLibraryBooksPage1UserID);
        startActivity(intent);
    }

    public void viewLibraryBooks1NextPageMethod(View view) {
         intent = new Intent(ViewLibraryBooksPage1Activity.this, ViewLibraryBooksPage2Activity.class);
         intent.putExtra("userID", viewLibraryBooksPage1UserID);
         startActivity(intent);
    }
}