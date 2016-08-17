package com.apps.esampaio.tarefas.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.core.entities.Task;

import java.util.Date;
import java.util.List;



public class TodayTasksFragment extends ListTasksFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setEmptyMessage(getActivity().getString(R.string.today_task_fragment_empty_message));
        return view;
    }

    @Override
    protected List<Task> getContentTasks() {
        return tasks.getTasksByDate(new Date(System.currentTimeMillis()),false);
    }

}
