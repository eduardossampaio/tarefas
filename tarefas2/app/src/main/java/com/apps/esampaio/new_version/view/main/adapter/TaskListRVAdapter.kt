package com.apps.esampaio.new_version.view.main.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apps.esampaio.legacy.R
import com.apps.esampaio.legacy.core.entities.Task

class TaskListRVAdapter : RecyclerView.Adapter<TaskListRVAdapter.TaskViewHolder> {

    var taskList = mutableListOf<Task>()

    constructor(tasks: MutableList<Task>){
        this.taskList = tasks;
    }
    constructor(){

    }
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskName) as TextView;
        val takDescription: TextView = itemView.findViewById(R.id.taskDescription) as TextView;


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.new_task_item_view, parent,false);
        return TaskViewHolder(view);
    }

    override fun getItemCount(): Int {
        return this.taskList.size;
    }

    override fun getItemId(position: Int): Long {
        return this.taskList[position].hashCode().toLong();
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position];

        holder.taskName.text = task.name;

        when (task.subtasksNumber) {
            0 -> {
                holder.takDescription.text = "Nenhuma tarefa"
            }
            1 -> {
                holder.takDescription.text = "1 tarefa"
            }
            else -> {
                holder.takDescription.text =   "${task.subtasksNumber} tarefas"
            }
        }
    }

    fun updateItems(tasks: MutableList<Task>){
        this.taskList = tasks;
        notifyDataSetChanged()
    }
}