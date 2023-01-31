package com.example.libraryapp;

import java.util.Map;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.text.Editable;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.text.TextWatcher;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private Intent intent = null;
    private Button mainLogInButton;
    private TextView mainUserIDEditTextView, mainPasswordEditTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLogInButton = findViewById(R.id.mainLogInButton);

        mainUserIDEditTextView = findViewById(R.id.mainUserIDEditTextView);
        mainPasswordEditTextView = findViewById(R.id.mainPasswordEditTextView);

        mainUserIDEditTextView.addTextChangedListener(this);
        mainPasswordEditTextView.addTextChangedListener(this);

        BooksDatabase booksDatabase = new BooksDatabase(this);
        booksDatabase.addAllBooksToDatabase();
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        mainLogInButton.setEnabled(!mainUserIDEditTextView.getText().toString().trim().isEmpty() &&
                !mainPasswordEditTextView.getText().toString().trim().isEmpty());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {}

    public void mainLogInMethod(View view) {
        String mainUserID = mainUserIDEditTextView.getText().toString().trim();
        String mainPassword = mainPasswordEditTextView.getText().toString().trim();

        mainLogInButton.setEnabled(false);
        mainUserIDEditTextView.setText("");
        mainPasswordEditTextView.setText("");

        boolean correctCredentials = false;
        UsersDatabase usersDatabase = new UsersDatabase(this);
        Map<String, String> usersIDAndPasswords = usersDatabase.getUsersIDsAndPasswords();

        for (Map.Entry<String, String> usersIDAndPassword : usersIDAndPasswords.entrySet()) {
            if (usersIDAndPassword.getKey().equals(mainUserID) && usersIDAndPassword.getValue().equals(mainPassword)) {
                Toast.makeText(this, R.string.main_login_confirmation, Toast.LENGTH_SHORT).show();
                correctCredentials = true;
                break;
            }
        }

        if(correctCredentials){
            intent = new Intent(MainActivity.this, MainLibraryPageActivity.class);
            intent.putExtra("userID", mainUserID);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, R.string.main_login_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void mainRegisterUserMethod(View view){
        intent = new Intent(MainActivity.this, RegisterUserActivity.class);
        startActivity(intent);
    }

    public void mainForgotPasswordMethod(View view){
        intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}