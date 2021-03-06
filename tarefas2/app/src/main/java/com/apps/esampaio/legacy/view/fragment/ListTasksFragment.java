package com.apps.esampaio.legacy.view.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.esampaio.R;
import com.apps.esampaio.legacy.core.Backup;
import com.apps.esampaio.legacy.core.Settings;
import com.apps.esampaio.legacy.core.Tasks;
import com.apps.esampaio.legacy.core.entities.Task;
import com.apps.esampaio.legacy.view.activity.ListSubtasksActivity;
import com.apps.esampaio.legacy.view.activity.adapter.ListTaskAdapter;
import com.apps.esampaio.legacy.view.dialogs.OptionsDialog;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListTasksFragment extends Fragment {
    private RecyclerView tasksList;
    private  ListTaskAdapter adapter;
    private  TextView emptyListMessage;
    private  FloatingActionButton newTaskButton;
    protected Tasks tasks;
    private View baseLayout;

    public ListTasksFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        baseLayout =  inflater.inflate(R.layout.activity_list_tasks, container, false);


        tasks = new Tasks(getActivity());
        tasksList = (RecyclerView) baseLayout.findViewById(R.id.list_tasks_items_list);
        tasksList.setLayoutManager(new LinearLayoutManager(getActivity()));
        emptyListMessage = (TextView) baseLayout.findViewById(R.id.list_tasks_empty_task_list);
        newTaskButton = (FloatingActionButton)baseLayout.findViewById(R.id.list_task_new_task_button);

        adapter = new ListTaskAdapter(getActivity(),getContentTasks()) {
            @Override
            public void itemClicked(RecyclerView.ViewHolder viewHolder, Task item) {
                try {
                    Intent intent = new Intent(getActivity(), ListSubtasksActivity.class);
                    intent.putExtra("item", item);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
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


    protected void createOptionsMenu(final Task item) {
        Settings settings = Settings.getInstance(getContext());
        int [] messagesIds;
        if(settings.manualBackup()) {
            messagesIds = new int[]{
                    R.string.dialog_options_edit,
                    R.string.dialog_options_delete,
                    R.string.dialog_options_backup
            };
        }else{
            messagesIds = new int[]{
                    R.string.dialog_options_edit,
                    R.string.dialog_options_delete,
            };
        }
        final int[] messageIdsPointer =messagesIds;
        Dialog dialog = new OptionsDialog(getActivity(),messagesIds){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                super.onClick(dialog, which);
                int selectedId = messageIdsPointer[which];

                if ( selectedId == R.string.dialog_options_edit){
                    createEditDialog(item);
                }else if ( selectedId == R.string.dialog_options_delete){
                    createDeleteDialog(item);
                }else if(selectedId == R.string.dialog_options_backup){
                    backupTask(item);
                }
            }
        };
        dialog.show();
    }

    private void backupTask(Task item){
        try {
            new Backup(getContext()).saveTask(item);
            Snackbar.make(baseLayout,getString(R.string.backup_task_backup_done),Snackbar.LENGTH_SHORT).show();
        } catch (Exception e) {
            Snackbar.make(baseLayout,"Error :"+e.getMessage(),Snackbar.LENGTH_SHORT).show();

        }
    }
    protected int getItemPosition(List<Task> tasksList,Task task){
        return tasksList.indexOf(task);
    }
    protected void insertItem(Task task) {
        List<Task> taskList = getContentTasks();
        adapter.addItem(task,getItemPosition(taskList,task));
    }

    protected void updateItems(){
        int itemCount= this.adapter.getItemCount();
        if ( itemCount==0){
            emptyListMessage.setVisibility(View.VISIBLE);
            tasksList.setVisibility(View.INVISIBLE);
        }else{
            emptyListMessage.setVisibility(View.INVISIBLE);
            tasksList.setVisibility(View.VISIBLE);
        }
    }
    protected void refreshItems(){
        List<Task> taskList = getContentTasks();
        adapter.refreshItens(taskList);
    }

    protected void createEditDialog(final Task item){
        Dialog editDialog = new com.apps.esampaio.legacy.view.dialogs.NewTaskDialog(getActivity(),item) {
            @Override
            public void onItemEntered(String taskName) {
                editItem(item,taskName);
            }
        };

        editDialog.show();
    }

    protected void editItem(Task item,String taskName){
        int oldIndex = getItemPosition(adapter.getItems(),item);
        item.setName(taskName);
        tasks.updateTask(item);
        int newIndex = getItemPosition(getContentTasks(),item);
        if(newIndex != oldIndex){
            adapter.refreshItem(item,oldIndex,newIndex);
        }else {
            adapter.refreshItem(item);
        }
    }

    protected void createDeleteDialog(final Task item){
//        Dialog deleteDialog = new ConfirmationDialog(getActivity(),getString(R.string.dialog_delete_task_title)+item.getName()+"?"){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                super.onClick(dialog, which);
//                adapter.deleteItem(item);
//                tasks.delete(item);
//                updateItems();
//            }
//        };

//        deleteDialog.show();
    }



    protected void createNewTaskDialog(){
        Dialog dialog = new com.apps.esampaio.legacy.view.dialogs.NewTaskDialog(getActivity()) {
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
