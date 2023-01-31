package com.example.libraryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.text.Editable;
import android.content.Intent;
import android.widget.TextView;
import android.text.TextWatcher;
import android.support.v7.app.AppCompatActivity;

public class RegisterUserActivity extends AppCompatActivity implements TextWatcher{

    private Button registerUserRegisterButton;
    private TextView registerUserNameEditTextView, registerUserPasswordEditTextView, registerGeneratedUserIDTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerUserRegisterButton = findViewById(R.id.registerUserRegisterButton);

        registerUserNameEditTextView = findViewById(R.id.registerUserNameEditTextView);
        registerUserPasswordEditTextView = findViewById(R.id.registerUserPasswordEditTextView);
        registerGeneratedUserIDTextView = findViewById(R.id.registerGeneratedUserIDTextView);

        registerUserNameEditTextView.addTextChangedListener(this);
        registerUserPasswordEditTextView.addTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        registerUserRegisterButton.setEnabled(!registerUserNameEditTextView.getText().toString().trim().isEmpty() &&
                !registerUserPasswordEditTextView.getText().toString().trim().isEmpty());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {}

    public void registerUserMethod(View view){
        String userName = registerUserNameEditTextView.getText().toString().trim();
        String password = registerUserPasswordEditTextView.getText().toString().trim();

        UsersDatabase usersDatabase = new UsersDatabase(this);
        boolean previousAccountExists = usersDatabase.checkUsersNamesAndPasswords(userName, password);

        if(previousAccountExists){
            Toast.makeText(this, R.string.register_user_details_exists, Toast.LENGTH_SHORT).show();
            registerGeneratedUserIDTextView.setText("");
        }
        else{
            GenerateUserID generateUserID = new GenerateUserID();
            String userID = generateUserID.checkUniqueID(usersDatabase.getUsersIDsAndPasswords());

            User user = new User(userName, password, userID, null);
            usersDatabase.addUserDetails(user);
            Toast.makeText(this, R.string.register_user_successful, Toast.LENGTH_SHORT).show();

            registerGeneratedUserIDTextView.setEnabled(true);
            String generatedUserIDMessage = getString(R.string.register_user_generated_userID, userID);
            registerGeneratedUserIDTextView.setText(generatedUserIDMessage);
        }

        registerUserNameEditTextView.setText("");
        registerUserPasswordEditTextView.setText("");
        registerUserRegisterButton.setEnabled(false);
    }

    public void registerReturnMethod(View view){
        Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
        startActivity(intent);
    }
}