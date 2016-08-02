package com.apps.esampaio.tarefas.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Subtask;
import com.apps.esampaio.tarefas.view.listeners.GestureDetectorTouchListener;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eduardo on 28/06/2016.
 */

public abstract class NewSubtaskDialog extends AppCompatDialog {


    private static final java.lang.String FRAG_TAG_DATE_PICKER = "DATE_PICKER_1";
    private static final String FRAG_TAG_TIME_PICKER = "TIME_PICKER_1";
    private Context context;
    private FragmentManager fragmentManager;
    private Button createButton;
    private Button cancelButton;

    private EditText name;
    private EditText description;
    private EditText date;
    private EditText time;
    private View layout;

    //TODO por isso em uma classe prória
    private int year = -1;
    private int month = -1;
    private int day = -1;
    private int hour = -1;
    private int minute = -1;

    private AlertDialog.Builder builder;

    public NewSubtaskDialog(final Context context) {
        super(context);
        this.context = context;
        this.fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

        layout = (View) LayoutInflater.from(context).inflate(R.layout.dialog_new_subtask, null);

        this.name = (EditText) layout.findViewById(R.id.dialog_new_subtask_name);
        this.description = (EditText) layout.findViewById(R.id.dialog_new_subtask_description);
        this.date = (EditText) layout.findViewById(R.id.dialog_new_subtask_date);
        this.time = (EditText) layout.findViewById(R.id.dialog_new_subtask_time);

        builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.dialog_new_subtask_title));
        builder.setPositiveButton(context.getResources().getString(R.string.dialog_message_ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Date taskDate = null;
                Date taskTime = null;
                Calendar calendar = Calendar.getInstance();
                if (year != -1 && month != -1 && day != -1) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    taskDate = calendar.getTime();
                }
                if (hour != -1 && minute != -1) {
                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    taskTime = calendar.getTime();
                }

                if (NewSubtaskDialog.this.name.getText().length() > 0) {
                    onItemEntered(name.getText().toString(), description.getText().toString(), taskDate, taskTime);
                    dismiss();
                }
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.dialog_message_cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onItemCanceled();
            }
        });
        this.date.setOnTouchListener(new GestureDetectorTouchListener(context) {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                showDatePicker();
                return super.onSingleTapUp(e);
            }
        });

        this.time.setOnTouchListener(new GestureDetectorTouchListener(context) {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                showTimePicker();
                return super.onSingleTapUp(e);
            }
        });
        builder.setView(layout);
    }


    private void showDatePicker() {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                        NewSubtaskDialog.this.year = year;
                        NewSubtaskDialog.this.month = monthOfYear;
                        NewSubtaskDialog.this.day = dayOfMonth;
                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(2016, 1, 1)
                .setDoneText("-OK-")
                //TODO data atual
                .setCancelText("-Cancel-");
        if (this.year != 0 && this.month != 0 && this.day != 0) {
            cdp.setPreselectedDate(year, month, day);
        }
        cdp.show(fragmentManager, FRAG_TAG_DATE_PICKER);
    }

    private void showTimePicker() {

        RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                        NewSubtaskDialog.this.hour = hourOfDay;
                        NewSubtaskDialog.this.minute = minute;
                    }
                })
                .setStartTime(hour==-1 ? 12 : hour,minute ==-1 ? 0 : minute)
                .setDoneText("-Yay-")
                .setCancelText("-Nop-");
        rtpd.show(fragmentManager, FRAG_TAG_TIME_PICKER);
    }

    //TODO set date
    public NewSubtaskDialog(Context context, Subtask subtask) {
        this(context);
        this.name.setText(subtask.getName());
        this.description.setText(subtask.getDescription());
        //set date
        //TODO por isso em um método
        Calendar calendar = Calendar.getInstance();
        if (subtask.getTaskDate() != null) {
            calendar.setTime(subtask.getTaskDate());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        if (subtask.getTaskTime() != null) {
            calendar.setTime(subtask.getTaskTime());

            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
        }

        //TODO por isso em um método
        if (this.year != 0 && this.month != 0 && this.day != 0) {
            date.setText(day + "/" + month + "/" + year);
        }

        if (hour >= 0 && minute >= 0) {
            time.setText(hour + ":" + minute);
        }

    }

    public abstract void onItemEntered(String name, String description, Date taskDate, Date taskTime);

    public void onItemCanceled() {

    }

    @Override
    public void show() {
        builder.show();

    }
}
