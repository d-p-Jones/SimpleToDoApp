package com.example.todo1;

import android.provider.BaseColumns;

public final class TodoContract {
    private TodoContract() {}

    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_NAME_TASK = "task";
        public static final String COLUMN_NAME_CHECKED = "checked";
    }
}