package com.example.todo1;

import static com.example.todo1.TodoContract.TodoEntry.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TODO = "todo";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TASK = "task";
    private static final String COLUMN_CHECKED = "checked";



    private static final String CREATE_TODO_TABLE =
            "CREATE TABLE " + TABLE_TODO + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TASK + " TEXT,"
                    + COLUMN_CHECKED + " INTEGER DEFAULT 0"
                    + ")";



    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    //public long addTodoItem(Todo todo) {
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    ContentValues values = new ContentValues();
    //    values.put(COLUMN_TASK, todo.getTask());
    //    values.put(COLUMN_CHECKED, todo.isChecked());
    //    long id = db.insert(TABLE_TODO, null, values);
    //    db.close();
    //    //return id;
    //}

    public void addTodoItem(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, todo.getTask());
        values.put(COLUMN_CHECKED, todo.isChecked());
        db.insert(TABLE_TODO, null, values);
        db.close();
        //return id;
    }

    @SuppressLint("Range")
    public List<Todo> getAllTodoItems() {
        List<Todo> todoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                todo.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_TASK)));
                todo.setChecked(cursor.getInt(cursor.getColumnIndex(COLUMN_CHECKED)) == 1);
                todoList.add(todo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return todoList;
    }
    // Delete a todo item from the database
    public void deleteTodoItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateTodoItemChecked(long id, boolean isChecked) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CHECKED, isChecked ? 1 : 0);

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    public void updateTodoItem(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, todo.getTask());
        values.put(COLUMN_CHECKED, todo.isChecked());

        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { String.valueOf(todo.getId()) });
        db.close();
    }
}
