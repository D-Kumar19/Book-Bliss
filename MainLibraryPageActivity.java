package com.example.libraryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

public class MainLibraryPageActivity extends AppCompatActivity {

    private Intent intent = null;
    private String mainLibraryPageUserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_library_page);

        TextView mainLibraryPageUserIDTextView = findViewById(R.id.mainLibraryPageUserIDTextView);

        intent = getIntent();
        if(intent != null) {
            mainLibraryPageUserID = intent.getStringExtra("userID");
        }

        String mainLibraryPageUserIDText = getString(R.string.main_userid_welcome_text, mainLibraryPageUserID);
        mainLibraryPageUserIDTextView.setText(mainLibraryPageUserIDText);
    }

    public void mainLibraryPageMyProfileMethod(View view){
        intent = new Intent(MainLibraryPageActivity.this, ViewMyProfileActivity.class);
        intent.putExtra("userID", mainLibraryPageUserID);
        startActivity(intent);
    }

    public void mainLibraryPageRentedBooksMethod(View view){
        intent = new Intent(MainLibraryPageActivity.this, ViewMyRentedBooksActivity.class);
        intent.putExtra("userID", mainLibraryPageUserID);
        startActivity(intent);
    }

    public void mainLibraryPageLibraryBooksMethod(View view){
        intent = new Intent(MainLibraryPageActivity.this, ViewLibraryBooksPage1Activity.class);
        intent.putExtra("userID", mainLibraryPageUserID);
        startActivity(intent);
    }

    public void mainLibraryPageLogOutMethod(View view){
        Toast.makeText(this, R.string.main_logout_successful, Toast.LENGTH_SHORT).show();
        intent = new Intent(MainLibraryPageActivity.this, MainActivity.class);
        intent.putExtra("userID", mainLibraryPageUserID);
        startActivity(intent);
    }
}