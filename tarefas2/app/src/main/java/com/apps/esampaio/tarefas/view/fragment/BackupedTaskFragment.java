package com.apps.esampaio.tarefas.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.core.Backup;
import com.apps.esampaio.tarefas.core.entities.BackupItem;
import com.apps.esampaio.tarefas.core.entities.Task;
import com.apps.esampaio.tarefas.view.activity.adapter.BackupTasksAdapter;

import java.util.List;

/**
 * Created by eduardo on 15/08/2016.
 */

public class BackupedTaskFragment extends Fragment {

    private RecyclerView list;
    private Button restoreButton;
    private Button deleteButton;
    private BackupTasksAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_backup_tasks, container, false);
        list = (RecyclerView) view.findViewById(R.id.list_backup_task_list);
        restoreButton = (Button) view.findViewById(R.id.list_backup_task_button_restore);
        deleteButton = (Button) view.findViewById(R.id.list_backup_task_button_delete);
        Backup backup = new Backup(getContext());
        try {
            List<BackupItem> tasks = backup.getBackupedTasks();
            adapter = new BackupTasksAdapter(tasks) {
                @Override
                public void itemChanged(BackupTasksAdapter.Item item) {
                    int selectedItensCount = getSelectedItensCount();
                    if (selectedItensCount == 0)
                        restoreButton.setEnabled(false);
                    else
                        restoreButton.setEnabled(true);
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

        } catch (Exception e) {

        }
        return view;
    }

    private void restoreButtonClick() {
        List<BackupItem> items = adapter.getSelectedTasks();
        Backup backup = new Backup(getContext());
        for (BackupItem item : items) {
            backup.restore(item);
        }
    }
    private void deleteButtonClick() {
        List<BackupItem> items = adapter.getSelectedTasks();
        Backup backup = new Backup(getContext());
        for (BackupItem item : items) {
            backup.delete(item);
            adapter.removeItem(item);
        }
    }
}
