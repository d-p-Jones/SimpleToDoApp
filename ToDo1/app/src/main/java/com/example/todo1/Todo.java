package com.example.todo1;

public class Todo {
    private long id;
    private String task;
    private boolean isChecked;

    public Todo(long id, String task, boolean isChecked) {
        this.id = id;
        this.task = task;
        this.isChecked = isChecked;
    }

    public Todo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}