package com.example.libraryapp;

import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.support.v7.app.AppCompatActivity;

public class ViewMyProfileActivity extends AppCompatActivity {

    private Intent intent = null;
    private String viewMyProfileUserID = "";
    public TextView viewMyProfileMainUserIDTextView, viewMyProfileUserNameTextView, viewMyProfileUserIDTextView, viewMyProfilePasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_profile);

        viewMyProfileMainUserIDTextView = findViewById(R.id.viewMyProfileMainUserIDTextView);

        viewMyProfileUserNameTextView = findViewById(R.id.viewMyProfileUserNameTextView);
        viewMyProfileUserIDTextView = findViewById(R.id.viewMyProfileUserIDTextView);
        viewMyProfilePasswordTextView = findViewById(R.id.viewMyProfilePasswordTextView);

        ListView viewMyProfileBooksListView = findViewById(R.id.viewMyProfileBooksListView);

        intent = getIntent();
        if(intent != null) {
            viewMyProfileUserID = intent.getStringExtra("userID");
        }

        UsersDatabase usersDatabase = new UsersDatabase(this);
        User user = usersDatabase.getUsersDetails(viewMyProfileUserID);

        String viewMyProfileMainUserID = getString(R.string.main_userid_welcome_text, viewMyProfileUserID);
        String viewMyProfileUserName = getString(R.string.view_my_profile_user_name, user.getUserName());
        String viewMyProfileUserPassword = getString(R.string.view_my_profile_user_password, user.getPassword());
        String viewMyProfileUserID = getString(R.string.view_my_profile_user_id, user.getUserID());

        viewMyProfileMainUserIDTextView.setText(viewMyProfileMainUserID);
        viewMyProfileUserNameTextView.setText(viewMyProfileUserName);
        viewMyProfileUserIDTextView.setText(viewMyProfileUserPassword);
        viewMyProfilePasswordTextView.setText(viewMyProfileUserID);

        ArrayList<String> rentedBooks = user.getBooks();
        if(rentedBooks == null){
            rentedBooks = new ArrayList<>();
            rentedBooks.add("No books to list!");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rentedBooks);
        viewMyProfileBooksListView.setAdapter(arrayAdapter);
    }

    public void viewMyProfileReturnMethod(View view){
        intent = new Intent(ViewMyProfileActivity.this, MainLibraryPageActivity.class);
        intent.putExtra("userID", viewMyProfileUserID);
        startActivity(intent);
    }
}