package com.example.todo1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Define itemsAdapter as a member variable of the MainActivity class
    private ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ListView and initialize the itemsAdapter
        ListView lvItems = findViewById(R.id.lvItems);
        ArrayList<String> items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        // Setup the "Add" button to add new items
        FloatingActionButton fab = findViewById(R.id.add_todo_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    private void addItem() {
        // Create an EditText view to prompt the user for input
        final EditText input = new EditText(this);

        // Set the hint text for the EditText view
        input.setHint("Enter new task");

        // Create an AlertDialog to display the EditText view
        new AlertDialog.Builder(this)
                .setTitle("Add New Task")
                .setMessage("Enter the task name below:")
                .setView(input)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // When the user clicks "Add" on the AlertDialog, add the new task to the list
                        String taskName = input.getText().toString();
                        itemsAdapter.add(taskName);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}





