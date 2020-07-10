package com.apps.esampaio.new_version.view.task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.apps.esampaio.R
import com.apps.esampaio.legacy.entities.Task


class TaskCategorySpinnerAdapter(ctx: Context, private var taskList: MutableList<Task>) : ArrayAdapter<Task>(ctx, 0, taskList) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(ctx);

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {

        return this.createView(position, recycledView, parent)

    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {

        return this.createView(position, recycledView, parent)

    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return if(! isNewTask(position)){
            getViewForTask(position,recycledView,parent)
        }else{
            getViewForNewTask(position,recycledView,parent)
        }

    }

    override fun getCount(): Int {
        return this.taskList.size + 1;
    }
        private fun getViewForTask(position: Int, view: View?, parent: ViewGroup?) : View{
        val itemView =
                view ?:
                layoutInflater.inflate(R.layout.activity_new_task_spinner, parent, false)
        val taskNameView = itemView.findViewById<TextView>(R.id.task_name);
        val colorIndicator = itemView.findViewById<View>(R.id.indicator_color)
        colorIndicator.visibility = View.VISIBLE
        val task = taskList[position];
        taskNameView.text =  task.name;
        return  itemView;
    }
    private fun getViewForNewTask(position: Int, view: View?, parent: ViewGroup?) : View {
        val itemView =
                view ?:
                layoutInflater.inflate(R.layout.activity_new_task_spinner, parent, false)
        val taskNameView = itemView.findViewById<TextView>(R.id.task_name);
        val colorIndicator = itemView.findViewById<View>(R.id.indicator_color)
        colorIndicator.visibility = View.GONE
        taskNameView.text = context.getString(R.string.new_category)
        return  itemView;
    }

    fun isNewTask(position: Int) : Boolean{
        return position == this.taskList.size;
    }

    fun addEnd(selectedTask: Task) {
        this.taskList.add(selectedTask);
        notifyDataSetChanged();
    }
}