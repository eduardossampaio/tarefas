package com.apps.esampaio.tarefas.view.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.entities.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 28/06/2016.
 */

public abstract class ListSubtaskAdapter extends RecyclerView.Adapter<ListSubtaskAdapter.ViewHolder> {



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView taskName;
        private TextView taskDescription;
        private CheckBox completed;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.activity_list_subtasks_item_name);
            taskDescription = (TextView) itemView.findViewById(R.id.activity_list_subtasks_item_description);
            completed= (CheckBox) itemView.findViewById(R.id.activity_list_subtasks_item_completed);
        }
    }


    private List<Subtask> items;
    private Task item;


    private Context context;

    public ListSubtaskAdapter(Task item,Context context){
        this.context = context;
        this.items = new ArrayList<>();
        this.item = item;
    }

    public void refreshItens(List<Subtask> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public abstract void itemClicked(RecyclerView.ViewHolder viewHolder,Subtask item);

    public abstract void itemUpdated(Task task,Subtask subtask);

    public void itemLongClicked(RecyclerView.ViewHolder viewHolder,Subtask item){
    }


    @Override
    public ListSubtaskAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_list_subtasks_item2,viewGroup,false);

        final ListSubtaskAdapter.ViewHolder viewHolder = new ListSubtaskAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                final int position = viewHolder.getLayoutPosition();
                final Subtask subtask  = item.getSubtasks().get(position);
                itemClicked(viewHolder,subtask);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = viewHolder.getLayoutPosition();
                final Subtask subtask  = item.getSubtasks().get(position);
                itemLongClicked(viewHolder,subtask);
                return true;
            }
        });

        viewHolder.completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final int position = viewHolder.getLayoutPosition();
                final Subtask subtask  = item.getSubtasks().get(position);
                if(subtask.isComplete()!=isChecked) {
                    subtask.setComplete(isChecked);
                    itemUpdated(item,subtask);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListSubtaskAdapter.ViewHolder viewHolder, int i) {
        Subtask subtask = item.getSubtasks().get(i);
        viewHolder.taskName.setText(subtask.getName());
        if ( subtask.getDescription() != null && ! subtask.getDescription().isEmpty())
            viewHolder.taskDescription.setText(subtask.getDescription());
        else
            viewHolder.taskDescription.setText(context.getString(R.string.subtasks_no_description));
        viewHolder.completed.setChecked(subtask.isComplete());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
