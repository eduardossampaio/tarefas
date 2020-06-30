package com.apps.esampaio.legacy.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.esampaio.legacy.R;
import com.apps.esampaio.legacy.entities.Subtask;
import com.apps.esampaio.legacy.view.listeners.GestureDetectorTouchListener;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;

import java.util.Calendar;
import java.util.Date;

public class SubtaskDialogActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD  = 1;
    public static final int REQUEST_CODE_EDIT = 2;

    public static final int RESULT_CODE_COMPLETE = 3;
    public static final int RESULT_CODE_CANCElED = 4;

    private static final String FRAG_TAG_DATE_PICKER = "1";

    private Button createButton;
    private Button cancelButton;

    private EditText name;
    private EditText description;
    private EditText date;
    private EditText time;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private Subtask subtask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_layout);

        this.name = (EditText) findViewById(R.id.dialog_new_subtask_name);
        this.description = (EditText) findViewById(R.id.dialog_new_subtask_description);
        this.date = (EditText) findViewById(R.id.dialog_new_subtask_date);
        this.time = (EditText) findViewById(R.id.dialog_new_subtask_time);

        cancelButton = (Button) findViewById(R.id.dialog_button_1);
        createButton = (Button) findViewById(R.id.dialog_button_2);

        cancelButton.setText("Cancel");
        createButton.setText("OK");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(RESULT_CODE_CANCElED);
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.HOUR, hour);
                calendar.set(Calendar.MINUTE, minute);

                Date date = calendar.getTime();
                if(subtask==null){
                    subtask= new Subtask();
                }

                subtask.setName(name.getText().toString());
                subtask.setDescription(description.getText().toString());

                Intent intent = getIntent();
                intent.putExtra("subtask",subtask);
                setResult(RESULT_CODE_COMPLETE,intent);
                finish();//finishing activity
            }
        });

        this.date.setOnTouchListener(new GestureDetectorTouchListener(this) {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                createDatePicker();
                return super.onSingleTapUp(e);
            }
        });

        this.time.setOnTouchListener(new GestureDetectorTouchListener(this){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                createTimePicker();
                return super.onSingleTapUp(e);
            }
        });
        Subtask subtask = (Subtask) getIntent().getExtras().getSerializable("subtask");

        if (subtask != null) {
            this.name.setText(subtask.getName());
            this.description.setText(subtask.getDescription());


        }

    }

    private void createDatePicker() {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                        SubtaskDialogActivity.this.year = year;
                        SubtaskDialogActivity.this.month = monthOfYear;
                        SubtaskDialogActivity.this.day = day;
                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setDoneText("-OK-")
                .setCancelText("-Cancel-");
        cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    private void createTimePicker() {
        TimePickerBuilder tpb = new TimePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .addTimePickerDialogHandler(new TimePickerDialogFragment.TimePickerDialogHandler() {
                    @Override
                    public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                        SubtaskDialogActivity.this.hour = hour;
                        SubtaskDialogActivity.this.minute = minute;
                    }
                })
                .setStyleResId(R.style.BetterPickersDialogFragment_Light);
        tpb.show();
    }


}
