package com.apps.esampaio.tarefas.view.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.core.Settings;
import com.apps.esampaio.tarefas.core.entities.Task;
import com.apps.esampaio.tarefas.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;


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
    public ListTaskAdapter(Context context,List<Task> items){
        this.context = context;
        this.items = items;
    }

    public void refreshItens(List<Task> items){
        //for(int i=0;i<)
        this.items = items;
        notifyDataSetChanged();
    }

    private int getItemPosition(Task item){
        for (int i=0;i<items.size();i++) {
            Task task = items.get(i);
            if(task.equals(item)){
                return i;
            }
        }
        return -1;
    }

    public void addItem(Task task,int index){
        this.items.add(index,task);
        notifyItemInserted(index);
    }

    public void deleteItem(Task item) {
        int pos = getItemPosition(item);
        if ( pos != -1){
            notifyItemRemoved(pos);
            this.items.remove(pos);
        }
    }
    public void refreshItem(Task item){
        int pos = getItemPosition(item);
        notifyItemChanged(pos);
    }

    public void refreshItem(Task item,int oldPos,int newPos){
        Task toMove = items.get(oldPos);
        items.set(newPos, items.get(oldPos));
        items.set(newPos, toMove);

        int pos = getItemPosition(item);
        notifyItemChanged(pos);
        notifyItemMoved(newPos,oldPos);
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
        Settings settings = Settings.getInstance(this.context);
        boolean capitalize = settings.capitalizeFirst();
        viewHolder.taskName.setText(StringUtils.capitalize(task.getName(),capitalize));

        String completedTaskMessages;
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

    public List<Task> getItems() {
        return items;
    }
}
