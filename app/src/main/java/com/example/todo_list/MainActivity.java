package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.GraphQLRequest;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Todo;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Todo> todoItems;
    private ArrayList<String> itemNames;
    private ArrayAdapter<String> itemsAdapter;
    private Button addItemButton;
    private ListView todoListView;
    private TextView completedText;
    public int completed = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            try {
                Amplify.addPlugin(new AWSApiPlugin());
                Amplify.addPlugin(new AWSDataStorePlugin());
                Amplify.addPlugin(new AWSCognitoAuthPlugin());
                Amplify.configure(getApplicationContext());
            }catch (Amplify.AlreadyConfiguredException configuredException){
               Log.i("Tutorial", "Being ed");
            }
            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }

        AuthUser currentUser = Amplify.Auth.getCurrentUser();
        Intent nextPage;
        if (currentUser == null){
            nextPage = new Intent(getApplicationContext(), LoginActivity.class);
        }else{
            nextPage = new Intent(getApplicationContext(), TodoActivity.class);
        }

        startActivity(nextPage);
        finish();
    }
}