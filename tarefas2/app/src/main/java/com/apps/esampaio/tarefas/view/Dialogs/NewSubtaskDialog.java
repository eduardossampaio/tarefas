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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private EditText dateText;
    private EditText timeText;
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
        this.dateText = (EditText) layout.findViewById(R.id.dialog_new_subtask_date);
        this.timeText = (EditText) layout.findViewById(R.id.dialog_new_subtask_time);

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
        this.dateText.setOnTouchListener(new GestureDetectorTouchListener(context) {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                showDatePicker();
                return super.onSingleTapUp(e);
            }
        });

        this.timeText.setOnTouchListener(new GestureDetectorTouchListener(context) {
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
                        NewSubtaskDialog.this.year = year;
                        NewSubtaskDialog.this.month = monthOfYear;
                        NewSubtaskDialog.this.day = dayOfMonth;
                        displayDate(year,monthOfYear,dayOfMonth);
                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(year !=-1 ? year : 2016, month != -1 ? month : 1, day != -1 ? day : 1)
                .setDoneText("-OK-")
                //TODO data atual
                .setCancelText("-Cancel-");

        cdp.show(fragmentManager, FRAG_TAG_DATE_PICKER);
    }

    private void showTimePicker() {

        RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                        NewSubtaskDialog.this.hour = hourOfDay;
                        NewSubtaskDialog.this.minute = minute;
                        displayTime(hourOfDay,minute);
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

        displayDate(subtask.getTaskDate());
        displayTime(subtask.getTaskTime());

    }

    public abstract void onItemEntered(String name, String description, Date taskDate, Date taskTime);

    public void onItemCanceled() {

    }

    @Override
    public void show() {
        builder.show();
    }

    private void displayDate(int year,int month,int day){
        if (this.year != -1 && this.month != -1 && this.day != -1) {
            Calendar calendar =Calendar.getInstance();
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            displayDate(calendar.getTime());
        }else{
            displayDate(null);
        }
    }
    private void displayDate(Date date){
        if(date!=null){
            dateText.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(date));
        }else{
            dateText.setText("-SELECT DATE-");
        }
    }

    private void displayTime(int hour ,int minute){
        if(hour!=-1 && minute!=-1){
            Calendar calendar =Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);
            displayTime(calendar.getTime());
        }else{
            displayTime(null);
        }

    }
    private void displayTime(Date date){
        if(date!=null){
            timeText.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(date));
        }else{
            timeText.setText("-SELECT TIME-");
        }
    }

}
