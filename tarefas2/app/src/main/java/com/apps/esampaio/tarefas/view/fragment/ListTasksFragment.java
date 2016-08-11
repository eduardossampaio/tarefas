package com.apps.esampaio.tarefas.view.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.core.Tasks;
import com.apps.esampaio.tarefas.core.entities.Task;
import com.apps.esampaio.tarefas.view.activity.ListSubtasksActivity;
import com.apps.esampaio.tarefas.view.activity.adapter.ListTaskAdapter;
import com.apps.esampaio.tarefas.view.dialogs.ConfirmationDialog;
import com.apps.esampaio.tarefas.view.dialogs.NewTaskDialog;
import com.apps.esampaio.tarefas.view.dialogs.OptionsDialog;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListTasksFragment extends Fragment {
    protected RecyclerView tasksList;

    protected ListTaskAdapter adapter;
    protected TextView emptyListMessage;
    protected FloatingActionButton newTaskButton;
    protected Tasks tasks;

    private Bundle savedInstanceState;
    public ListTasksFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View baseLayout =  inflater.inflate(R.layout.activity_list_tasks, container, false);

        this.savedInstanceState = savedInstanceState;
        tasks = new Tasks(getActivity());
        tasksList = (RecyclerView) baseLayout.findViewById(R.id.list_tasks_items_list);
        tasksList.setLayoutManager(new LinearLayoutManager(getActivity()));
        emptyListMessage = (TextView) baseLayout.findViewById(R.id.list_tasks_empty_task_list);
        newTaskButton = (FloatingActionButton)baseLayout.findViewById(R.id.list_task_new_task_button);

        adapter = new ListTaskAdapter(getActivity(),getContentTasks()) {
            @Override
            public void itemClicked(RecyclerView.ViewHolder viewHolder, Task item) {
                Intent intent = new Intent(getActivity(),ListSubtasksActivity.class);
                intent.putExtra("item",item);
                startActivity(intent);
            }

            @Override
            public void itemLongClicked(RecyclerView.ViewHolder viewHolder, Task item) {
                super.itemLongClicked(viewHolder, item);
                createOptionsMenu(item);
            }
        };

        tasksList.setAdapter(adapter);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTaskDialog();
            }
        });
        updateItems();
        refreshItems();
        return baseLayout;

    }

    protected List<Task> getContentTasks(){
        return tasks.getTasks();
    }
    protected void setEmptyMessage(int messageId){
        setEmptyMessage(getActivity().getString(messageId));
    }
    protected void setEmptyMessage(String message){
        emptyListMessage.setText(message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshItems();
        updateItems();
    }




    private void createOptionsMenu(final Task item) {
        final int [] messagesIds={
                R.string.dialog_options_edit,
                R.string.dialog_options_delete,
        };
        Dialog dialog = new OptionsDialog(getActivity(),messagesIds){
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
            tasksList.setVisibility(View.INVISIBLE);
        }else{
            emptyListMessage.setVisibility(View.INVISIBLE);
            tasksList.setVisibility(View.VISIBLE);
        }
    }
    private void refreshItems(){
        List<Task> taskList = getContentTasks();
        adapter.refreshItens(taskList);
    }

    private void createEditDialog(final Task item){
        Dialog editDialog = new NewTaskDialog(getActivity(),item) {
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
        Dialog deleteDialog = new ConfirmationDialog(getActivity(),getString(R.string.dialog_delete_subtask_title)+item.getName()+"?"){
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



    private void createNewTaskDialog(){
        Dialog dialog = new NewTaskDialog(getActivity()) {
            @Override
            public void onItemEntered(String taskName) {
                Task task  = new Task(taskName);
                try{
                    tasks.addTask(task);
                    insertItem(task);
                    updateItems();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        dialog.show();

    }


}
