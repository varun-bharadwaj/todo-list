package com.example.todo_list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class SignUpActivity extends AppCompatActivity {
    Button signUp;
    Boolean signedUp = true;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameObj = findViewById(R.id.username);
                EditText passwordObj = findViewById(R.id.password);
                EditText emailObj = findViewById(R.id.email);

                String username = usernameObj.getText().toString();
                String password = passwordObj.getText().toString();
                String email = emailObj.getText().toString();

                if(email.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                }else if(username.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter Username", Toast.LENGTH_SHORT).show();
                }else if (password.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    AuthSignUpOptions options = AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(), email)
                            .build();
                    Amplify.Auth.signUp(username, password, options,
                            result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                            error -> signedUp = false
                    );
                    if (signedUp) {
                        Intent confirmIntent = new Intent(getApplicationContext(), ConfirmActivity.class);
                        startActivity(confirmIntent);
                    }else{
                        usernameObj.setText("");
                        passwordObj.setText("");
                        emailObj.setText("");
                    }
                }
            }
        });
    }

}
