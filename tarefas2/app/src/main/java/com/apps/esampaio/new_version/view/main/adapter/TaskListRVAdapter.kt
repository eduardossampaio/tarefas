package com.apps.esampaio.new_version.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.adprogressbarlib.AdCircleProgress
import com.apps.esampaio.legacy.R
import com.apps.esampaio.legacy.core.entities.Subtask
import com.apps.esampaio.legacy.core.entities.Task


class TaskListRVAdapter : RecyclerView.Adapter<TaskListRVAdapter.TaskViewHolder> {

    private val ITEM_TYPE_HEADER = 1
    private val ITEM_TYPE_LIST_ITEM = 2

    var taskListItem = mutableListOf<TaskListItem>()

    constructor(tasks: MutableList<Task>){
       this.taskListItem = createTaskListItem(tasks);
    }
    constructor(){

    }
    private fun createTaskListItem(tasks: MutableList<Task>) : MutableList<TaskListItem> {
        val taskListItem = mutableListOf<TaskListItem>()
        for (task  in tasks){
            taskListItem.add(TaskListItem (task, Subtask(),true) )
            for(subtask in task.subtasks){
                taskListItem.add(TaskListItem (task,subtask,false) )
            }
        }
        return taskListItem;
    }
    abstract  class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class TaskViewHolderItem (itemView: View) : TaskViewHolder(itemView){
        val taskName: TextView = itemView.findViewById(R.id.taskName) as TextView;
        val takDescription: TextView = itemView.findViewById(R.id.taskDescription) as TextView;
    }
    class TaskViewHolderHeader (itemView: View) : TaskViewHolder(itemView){
        val taskTitle = itemView.findViewById<TextView>(R.id.task_title);
        val progress = itemView.findViewById<AdCircleProgress>(R.id.task_progress);
    }

    override fun getItemViewType(position: Int): Int {
        return if (taskListItem[position].isHeader)
            this.ITEM_TYPE_HEADER
        else this.ITEM_TYPE_LIST_ITEM;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        if (viewType == this.ITEM_TYPE_HEADER){
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.new_subtask_item_view_header, parent, false);
            return TaskViewHolderHeader(view);
        }else {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.new_subtask_item_view, parent, false);
            return TaskViewHolderItem(view);
        }
    }

    override fun getItemCount(): Int {
        return this.taskListItem.size
    }

    override fun getItemId(position: Int): Long {
        return this.taskListItem[position].hashCode().toLong();
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
       val item = this.taskListItem[position]
        if (item.isHeader){
           bindHeader(holder as TaskViewHolderHeader, item)
       }else{
            bindItem(holder as TaskViewHolderItem, item)
       }
    }


    private fun bindHeader(holder: TaskViewHolderHeader, item: TaskListItem) {
        holder.taskTitle.text = item.task.name;
        holder.progress.progress = item.task.completedPercentage;
    }

    private  fun bindItem(holder: TaskViewHolderItem, task: TaskListItem){


        holder.taskName.text = task.subtask.name;
        holder.takDescription.text = task.subtask.description;


    }

    fun updateItems(tasks: MutableList<Task>){
        val newTaskItems = createTaskListItem(tasks)
        val diffCallback = TaskListItemDiffCallback(this.taskListItem,newTaskItems);
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.taskListItem.clear();
        this.taskListItem.addAll(newTaskItems);

        diffResult.dispatchUpdatesTo(this);

    }
}