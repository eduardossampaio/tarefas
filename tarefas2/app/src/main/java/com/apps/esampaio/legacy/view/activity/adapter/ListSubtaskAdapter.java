package com.apps.esampaio.legacy.view.activity.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.apps.esampaio.R;
import com.apps.esampaio.legacy.core.Settings;
import com.apps.esampaio.legacy.core.entities.Subtask;
import com.apps.esampaio.legacy.core.entities.Task;
import com.apps.esampaio.legacy.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class ListSubtaskAdapter extends RecyclerView.Adapter<ListSubtaskAdapter.ViewHolder> {



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView taskName;
        private TextView taskDescription;
        private TextView taskDate;
        private CheckBox completed;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.activity_list_subtasks_item_name);
            taskDescription = (TextView) itemView.findViewById(R.id.activity_list_subtasks_item_description);
            completed= (CheckBox) itemView.findViewById(R.id.activity_list_subtasks_item_completed);
            taskDate= (TextView) itemView.findViewById(R.id.activity_list_subtasks_item_date);
        }
    }


    private List<Subtask> items;
    private Task item;
    private Context context;

    public ListSubtaskAdapter(Task item,Context context){
        this.context = context;
        this.item = item;
        this.items =new ArrayList<>();
        for (Subtask subtask:item.getSubtasks()) {
            items.add(subtask);
        }
    }


    public void addItemToEnd(Subtask task){
        int lastIndex = item.getSubtasks().size();
        notifyItemInserted(lastIndex);

    }
    public void addItem(Subtask subtask,int index) {
        items.add(index,subtask);
        notifyItemInserted(index);
    }

    public void deleteItem(Subtask item) {
        int pos = getItemPosition(item);
        if ( pos != -1){
            notifyItemRemoved(pos);
            this.items.remove(pos);
        }
    }
    public void refreshItem(Subtask item){
        int pos = getItemPosition(item);
        notifyItemChanged(pos);
    }
    public void refreshItem(Subtask subtask,int oldPos,int newPos){
        refreshItem(subtask);
        items.remove(oldPos);
        items.add(newPos,subtask);
        notifyItemMoved(oldPos,newPos);
    }
    public void refreshAll() {
        for (int i=0;i<items.size();i++) {
            notifyItemChanged(i);
        }
    }
    private int getItemPosition(Subtask item) {
        for(int i=0;i<items.size();i++){
            Subtask subtask = items.get(i);
            if(item.equals(subtask)){
                return i;
            }
        }
        return  -1;
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
        Settings settings = Settings.getInstance(this.context);
        boolean capitalize = settings.capitalizeFirst();
        Subtask subtask = item.getSubtasks().get(i);
        viewHolder.taskName.setText(StringUtils.capitalize(subtask.getName(),capitalize));
        if ( subtask.getDescription() != null && ! subtask.getDescription().isEmpty())
            viewHolder.taskDescription.setText(subtask.getDescription());
        else
            viewHolder.taskDescription.setText(context.getString(R.string.subtasks_no_description));


        if(subtask.getDateTime().dateSetted()){
            String formatedDate = subtask.getDateTime().formatDate();
            viewHolder.taskDate.setText(formatedDate);
            if ( subtask.getDateTime().timeSetted()){
                String at = context.getString(R.string.subtasks_schedule_date);
                String formatedTime = subtask.getDateTime().formatTime();
                viewHolder.taskDate.setText(StringUtils.append(true,formatedDate,at,formatedTime));
            }
        }else{
            viewHolder.taskDate.setText(context.getString(R.string.subtasks_no_date));
        }
        viewHolder.completed.setChecked(subtask.isComplete());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
