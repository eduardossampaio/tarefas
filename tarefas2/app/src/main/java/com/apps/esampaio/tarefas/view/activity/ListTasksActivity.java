package com.apps.esampaio.tarefas.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.apps.esampaio.tarefas.BuildConfig;
import com.apps.esampaio.tarefas.Constants;
import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.Tasks;
import com.apps.esampaio.tarefas.entities.Task;
import com.apps.esampaio.tarefas.view.dialogs.ConfirmationDialog;
import com.apps.esampaio.tarefas.view.dialogs.MessageDialog;
import com.apps.esampaio.tarefas.view.dialogs.NewTaskDialog;
import com.apps.esampaio.tarefas.view.dialogs.OptionsDialog;
import com.apps.esampaio.tarefas.view.activity.adapter.ListTaskAdapter;

import java.util.List;

public class ListTasksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListTaskAdapter adapter;
    private TextView emptyListMessage;
    private FloatingActionButton newTaskButton;

    private Tasks tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);
        tasks = new Tasks(this);

        recyclerView = (RecyclerView) findViewById(R.id.list_tasks_items_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyListMessage = (TextView) findViewById(R.id.list_tasks_empty_task_list);
        newTaskButton = (FloatingActionButton)findViewById(R.id.list_task_new_task_button);

        adapter = new ListTaskAdapter(this) {
            @Override
            public void itemClicked(RecyclerView.ViewHolder viewHolder, Task item) {
                Intent intent = new Intent(ListTasksActivity.this,ListSubtasksActivity.class);
                intent.putExtra("item",item.getId());
                startActivity(intent);
            }

            @Override
            public void itemLongClicked(RecyclerView.ViewHolder viewHolder, Task item) {
                super.itemLongClicked(viewHolder, item);
                createOptionsMenu(item);
            }
        };
        recyclerView.setAdapter(adapter);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTaskDialog();
            }
        });
        updateItems();
        refreshItems();
        showVersionNotes();

    }

    private void showVersionNotes() {
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE_VERSION_NOTES_KEY, 0);
        boolean displayed = sharedPreferences.getBoolean(versionName,false);
        if ( ! displayed){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(versionName,true);
            Dialog dialog = new MessageDialog(this,R.string.dialog_release_notes_title,R.string.change_notes);
            dialog.show();
            editor.apply();
        }
    }


    private void createOptionsMenu(final Task item) {
        final int [] messagesIds={
                R.string.dialog_options_edit,
                R.string.dialog_options_delete,
        };
        Dialog dialog = new OptionsDialog(this,messagesIds){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                super.onClick(dialog, which);
                int selectedId = messagesIds[which];

                if ( selectedId == R.string.dialog_options_edit){
                    createEditDialog(item);
                }else if ( selectedId == R.string.dialog_options_delete){
                    createDeleteDialog(item);
                }
            }
        };
        dialog.show();
    }

    private void insertItem(Task task) {
        adapter.addItemToEnd(task);
    }

    private void updateItems(){
        int itemCount= this.adapter.getItemCount();
        if ( itemCount==0){
            emptyListMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }else{
            emptyListMessage.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    private void refreshItems(){
        List<Task> taskList = tasks.getTasks();
        adapter.refreshItens(taskList);
    }

    private void createEditDialog(final Task item){
        Dialog editDialog = new NewTaskDialog(this,item) {
            @Override
            public void onItemEntered(String taskName) {
                item.setName(taskName);
                tasks.updateTask(item);
                adapter.refreshItem(item);
            }
        };

        editDialog.show();
    }

    private void createDeleteDialog(final Task item){
        Dialog deleteDialog = new ConfirmationDialog(ListTasksActivity.this,getString(R.string.dialog_delete_subtask_title)+item.getName()+"?"){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                super.onClick(dialog, which);
                adapter.deleteItem(item);
                tasks.delete(item);
                updateItems();
            }
        };

        deleteDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateItems();
    }

    private void createNewTaskDialog(){
        Dialog dialog = new NewTaskDialog(this) {
            @Override
            public void onItemEntered(String taskName) {
                Task task  = new Task(taskName);
                try{
                    tasks.addTask(task);
//                    updateItems();
                    insertItem(task);
                    updateItems();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        dialog.show();
//        Intent intent = new Intent(this, AddTaskActivity.class);
//        startActivity(intent);
    }


}
