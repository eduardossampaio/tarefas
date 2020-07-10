package com.apps.esampaio.new_version.view.task

import android.app.Dialog
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.apps.esampaio.R
import com.apps.esampaio.legacy.Tasks
import com.apps.esampaio.legacy.core.entities.DateTime
import com.apps.esampaio.legacy.entities.Subtask
import com.apps.esampaio.legacy.entities.Task
import com.apps.esampaio.new_version.view.task.adapter.TaskCategorySpinnerAdapter
import com.apps.esampaio.new_version.view.task.dialogs.NewCategoryDialog
import com.apps.esampaio.new_version.view.task.dialogs.NewCategoryDialogListener
import com.apps.esampaio.new_version.view.utils.DisableViewTextWatcher
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment
import com.noowenz.customdatetimepicker.CustomDateTimePicker
import java.util.*

class NewTaskActivity : AppCompatActivity(), NewCategoryDialogListener{

    /** Views **/
    lateinit var taskCategorySpinner: Spinner;
    lateinit var taskCategoryAdapter: TaskCategorySpinnerAdapter;

    lateinit var saveButton : Button;
    lateinit var cancelButton : Button;
    lateinit var addDateTimeButton : Button
    lateinit var deleteDateButton : ImageButton

    lateinit var subtaskName : EditText;
    lateinit var subtaskDescription : EditText;
    lateinit var dateTimePicker: CustomDateTimePicker;
    /*****/

    var subtask = Subtask();
    var selectedTask : Task? = null;
    var selectedDate: Date? = null;
    lateinit  var tasksService: Tasks
    lateinit var allTasks : MutableList<Task>;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        tasksService = Tasks(this);
        allTasks = tasksService.tasks;

        taskCategorySpinner = findViewById(R.id.task_category);

        saveButton = findViewById(R.id.save_task_button)
        cancelButton = findViewById(R.id.cancel_task_button)
        addDateTimeButton = findViewById(R.id.add_date_time);
        deleteDateButton = findViewById(R.id.remove_date);

        subtaskName = findViewById(R.id.subtask_name)
        subtaskDescription  =  findViewById(R.id.subtask_description)

        taskCategoryAdapter = TaskCategorySpinnerAdapter(this,allTasks);


        taskCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("NewTaskActivity", "onItemSelected: $position")

                if(taskCategoryAdapter.isNewTask(position)){
                    createNewCategory();
                }else{
                    selectedTask = allTasks[position];
                }
            }
        }

        taskCategorySpinner.adapter = taskCategoryAdapter;

        addDateTimeButton.setOnClickListener {
            dateTimePicker.showDialog();
        }


        saveButton.setOnClickListener {
            saveButtonPressed()
        }

        cancelButton.setOnClickListener {
            cancelButtonPressed()
        }

        deleteDateButton.setOnClickListener {
            onDateDeleted();
        }

        subtaskName.addTextChangedListener(DisableViewTextWatcher(saveButton, disableOnInit = true))

    }

    private fun createNewCategory() {
        val newCategoryDialog = NewCategoryDialog(this);
        newCategoryDialog.listener = this;
        newCategoryDialog.show();

    }

    override fun onResume() {
        super.onResume()
        taskCategoryAdapter.notifyDataSetChanged();
        setupDateTimePicker();

        //let subtask vinda de outra tela
    }

    private fun onDateSelected(dateSelected:Date) {
        this.selectedDate = dateSelected;
        this.deleteDateButton.visibility = View.VISIBLE
        this.addDateTimeButton.text = selectedDate.toString();

    }

    private fun onDateDeleted() {
        this.selectedDate = null;
        this.deleteDateButton.visibility = View.GONE
        this.addDateTimeButton.text = getString(R.string.enter_date_time)
    }



    private fun saveButtonPressed() {

        if (selectedTask != null) {
            this.subtask.name = subtaskName.text.toString();
            this.subtask.description = subtaskDescription.text.toString();
            this.subtask.taskId = selectedTask!!.id;
            selectedTask!!.addSubtask(this.subtask);
            tasksService.saveOrUpdateTask(selectedTask)
            finish();
        }

    }

    private fun cancelButtonPressed() {
        finish();
    }



    private fun setupDateTimePicker(){
        dateTimePicker = CustomDateTimePicker(this, object  : CustomDateTimePicker.ICustomDateTimeListener{
                override fun onCancel() {

                }

                override fun onSet(dialog: Dialog, calendarSelected: Calendar, dateSelected: Date, year: Int, monthFullName: String, monthShortName: String, monthNumber: Int, day: Int, weekDayFullName: String, weekDayShortName: String, hour24: Int, hour12: Int, min: Int, sec: Int, AM_PM: String) {
                    onDateSelected(dateSelected);
                }

            });
        dateTimePicker.setDate(Calendar.getInstance())
    }



    override fun onSave(categoryName: String, selectedColor: String) {
        Toast.makeText(this,"$categoryName =>  $selectedColor", Toast.LENGTH_SHORT).show()
        this.selectedTask = Task(categoryName);
        allTasks.add(this.selectedTask!!);
        taskCategoryAdapter.addEnd(selectedTask!!);
        taskCategorySpinner.setSelection(allTasks.size -1)


    }

    override fun onCancel() {
        taskCategorySpinner.setSelection(0);

    }
}