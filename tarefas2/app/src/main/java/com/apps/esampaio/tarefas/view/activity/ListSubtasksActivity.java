package com.apps.esampaio.tarefas.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.Tasks;
import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.entities.Task;
import com.apps.esampaio.tarefas.view.dialogs.ConfirmationDialog;
import com.apps.esampaio.tarefas.view.dialogs.NewSubtaskDialog;
import com.apps.esampaio.tarefas.view.dialogs.OptionsDialog;
import com.apps.esampaio.tarefas.view.activity.adapter.ListSubtaskAdapter;

import java.util.Date;
import java.util.List;

public class ListSubtasksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyListMessage;
    private FloatingActionButton newTaskButton;

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
        newTaskButton = (FloatingActionButton)findViewById(R.id.list_subtask_new_task_button);
        tasks = new Tasks(this);

        int itemId = getIntent().getExtras().getInt("item");
        item = tasks.getTask(itemId);

        setTitle(item.getName());

        adapter = new ListSubtaskAdapter(item,this) {
            @Override
            public void itemClicked(RecyclerView.ViewHolder viewHolder, Subtask item) {
//                createDetailDialog(item);
//                startCreatelActivity(item);
            }

            @Override
            public void itemLongClicked(RecyclerView.ViewHolder viewHolder, Subtask item) {
                super.itemLongClicked(viewHolder, item);
                showOptionsDilaog(item);
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
//                Intent intent = new Intent(ListSubtasksActivity.this,CreateSubtaskActivity.class);
//                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        updateItems();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateItems(){
        List<Subtask> taskList = item.getSubtasks();
        if ( taskList.size()==0){
            emptyListMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }else{
            emptyListMessage.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.refreshItens(taskList);
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
                subtask.setName(name);
                subtask.setTaskDate(taskDate);
                subtask.setTaskTime(taskTime);
                subtask.setDescription(description);
                item.updateSubtask(subtask);
                tasks.updateTask(item);
                updateItems();
            }
        };
        dialog.show();
    }
    private void createDetailDialog(final Subtask subtask){
        Dialog dialog = new NewSubtaskDialog(this,subtask) {
            @Override
            public void onItemEntered(String name, String description,Date taskDate,Date taskTime) {
                subtask.setName(name);
                subtask.setDescription(description);
                subtask.setTaskDate(taskDate);
                subtask.setTaskTime(taskTime);
                item.updateSubtask(subtask);
                tasks.updateTask(item);
                updateItems();
            }
        };
        dialog.setTitle("-Subtask-");
        dialog.show();

    }

    private void createDeleteDialog(final Subtask subtask) {
        Dialog deleteDialog = new ConfirmationDialog(ListSubtasksActivity.this,getString(R.string.dialog_delete_subtask_title)+subtask.getName()+"?"){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                super.onClick(dialog, which);
                tasks.deleteSubtask(subtask);
                item.getSubtasks().remove(subtask);
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
            String message = subtask.getName();
            if ( subtask.isComplete())
                message += ListSubtasksActivity.this.getString(R.string.subtasks_updated_message_checked);
            else
                message += ListSubtasksActivity.this.getString(R.string.subtasks_updated_message_unchecked);
            Snackbar.make(layout,message,Snackbar.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }


    private void startCreatelActivity(Subtask subtask){
        Intent intent = new Intent(ListSubtasksActivity.this,SubtaskDialogActivity.class);
        intent.putExtra("subtask",subtask);
        startActivityForResult(intent,SubtaskDialogActivity.REQUEST_CODE_ADD,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==SubtaskDialogActivity.REQUEST_CODE_ADD){
            if(resultCode==SubtaskDialogActivity.RESULT_CODE_COMPLETE){
                Subtask subtask = (Subtask)data.getSerializableExtra("subtask");
                if(subtask!=null){
                    item.updateSubtask(subtask);
                    tasks.updateTask(item);
                }
            }
        }
    }
}
