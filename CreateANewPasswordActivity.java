package com.example.libraryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.text.Editable;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.text.TextWatcher;
import android.support.v7.app.AppCompatActivity;

public class CreateANewPasswordActivity extends AppCompatActivity implements TextWatcher{

    private Intent intent = null;
    private String userID = "", usersPreviousPassword = "";
    private Button createANewPasswordButton;
    private TextView createANewPassword1EditTextView, createANewPassword2EditTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_anew_password);

        createANewPasswordButton = findViewById(R.id.createANewPasswordButton);

        createANewPassword1EditTextView = findViewById(R.id.createANewPassword1EditTextView);
        createANewPassword2EditTextView = findViewById(R.id.createANewPassword2EditTextView);

        createANewPassword1EditTextView.addTextChangedListener(this);
        createANewPassword2EditTextView.addTextChangedListener(this);

        intent = getIntent();
        if(intent != null) {
            userID = intent.getStringExtra("userID");
            usersPreviousPassword = intent.getStringExtra("previousPassword");
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        createANewPasswordButton.setEnabled(!createANewPassword1EditTextView.getText().toString().trim().isEmpty() &&
                !createANewPassword2EditTextView.getText().toString().trim().isEmpty());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {}

    public void createANewPasswordMethod(View view){
        String usersPassword1 = createANewPassword1EditTextView.getText().toString().trim();
        String usersPassword2 = createANewPassword2EditTextView.getText().toString().trim();

        createANewPasswordButton.setEnabled(false);
        createANewPassword1EditTextView.setText("");
        createANewPassword2EditTextView.setText("");

        if(!usersPassword1.equals(usersPassword2)){
            Toast.makeText(this, R.string.new_password_does_not_matches, Toast.LENGTH_SHORT).show();
        }
        else if(usersPassword1.equals(usersPassword2) && usersPassword1.equals(usersPreviousPassword)){
            Toast.makeText(this, R.string.new_password_is_same_as_previous, Toast.LENGTH_SHORT).show();
        }
        else{
            UsersDatabase usersDatabase = new UsersDatabase(this);
            usersDatabase.updateUsersPassword(userID, usersPassword1);
            Toast.makeText(this, R.string.new_password_is_correct, Toast.LENGTH_SHORT).show();

            intent = new Intent(CreateANewPasswordActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void createANewPasswordReturnMethod(View view){
        intent = new Intent(CreateANewPasswordActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}