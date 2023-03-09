package com.example.todo1;

//import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> todoList;
    private TodoDatabaseHelper databaseHelper;
    //private Context context;


    public TodoAdapter(List<Todo> todoList) {
        this.todoList = todoList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.taskTextView.setText(todo.getTask());
        holder.checkBox.setChecked(todo.isChecked());

        int color = todo.isChecked() ? Color.parseColor("#009688") : Color.parseColor("#FF9800");
        holder.itemView.setBackgroundColor(color);
        holder.taskTextView.setBackgroundColor(color);

        int flags = todo.isChecked() ? holder.taskTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : holder.taskTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG);
        holder.taskTextView.setPaintFlags(flags);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todo.setChecked(isChecked);
            TodoDatabaseHelper databaseHelper = new TodoDatabaseHelper(buttonView.getContext());
            databaseHelper.updateTodoItem(todo);

            int checkedColor = isChecked ? Color.parseColor("#009688") : Color.parseColor("#FF9800");
            holder.itemView.setBackgroundColor(checkedColor);
            holder.taskTextView.setBackgroundColor(checkedColor);

            int checkedFlags = isChecked ? holder.taskTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : holder.taskTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.taskTextView.setPaintFlags(checkedFlags);

            new Handler().postDelayed(() -> notifyItemChanged(holder.getBindingAdapterPosition()), 50);
        });
    }



    @Override
    public int getItemCount() {
        return todoList.size();
    }




    //public void removeItem(int position) {
   //     todoList.remove(position);
    //    notifyItemRemoved(position);
    //}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView taskTextView;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            taskTextView = itemView.findViewById(R.id.task_text);
            checkBox = itemView.findViewById(R.id.checkbox);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ((MainActivity) v.getContext()).deleteItem(position);
            }
            return true;
        }


    }
}
