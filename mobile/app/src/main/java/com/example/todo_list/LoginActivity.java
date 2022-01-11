package com.example.todo_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.core.Amplify;

public class LoginActivity extends AppCompatActivity {
    Button signUp;
    Button login;
    Boolean loggedIn = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        AuthUser currentUser = Amplify.Auth.getCurrentUser();
        if (currentUser != null){
            Intent returnToMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(returnToMain);
        }
        signUp = findViewById(R.id.signUpConstraint);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               authenticateSignIn();
            }
        });

    }
    private void authenticateSignIn(){
        EditText usernameObj = findViewById(R.id.loginUsername);
        EditText passwordObj = findViewById(R.id.loginPassword);

        String username = usernameObj.getText().toString();
        String password = passwordObj.getText().toString();
        if(username.equals("")){
            Toast.makeText(getApplicationContext(), "Please Enter Username", Toast.LENGTH_SHORT).show();
        }else if (password.equals("")){
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
        }else {
            Amplify.Auth.signIn(
                    username,
                    password,
                    result -> loginSuccess(result),
                    error -> loginReset(error, usernameObj, passwordObj)
            );
        }
    }
    private void loginSuccess(AuthSignInResult authSignInResult) {
        //Go to the chat screen
        Intent intent = new Intent(this, TodoActivity.class);
        startActivity(intent);
    }

    private void loginReset(AuthException exception, EditText usernameObj, EditText passwordObj){
        usernameObj.setText("");
        passwordObj.setText("");
    }
}
