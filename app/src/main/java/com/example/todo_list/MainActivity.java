package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private Button addItemButton;
    private ListView todoListView;
    private TextView completedText;
    public int completed = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(this.getSupportActionBar()!=null)
            this.getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoListView = findViewById(R.id.TodoListView);
        addItemButton = findViewById(R.id.AddButton);
        completedText = findViewById(R.id.CompleteCounter);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        todoListView.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    private void setUpListViewListener() {
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Task Deleted", Toast.LENGTH_LONG).show();
                items.remove(position);
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
        if (!(inputString.equals(""))) {
            items.add(inputString);
            input.setText("");
            itemsAdapter.notifyDataSetChanged();
        }else{
            Context context = getApplicationContext();
            Toast.makeText(context, items.get(0), Toast.LENGTH_LONG).show();
        }
    }

    private void updateCompletedText(){
        String completedString = "Completed: " + completed;
        completedText.setText(completedString);
    }
}