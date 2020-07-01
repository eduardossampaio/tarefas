package com.apps.esampaio.new_version.view.main.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.esampaio.legacy.R
import com.apps.esampaio.legacy.core.Tasks
import com.apps.esampaio.legacy.core.entities.Subtask
import com.apps.esampaio.legacy.core.entities.Task
import com.apps.esampaio.new_version.view.main.adapter.TaskListRVAdapter
import com.apps.esampaio.new_version.view.task.NewTaskActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
import java.util.*


class NewMainActivity : AppCompatActivity() {
    lateinit var taskList : RecyclerView;
    lateinit var newTaskButton : ViewGroup;
    lateinit var tabBar : TabLayout;

    private lateinit var tasks : Tasks;

    private lateinit var taskListRVAdapter: TaskListRVAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)
        taskList = findViewById<RecyclerView>(R.id.task_list)
        newTaskButton = findViewById<LinearLayout>(R.id.new_task_button)
        tabBar = findViewById<TabLayout>(R.id.option_tabs)


        this.tasks = Tasks(this);

        createTasks()

        this.taskListRVAdapter = TaskListRVAdapter(emptyList<Task>().toMutableList());

        taskList.adapter = this.taskListRVAdapter
        taskList.layoutManager = LinearLayoutManager(this);

        newTaskButton.setOnClickListener {
            createNewTask();
        }

        tabBar.addOnTabSelectedListener(object : BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("MainScreen", "Tab selected: "+ tab.position);

                when (tab.position) {
                    0 -> {
                        showAllTask()
                    }
                    1 -> {
                        showTodayTasks()
                    }
                    2 -> {
                        showWeekTasks()
                    }
                    3 -> {
                        showCompletedTasks()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        showAllTask()

    }

    private fun showAllTask() {
        val tasks = tasks.tasks;
        taskListRVAdapter.updateItems(tasks)
    }

    private fun showTodayTasks() {
        val tasks = this.tasks.getTasksByDate(Date(System.currentTimeMillis()),false);
        taskListRVAdapter.updateItems(tasks)
    }

    private fun showWeekTasks() {
//        val tasks = this.tasks.getTasksByDate()
//        taskListRVAdapter.updateItems(tasks)
    }
    private fun showCompletedTasks() {
        val tasks = tasks.completedTasks;
        taskListRVAdapter.updateItems(tasks)
    }

    private fun createNewTask() {
        val intent = Intent(this, NewTaskActivity::class.java)
        startActivity(intent);
    }
    private fun createTasks() {

        val task = Task("Tarefa 1");

        var subtask = Subtask("subtarefa1", "blablala")
        subtask.taskDate = (Date(System.currentTimeMillis()))

        task.addSubtask(subtask)
        subtask = Subtask("subtarefa2", "blablala-blablabla");
        subtask.isComplete = true;
        task.addSubtask(subtask);

        tasks.addTask(task);

        val task2 = Task("Tarefa 2");

        subtask = Subtask("subtarefa3", "blablala");
        subtask.taskDate = Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 5) )
        subtask.isComplete = true;
        task2.addSubtask(subtask);

        subtask = Subtask("subtarefa4", "blablala");
        subtask.taskDate = Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 3) )
        subtask.isComplete = true;
        task2.addSubtask(subtask);

        tasks.addTask(task2);


    }
}