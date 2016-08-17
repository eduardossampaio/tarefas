package com.apps.esampaio.tarefas.view.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public abstract class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView taskName;
        private TextView progressNumber;
        private ProgressBar progressBar;

        public ViewHolder(View itemView){
                super(itemView);
                taskName = (TextView) itemView.findViewById(R.id.list_task_item_task_name);
                progressNumber = (TextView) itemView.findViewById(R.id.list_task_item_completed_number);
                progressBar = (ProgressBar) itemView.findViewById(R.id.list_task_item_progress);
            }
    }


    private List<Task> items;
    private Context context;

    public ListTaskAdapter(Context context){
        this.context = context;
        this.items = new ArrayList<>();
    }

    public void refreshItens(List<Task> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public abstract void itemClicked(RecyclerView.ViewHolder viewHolder,Task item);

    public void itemLongClicked(RecyclerView.ViewHolder viewHolder,Task item){

    }


    @Override
    public ListTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_list_task_item,viewGroup,false);

        final ListTaskAdapter.ViewHolder viewHolder = new ListTaskAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                Task task = items.get( pos );
                itemClicked(viewHolder,task);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                Task task = items.get( pos );
                itemLongClicked(viewHolder,task);
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListTaskAdapter.ViewHolder viewHolder, int i) {
        Task task = items.get(i);
        viewHolder.taskName.setText(task.getName());

        String completedTaskMessages ="";
        if(task.getSubtasksNumber()==0){
            completedTaskMessages=context.getString(R.string.task_no_subtasks_registered);
        }else {
            completedTaskMessages = this.context.getResources().getString(R.string.task_item_completed_tasks);
            completedTaskMessages+= task.getCompleteSubtasksNumber()+"/"+task.getSubtasksNumber();
        }

        float percentage = ( (float)task.getCompleteSubtasksNumber() / (float)  task.getSubtasksNumber() ) * 100;

        viewHolder.progressNumber.setText(completedTaskMessages);
        viewHolder.progressBar.setProgress( (int) percentage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
