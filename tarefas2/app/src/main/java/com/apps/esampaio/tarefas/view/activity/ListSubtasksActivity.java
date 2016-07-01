package com.apps.esampaio.tarefas.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.Tasks;
import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.entities.Task;
import com.apps.esampaio.tarefas.view.Dialogs.ConfirmationDialog;
import com.apps.esampaio.tarefas.view.Dialogs.NewSubtaskDialog;
import com.apps.esampaio.tarefas.view.Dialogs.OptionsDialog;
import com.apps.esampaio.tarefas.view.activity.adapter.ListSubtaskAdapter;

import java.util.List;

public class ListSubtasksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyListMessage;
    private FloatingActionButton newTaskButton;

    private ListSubtaskAdapter adapter;
    private Tasks tasks;
    private Task item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subtasks);

        final View layout = findViewById(R.id.activity_list_subtasks);

        recyclerView = (RecyclerView) findViewById(R.id.list_subtasks_items_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyListMessage = (TextView) findViewById(R.id.list_subtasks_empty_task_list);
        newTaskButton = (FloatingActionButton)findViewById(R.id.list_subtask_new_task_button);
        tasks = new Tasks(this);


        item = (Task)  getIntent().getExtras().getSerializable("item");

        adapter = new ListSubtaskAdapter(item,this) {
            @Override
            public void itemClicked(RecyclerView.ViewHolder viewHolder, Subtask item) {
//                Intent intent = new Intent(ListSubtasksActivity.this,DetailSubtask.class);
//                intent.putExtra("subtask",item);
//                startActivity(intent);

            }

            @Override
            public void itemLongClicked(RecyclerView.ViewHolder viewHolder, Subtask item) {
                super.itemLongClicked(viewHolder, item);
                showOptionsDilaog(item);
            }

            @Override
            public void itemUpdated(Task item) {
                tasks.updateTask(item);
                updateItems();
                Snackbar.make(layout,"Teste",Snackbar.LENGTH_LONG).show();
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

    }

    private void updateItems(){
//        item = tasks.getTask(item.getId());
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
            public void onItemEntered(String name, String description) {
                Subtask subtask = new Subtask(name,description);
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
            public void onItemEntered(String name, String description) {
                subtask.setName(name);
                subtask.setDescription(description);
                item.updateTask(subtask);
                tasks.updateTask(item);
                updateItems();
            }
        };
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

}
