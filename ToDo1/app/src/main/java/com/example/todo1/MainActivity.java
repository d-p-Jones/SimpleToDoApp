package com.example.todo1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Todo> todoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private TodoDatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new TodoDatabaseHelper(this);

        recyclerView = findViewById(R.id.todo_list);
        todoAdapter = new TodoAdapter(todoList);
        recyclerView.setAdapter(todoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        loadTodoList();

        FloatingActionButton fab = findViewById(R.id.add_todo_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add Item");
                View dialogView = getLayoutInflater().inflate(R.layout.add_todo_dialog, null);
                final EditText input = dialogView.findViewById(R.id.edit_task_name);
                builder.setView(dialogView);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newItem = input.getText().toString();
                        Todo todo = new Todo();
                        todo.setTask(newItem);
                        addTodoItem(todo);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void loadTodoList() {
        new AsyncTask<Void, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(Void... params) {
                return databaseHelper.getAllTodoItems();
            }

            @Override
            protected void onPostExecute(List<Todo> newTodoList) {
                int oldSize = todoList.size();
                todoList.clear();
                todoList.addAll(newTodoList);
                int newSize = todoList.size();
                if (newSize > oldSize) {
                    todoAdapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }

    private void addTodoItem(Todo todo) {
        databaseHelper.addTodoItem(todo);
        int position = todoList.size();
        todoList.add(todo);
        todoAdapter.notifyItemInserted(position);
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            deleteItem(position);
            Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();

        }
    };

    void deleteItem(int position) {
        if (position >= 0 && position < todoList.size()) {
            Todo todo = todoList.get(position);
            databaseHelper.deleteTodoItem(todo.getId());
            todoList.remove(position);
            todoAdapter.notifyItemRemoved(position);
        }
    }
}