package com.apps.esampaio.new_version.view.main.adapter

import androidx.recyclerview.widget.DiffUtil

fun List<*>.deepEquals(other : List<*>) =
        this.size == other.size && this.mapIndexed { index, element -> element == other[index] }.all { it }

class TaskListItemDiffCallback(private val oldItems:List<TaskListItem>, private val newItems:List<TaskListItem>) : DiffUtil.Callback() {



    override fun getOldListSize(): Int {
       return oldItems.size;
    }

    override fun getNewListSize(): Int {
        return newItems.size;
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition];
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].task.name == newItems[newItemPosition].task.name &&
                    oldItems[oldItemPosition].task.subtasks.deepEquals(newItems[newItemPosition].task.subtasks)
    }
}