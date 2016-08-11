package com.apps.esampaio.tarefas.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Task;

import java.util.ArrayList;
import java.util.List;



public class CompletedTasksFragment extends ListTasksFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setEmptyMessage(getActivity().getString(R.string.completed_task_fragment_empty_message));
        return view;
    }

    @Override
    protected List<Task> getContentTasks() {
        return tasks.getCompletedTasks();
    }
}
