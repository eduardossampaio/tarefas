package com.apps.esampaio.tarefas.view.activity.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.core.Backup;
import com.apps.esampaio.tarefas.core.Settings;
import com.apps.esampaio.tarefas.core.entities.BackupItem;
import com.apps.esampaio.tarefas.core.entities.Subtask;
import com.apps.esampaio.tarefas.core.entities.Task;
import com.apps.esampaio.tarefas.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 15/08/2016.
 */

public class BackupTasksAdapter extends RecyclerView.Adapter<BackupTasksAdapter.Holder>{

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
    private List<Item> itens;

    public BackupTasksAdapter(List<BackupItem> backupItems) {
        this.itens = new ArrayList<>();
        for (BackupItem backupItem :backupItems) {
            itens.add(new Item(backupItem));
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_backup_tasks_item,parent,false);
        final Holder holder = new Holder(view);
        holder.selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Item item =itens.get(holder.getLayoutPosition());
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
        BackupItem item = itens.get(position).backupItem;
        Task task = item.getTask();
        holder.taskName.setText(task.getName());
        holder.backupDate.setText(StringUtils.append(true,"=Backup at=",item.getBackupDate().formatDate(),item.getBackupDate().formatTime()));
    }


    public void removeItem(BackupItem item) {
        int pos = this.itens.indexOf(new Item(item));
        this.itens.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public int getSelectedItensCount(){
        int count =0;
        for (Item item:itens) {
            if(item.selected)
                count++;
        }
        return count;
    }

    public List<BackupItem> getSelectedTasks(){
        List<BackupItem> backupItems = new ArrayList<>();
        for (Item item: this.itens) {
            if(item.selected)
                backupItems.add(item.backupItem);
        }
        return backupItems;
    }


}
