package com.apps.esampaio.new_version.view.task

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.apps.esampaio.legacy.R
import com.apps.esampaio.legacy.Tasks
import com.apps.esampaio.legacy.core.entities.DateTime
import com.apps.esampaio.legacy.entities.Subtask
import com.apps.esampaio.legacy.entities.Task
import com.apps.esampaio.new_version.view.task.adapter.TaskCategorySpinnerAdapter
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment
import java.util.*

class NewTaskActivity : AppCompatActivity() {

    lateinit var cdp: CalendarDatePickerDialogFragment
    lateinit var rtpd: RadialTimePickerDialogFragment;

    private val FRAG_TAG_DATE_PICKER = "DATE_PICKER_1"
    private val FRAG_TAG_TIME_PICKER = "TIME_PICKER_1"

    lateinit  var tasksService: Tasks

    lateinit var taskCategorySpinner: Spinner;
    lateinit var taskAdapter: TaskCategorySpinnerAdapter;
    lateinit var selectDate : Button;
    lateinit var selectHour : Button;
    lateinit var saveButton : Button;
    lateinit var cancelButton : Button;

    lateinit var subtaskName : EditText;
    lateinit var subtaskDescription : EditText;


    var subtask = Subtask();
    var selectedTask : Task? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        tasksService = Tasks(this);
        val tasks = tasksService.tasks;

        taskCategorySpinner = findViewById(R.id.task_category);
        selectDate = findViewById(R.id.add_date)
        selectHour = findViewById(R.id.add_time)
        saveButton = findViewById(R.id.save_task_button)
        cancelButton = findViewById(R.id.cancel_task_button)

         subtaskName = findViewById(R.id.subtask_name)
        subtaskDescription  =  findViewById(R.id.subtask_description)

        taskAdapter = TaskCategorySpinnerAdapter(this,tasks.toList());


        taskCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("NewTaskActivity", "onItemSelected: $position")

                if(taskAdapter.isNewTask(position)){

                }else{
                    selectedTask = tasks[position];
                }
            }
        }

        taskCategorySpinner.adapter = taskAdapter;

      setupDatePicker()
        setupTimePicker()



        selectDate.setOnClickListener {
            Log.d("NewTaskActivity", "selecionou data");
            showDatePicker();

        }
        selectHour.setOnClickListener {
            Log.d("NewTaskActivity", "selecionou hora");
            showTimePicker()
        }

        saveButton.setOnClickListener {
            saveButtonPressed()
        }

        cancelButton.setOnClickListener {
            cancelButtonPressed()
        }

    }

    override fun onResume() {
        super.onResume()
        taskAdapter.notifyDataSetChanged();
        //let subtask vinda de outra tela
    }

    private fun showDatePicker() {

        cdp.show(supportFragmentManager, FRAG_TAG_DATE_PICKER)
    }

    private fun showTimePicker() {

        rtpd.show(supportFragmentManager, FRAG_TAG_TIME_PICKER)
    }

    private fun saveButtonPressed() {

        if (selectedTask != null) {
            this.subtask.name = subtaskName.text.toString();
            this.subtask.description = subtaskDescription.text.toString();
            this.subtask.taskId = selectedTask!!.id;
            selectedTask!!.addSubtask(this.subtask);
            tasksService.saveOrUpdateTask(selectedTask)
        }

//        this.tasksService.updateTask()

//        item.addSubtask(subtask)
//        tasks.updateTask(item)
//        item.sortSubtasks()
//        adapter.addItem(subtask, item.getSubtaskIndex(subtask))
//        updateItems()
    }
    private fun cancelButtonPressed() {

    }



    private fun setupDatePicker(){

        cdp = CalendarDatePickerDialogFragment()
                .setOnDateSetListener { dialog, year, monthOfYear, dayOfMonth ->
//                    subtask.dateTime.setDate(year, monthOfYear, dayOfMonth)
//                    displayDate()
//                    enableTime()
                }
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setDoneText(getString(R.string.dialog_message_ok))
//                .setThemeCustom(R.style.MyCustomBetterPickersDialogs)
                .setThemeLight()
                .setCancelText(getString(R.string.dialog_message_cancel))
        val timeToSet: DateTime = DateTime.getCurrentDateTime()

        val y = timeToSet.year
        val m = timeToSet.month
        val d = timeToSet.day
        cdp.setPreselectedDate(y, m, d)
    }

    private fun setupTimePicker(){
        rtpd = RadialTimePickerDialogFragment()
                .setOnTimeSetListener { dialog, hourOfDay, minute ->
//                    subtask.dateTime.setTime(hourOfDay, minute)
//                    displayTime()
                }
                .setThemeLight()
                .setThemeCustom(R.style.MyCustomBetterPickersDialogs)
                .setDoneText(getString(R.string.dialog_message_ok))
                .setCancelText(getString(R.string.dialog_message_cancel))
    }
}