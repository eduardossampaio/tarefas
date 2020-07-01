package com.apps.esampaio.legacy.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apps.esampaio.legacy.R;
import com.apps.esampaio.legacy.core.Backup;
import com.apps.esampaio.legacy.core.Tasks;
import com.apps.esampaio.legacy.core.entities.BackupItem;
import com.apps.esampaio.legacy.view.activity.adapter.BackupTasksAdapter;

import com.apps.esampaio.legacy.view.dialogs.MessageDialog;

import java.util.List;

/**
 * Created by eduardo on 15/08/2016.
 */

public class BackedUpTaskFragment extends Fragment {

    private TextView emptyMessage;
    private RecyclerView list;
    private Button restoreButton;
    private Button deleteButton;
    private BackupTasksAdapter adapter;
    private Tasks tasks;
    private Backup backup;

    private View layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.list_backup_tasks, container, false);
        list = (RecyclerView) layout.findViewById(R.id.list_backup_task_list);
        restoreButton = (Button) layout.findViewById(R.id.list_backup_task_button_restore);
        deleteButton = (Button) layout.findViewById(R.id.list_backup_task_button_delete);
        emptyMessage= (TextView) layout.findViewById(R.id.list_backup_task_empty_message);

        tasks = new Tasks(getContext());
        backup = new Backup(getContext());
        try {
            List<BackupItem> tasks = backup.getBackupedTasks();
            if(tasks.size()==0)
                deleteButton.setEnabled(false);
            adapter = new BackupTasksAdapter(getContext(),tasks) {
                @Override
                public void itemChanged(BackupTasksAdapter.Item item) {
                    int selectedItensCount = getSelectedItensCount();
                    if (selectedItensCount == 0) {
                        restoreButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                    }else if(haveSelectedMoreThanOne()) {
                        restoreButton.setEnabled(false);
                        deleteButton.setEnabled(true);
                    }else{
                        restoreButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                    }
                }
            };
            list.setAdapter(adapter);
            list.setLayoutManager(new LinearLayoutManager(getContext()));
            restoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restoreButtonClick();
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteButtonClick();
                }
            });
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
            emptyMessage.setEnabled(true);
            emptyMessage.setText(e.getMessage());
        }
        return layout;
    }

    public void refresh() throws Exception {
            List<BackupItem> tasks = backup.getBackupedTasks();
            if(tasks.size()==0){
                emptyMessage.setVisibility(View.VISIBLE);
                list.setVisibility(View.INVISIBLE);
                restoreButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }else{
                emptyMessage.setVisibility(View.INVISIBLE);
                list.setVisibility(View.VISIBLE);
            }
    }

    private void restoreButtonClick() {
        MessageDialog dialog = new MessageDialog(getContext(),getString(R.string.backup_task_restore_message), "") {

            public void onClick(DialogInterface dialog, int which) {
                restoreBackups();
            }
        };
        dialog.show();
    }
    private void deleteButtonClick() {
        MessageDialog dialog = new MessageDialog(getContext(),getString(R.string.backup_task_delete_message), "") {

            public void onClick(DialogInterface dialog, int which) {
                try {
                    deleteBackups();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        dialog.show();
    }

    private void deleteBackups() throws Exception {
        List<BackupItem> items = adapter.getSelectedTasks();
        Backup backup = new Backup(getContext());
        for (BackupItem item : items) {
            backup.delete(item);
            adapter.removeItem(item);
        }
        refresh();
    }
    private void restoreBackups(){
        List<BackupItem> items = adapter.getSelectedTasks();
        Backup backup = new Backup(getContext());
        for (BackupItem item : items) {
            if(tasks.exists(item.getTask())){
                Snackbar.make(layout,getString(R.string.backup_task_already_restored_message),Snackbar.LENGTH_SHORT).show();
            }else{
                backup.restore(item);
                Snackbar.make(layout,getString(R.string.backup_task_restore_done),Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
