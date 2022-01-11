package com.example.todo_list;

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

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.GraphQLRequest;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TodoActivity extends AppCompatActivity {

    private ArrayList<Todo> todoItems;
    private ArrayList<String> itemNames;
    private ArrayAdapter<String> itemsAdapter;
    private Button addItemButton;
    private Button signOutButton;
    private ListView todoListView;
    private TextView completedText;
    public int completed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        todoListView = findViewById(R.id.TodoListView);
        addItemButton = findViewById(R.id.AddButton);
        completedText = findViewById(R.id.CompleteCounter);
        signOutButton = findViewById(R.id.signOut);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });
        itemNames = new ArrayList<>();
        todoItems = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemNames);
        todoListView.setAdapter(itemsAdapter);
        setUpListViewListener();
        AuthUser currUser = Amplify.Auth.getCurrentUser();
        if (currUser == null) {
            Intent returnToLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(returnToLoginIntent);
        }
        String currUsername = currUser.getUsername().toLowerCase(Locale.ROOT);
        Amplify.DataStore.query(Todo.class,
                todos -> {
                    while (todos.hasNext()) {
                        Todo todo = todos.next();
                        if (todo.getUser() != null) {
                            if (todo.getUser().equals(currUsername)) {
                                if (todo.getCompleted() == null) {
                                    todoItems.add(todo);
                                    String name = todo.getName();
                                    itemNames.add(name);
                                    itemsAdapter.notifyDataSetChanged();
                                } else {
                                    completed++;
                                    updateCompletedText();
                                }
                            }
                        }
                    }
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );

        Amplify.DataStore.observe(Todo.class,
                started -> Log.i("Tutorial", "Observation began."),
                change -> Log.i("Tutorial", change.item().toString()),
                failure -> Log.e("Tutorial", "Observation failed.", failure),
                () -> Log.i("Tutorial", "Observation complete.")
        );

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signOut(
                        () -> returnToMainPage(),
                        error -> signOutError()
                );
            }
        });

    }

    private void returnToMainPage() {
        Intent returnToLoginIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(returnToLoginIntent);
    }

    private void signOutError() {
        Toast.makeText(getApplicationContext(), "Failed To Sign Out", Toast.LENGTH_SHORT).show();
    }

    private void setUpListViewListener() {
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Task Deleted", Toast.LENGTH_LONG).show();
                Todo curr = todoItems.get(position);
                Date date = new Date();
                int offsetMillis = TimeZone.getDefault().getOffset(date.getTime());
                int offsetSeconds = (int) TimeUnit.MILLISECONDS.toSeconds(offsetMillis);
                Temporal.DateTime temporalDateTime = new Temporal.DateTime(date, offsetSeconds);
                Todo update = curr.copyOfBuilder().completed("Completed").due(temporalDateTime).build();
                Amplify.DataStore.save(update,
                        updated -> Log.i("Tutorial", "Updated AWS Database"),
                        failure -> Log.i("Tutorial", "Failed to Update"));
                Log.i("Test", curr.getId());

                todoItems.remove(position);
                itemNames.remove(position);
                itemsAdapter.notifyDataSetChanged();
                completed++;
                updateCompletedText();
                return false;
            }
        });
    }

    private void addItem(View view) {
        EditText input = findViewById(R.id.inputItem);
        String inputString = input.getText().toString();
        String currUsername = Amplify.Auth.getCurrentUser().getUsername();
        Todo item = Todo.builder()
                .name(inputString).user(currUsername)
                .build();
        Amplify.DataStore.save(item,
                success -> Log.i("Tutorial", "Saved item: " + success.item().getName()),
                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
        );

        if (!(inputString.equals(""))) {
            todoItems.add(item);
            itemNames.add(inputString);
            input.setText("");
            itemsAdapter.notifyDataSetChanged();
        } else {
            Context context = getApplicationContext();
            Toast.makeText(context, "Please Type Text", Toast.LENGTH_LONG).show();
        }
    }

    private void updateCompletedText() {
        String completedString = "Completed: " + completed;
        completedText.setText(completedString);
    }
}
