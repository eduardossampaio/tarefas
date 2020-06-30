package com.apps.esampaio.new_version.view.main.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.LinearLayout
import com.apps.esampaio.legacy.R
import com.apps.esampaio.legacy.core.Tasks
import com.apps.esampaio.legacy.core.entities.Subtask
import com.apps.esampaio.legacy.core.entities.Task
import com.apps.esampaio.new_version.view.main.adapter.TaskListRVAdapter
import com.apps.esampaio.new_version.view.task.NewTaskActivity

class NewMainActivity : AppCompatActivity() {
    lateinit var taskList : RecyclerView;
    lateinit var newTaskButton : ViewGroup;

    private lateinit var tasks : Tasks;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)
        taskList = findViewById(R.id.task_list) as RecyclerView
        newTaskButton = findViewById(R.id.new_task_button) as LinearLayout

        this.tasks = Tasks(this);

        val completedTasks = tasks.tasks

        taskList.adapter = TaskListRVAdapter(completedTasks);
        taskList.layoutManager = LinearLayoutManager(this);

        newTaskButton.setOnClickListener {
            createNewTask();
        }

    }

    private fun createNewTask() {
        val intent = Intent(this, NewTaskActivity::class.java)
        startActivity(intent);
    }
    private fun createTasks() {

        val task = Task("Tarefa 1");


        task.addSubtask(Subtask("subtarefa1", "blablala"));
        task.addSubtask(Subtask("subtarefa2", "blablala-blablabla"));

        tasks.addTask(task);

        val task2 = Task("Tarefa 2");

        task2.addSubtask(Subtask("subtarefa3", "blablala"));
        task2.addSubtask(Subtask("subtarefa4", "blablala-blablabla"));

        tasks.addTask(task2);


    }
}