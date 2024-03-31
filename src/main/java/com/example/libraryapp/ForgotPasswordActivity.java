package com.example.libraryapp;

import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.text.Editable;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.text.TextWatcher;
import android.support.v7.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity implements TextWatcher {

    private Intent intent = null;
    private Button forgotPasswordButton;
    private TextView forgotPasswordUserNameEditTextView, forgotPasswordUserIDEditTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        forgotPasswordUserNameEditTextView = findViewById(R.id.forgotPasswordUserNameEditTextView);
        forgotPasswordUserIDEditTextView = findViewById(R.id.forgotPasswordUserIDEditTextView);

        forgotPasswordUserNameEditTextView.addTextChangedListener(this);
        forgotPasswordUserIDEditTextView.addTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        forgotPasswordButton.setEnabled(!forgotPasswordUserNameEditTextView.getText().toString().trim().isEmpty() &&
                !forgotPasswordUserIDEditTextView.getText().toString().trim().isEmpty());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    public void forgotPasswordMethod(View view) {
        String userName = forgotPasswordUserNameEditTextView.getText().toString().trim();
        String userID = forgotPasswordUserIDEditTextView.getText().toString().trim();

        forgotPasswordButton.setEnabled(false);
        forgotPasswordUserIDEditTextView.setText("");
        forgotPasswordUserNameEditTextView.setText("");

        UsersDatabase database = new UsersDatabase(this);
        boolean previousAccountExists = database.checkUsersNameAndUserIDs(userName, userID);

        if (!previousAccountExists) {
            Toast.makeText(this, R.string.forgot_password_no_account_exists, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, R.string.forgot_password_account_exists, Toast.LENGTH_SHORT).show();

            String previousPassword = database.getUsersPassword(userID);

            intent = new Intent(ForgotPasswordActivity.this, CreateANewPasswordActivity.class);
            intent.putExtra("userID", userID);
            intent.putExtra("previousPassword", previousPassword);
            startActivity(intent);
        }
    }

    public void forgotPasswordReturnMethod(View view) {
        intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
        startActivity(intent);
    }
}