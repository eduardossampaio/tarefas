package com.apps.esampaio.tarefas.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.core.Tasks;
import com.apps.esampaio.tarefas.core.entities.Subtask;
import com.apps.esampaio.tarefas.core.entities.Task;
import com.apps.esampaio.tarefas.view.activity.adapter.ListSubtaskAdapter;
import com.apps.esampaio.tarefas.view.dialogs.ConfirmationDialog;
import com.apps.esampaio.tarefas.view.dialogs.DetailSubtaskDialog;
import com.apps.esampaio.tarefas.view.dialogs.NewSubtaskDialog;
import com.apps.esampaio.tarefas.view.dialogs.NewTaskDialog;
import com.apps.esampaio.tarefas.view.dialogs.OptionsDialog;

import java.util.Date;
import java.util.List;

public class ListSubtasksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyListMessage;


    private ListSubtaskAdapter adapter;
    private Tasks tasks;
    private Task item;

    private View layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subtasks);

        layout = findViewById(R.id.activity_list_subtasks);
        recyclerView = (RecyclerView) findViewById(R.id.list_subtasks_items_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyListMessage = (TextView) findViewById(R.id.list_subtasks_empty_task_list);
        FloatingActionButton newTaskButton = (FloatingActionButton)findViewById(R.id.list_subtask_new_task_button);
        tasks = new Tasks(this);
        item = (Task) getIntent().getExtras().get("item");
        adapter = new ListSubtaskAdapter(item,this) {
            @Override
            public void itemClicked(RecyclerView.ViewHolder viewHolder, Subtask item) {
                createDetailDialog(item);
            }


            @Override
            public void itemUpdated(Task item,Subtask subtask) {
                new UpdateTaskAsync().execute(subtask);
            }
        };

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewSubtaskDialog();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        updateItems();
        updateActionBar();

    }
    void updateActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setSubtitle(item.getName());
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_subtasks_menu,menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.list_subtask_menu_edit:
                editTask();
                break;
            case R.id.list_subtask_menu_delete:
                deleteTask();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteTask(){
        Dialog deleteDialog = new ConfirmationDialog(this,getString(R.string.dialog_delete_task_title)+item.getName()+"?"){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                super.onClick(dialog, which);
                tasks.delete(item);
                finish();
            }
        };
        deleteDialog.show();
    }

    private void editTask(){
        Dialog editDialog = new NewTaskDialog(this,item) {
            @Override
            public void onItemEntered(String taskName) {
                item.setName(taskName);
                tasks.updateTask(item);
                updateActionBar();
            }
        };
        editDialog.show();
    }

    private void updateItems(){
        List<Subtask> taskList = item.getSubtasks();
        if ( taskList.size()==0){
            emptyListMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }else{
            emptyListMessage.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }


    private void showNewSubtaskDialog(){
        Dialog dialog = new NewSubtaskDialog(this) {
            @Override
            public void onItemEntered(String name, String description,Date taskDate,Date taskTime) {
                Subtask subtask = new Subtask(name,description);
                subtask.setTaskDate(taskDate);
                subtask.setTaskTime(taskTime);
                item.addSubtask(subtask);
                tasks.updateTask(item);
                item.sortSubtasks();
                adapter.addItem(subtask,item.getSubtaskIndex(subtask));
                updateItems();
            }
        };
        dialog.show();
    }

    private void showOptionsDilaog(final Subtask item){

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

    private void createEditDialog(final Subtask subtask) {
        Dialog dialog = new NewSubtaskDialog(this,subtask) {
            @Override
            public void onItemEntered(String name, String description,Date taskDate,Date taskTime) {
                editSubtask(subtask,name,description,taskDate,taskTime);
            }
        };
        dialog.show();
    }

    private void createDetailDialog(final Subtask subtask) {
        Dialog dialog = new DetailSubtaskDialog(this,subtask) {
            @Override
            public void onItemEntered(String name, String description,Date taskDate,Date taskTime) {
                editSubtask(subtask,name,description,taskDate,taskTime);
            }
        };
        dialog.show();
    }
    private void editSubtask(Subtask subtask,String newName, String newDescription,Date newDate,Date newTime){
        int oldPos = item.getSubtaskIndex(subtask);
        subtask.setName(newName);
        subtask.setTaskDate(newDate);
        subtask.setTaskTime(newTime);
        subtask.setDescription(newDescription);
        item.updateSubtask(subtask);
        tasks.updateTask(item);
        adapter.refreshItem(subtask);
        item.sortSubtasks();

        int newPos = item.getSubtaskIndex(subtask);
        if(oldPos != newPos){
            adapter.refreshItem(subtask,oldPos,newPos);
        }else{
            adapter.refreshItem(subtask);
        }
        updateItems();
    }
    private void editSubtask(Subtask subtask){
        editSubtask(subtask, subtask.getName(), subtask.getDescription(), subtask.getTaskDate(), subtask.getTaskTime());
    }

    private void createDeleteDialog(final Subtask subtask) {
        Dialog deleteDialog = new ConfirmationDialog(ListSubtasksActivity.this,getString(R.string.dialog_delete_subtask_title)+subtask.getName()+"?"){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                super.onClick(dialog, which);
                tasks.deleteSubtask(subtask);
                adapter.deleteItem(subtask);
                updateItems();
            }
        };

        deleteDialog.show();
    }

    private class UpdateTaskAsync extends AsyncTask<Subtask,Void,Subtask>{
        @Override
        protected Subtask doInBackground(Subtask... params) {
            Subtask subtask = params[0];
            tasks.updateTask(item);
            return subtask;
        }

        @Override
        protected void onPostExecute(Subtask subtask) {
            super.onPostExecute(subtask);
            updateItems();
            String message = subtask.getName().trim();
            message+=" ";
            if ( subtask.isComplete()) {
                message += ListSubtasksActivity.this.getString(R.string.subtasks_updated_message_checked);
            }
            else {
                message += ListSubtasksActivity.this.getString(R.string.subtasks_updated_message_unchecked);
            }
            Snackbar.make(layout,message,Snackbar.LENGTH_SHORT).show();
            editSubtask(subtask);
        }
    }

}
