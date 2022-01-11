package com.example.todo_list;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;

public class ConfirmActivity extends AppCompatActivity {
    Button confirmButton;
    Boolean confirmed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameObj = findViewById(R.id.usernameConfirm);
                EditText codeObj = findViewById(R.id.code);

                String username = usernameObj.getText().toString();
                String code = codeObj.getText().toString();

                Amplify.Auth.confirmSignUp(
                        username,
                        code,
                        result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete" ),
                        error -> confirmed = false

                );
                if (confirmed) {
                    Intent returnToLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(returnToLoginIntent);
                }else{
                    usernameObj.setText("");
                    codeObj.setText("");
                    Toast.makeText(getApplicationContext(), "Confirmation Failed", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
}
