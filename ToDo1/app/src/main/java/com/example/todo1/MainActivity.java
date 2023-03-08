package com.example.todo1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Todo> todoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView todoRecyclerView = findViewById(R.id.todo_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        todoRecyclerView.setLayoutManager(layoutManager);

        TodoAdapter todoAdapter = new TodoAdapter(todoList);
        todoRecyclerView.setAdapter(todoAdapter);

        FloatingActionButton fab = findViewById(R.id.add_todo_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override

        }
        );
    }
}




