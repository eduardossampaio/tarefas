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
import com.apps.esampaio.tarefas.core.entities.BackupItem;
import com.apps.esampaio.tarefas.core.entities.Task;
import com.apps.esampaio.tarefas.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 15/08/2016.
 */

public class BackupTasksAdapter extends RecyclerView.Adapter<BackupTasksAdapter.Holder>{

    private Context context;

    public static class Holder extends RecyclerView.ViewHolder{

        private TextView taskName;
        private TextView backupDate;
        private CheckBox selected;
        public Holder(View itemView) {
            super(itemView);
            taskName   = (TextView) itemView.findViewById(R.id.list_backup_task_item_name);
            backupDate = (TextView) itemView.findViewById(R.id.list_backup_task_item_date);
            selected   = (CheckBox) itemView.findViewById(R.id.list_backup_task_item_check);
        }
    }
    public class Item{

        private BackupItem backupItem;
        private boolean selected;
        public Item(BackupItem backupItem) {
            this.backupItem = backupItem;
            selected = false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return backupItem.equals(item.backupItem);

        }

        @Override
        public int hashCode() {
            return backupItem.hashCode();
        }
    }
    private List<Item> items;

    public BackupTasksAdapter(Context context,List<BackupItem> backupItems) {
        this.items = new ArrayList<>();
        this.context = context;
        for (BackupItem backupItem :backupItems) {
            items.add(new Item(backupItem));
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_backup_tasks_item,parent,false);
        final Holder holder = new Holder(view);
        holder.selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Item item = items.get(holder.getLayoutPosition());
                item.selected = isChecked;
                itemChanged(item);
            }
        });
        return holder;
    }

    public void itemChanged(Item item){

    }
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        BackupItem item = items.get(position).backupItem;
        Task task = item.getTask();
        holder.taskName.setText(task.getName());
        holder.backupDate.setText(StringUtils.append(true,context.getString(R.string.backup_task_time_message),item.getBackupDate().formatDate(),item.getBackupDate().formatTime()));
    }


    public void removeItem(BackupItem item) {
        int pos = this.items.indexOf(new Item(item));
        this.items.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getSelectedItensCount(){
        int count =0;
        for (Item item: items) {
            if(item.selected)
                count++;
        }
        return count;
    }

    public List<BackupItem> getSelectedTasks(){
        List<BackupItem> backupItems = new ArrayList<>();
        for (Item item: this.items) {
            if(item.selected)
                backupItems.add(item.backupItem);
        }
        return backupItems;
    }

    public boolean haveSelectedMoreThanOne(){
        for(int i = 0; i<this.items.size(); i++){
            if(!items.get(i).selected)
                continue;
            for(int j = i+1; j<this.items.size(); j++){
                if( items.get(i).backupItem.equalsName(items.get(j).backupItem) && items.get(j).selected){
                    return true;
                }
            }
        }
        return false;
    }
}
