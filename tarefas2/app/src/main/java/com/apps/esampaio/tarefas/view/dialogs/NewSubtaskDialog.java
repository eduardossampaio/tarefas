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
import android.widget.ImageView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.core.entities.DateTime;
import com.apps.esampaio.tarefas.core.entities.Subtask;
import com.apps.esampaio.tarefas.view.listeners.GestureDetectorTouchListener;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;

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
    private ImageView clearDate;
    private ImageView clearTime;
    private ImageView timeIcon;

    private EditText name;
    private EditText description;
    private EditText dateText;
    private EditText timeText;
    private View layout;

    private DateTime dateTime;

    private AlertDialog.Builder builder;

    public NewSubtaskDialog(final Context context) {
        super(context);
        this.context = context;
        this.fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

        layout = LayoutInflater.from(context).inflate(R.layout.dialog_new_subtask, null);

        this.name = (EditText) layout.findViewById(R.id.dialog_new_subtask_name);
        this.description = (EditText) layout.findViewById(R.id.dialog_new_subtask_description);
        this.dateText = (EditText) layout.findViewById(R.id.dialog_new_subtask_date);
        this.timeText = (EditText) layout.findViewById(R.id.dialog_new_subtask_time);
        this.clearDate = (ImageView) layout.findViewById(R.id.clear_date);
        this.clearTime = (ImageView) layout.findViewById(R.id.clear_time);
        this.timeIcon = (ImageView) layout.findViewById(R.id.ic_hour);

        this.clearDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidateDate();
            }
        });
        this.clearTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidateTime();
            }
        });

        this.dateTime = new DateTime();
        builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.dialog_new_subtask_title));
        builder.setPositiveButton(context.getResources().getString(R.string.dialog_message_ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (NewSubtaskDialog.this.name.getText().length() > 0) {
                    onItemEntered(name.getText().toString(), description.getText().toString(), dateTime.getDate(), dateTime.getTime());
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
                if(dateTime.dateSetted()) {
                    showTimePicker();
                    return super.onSingleTapUp(e);
                }
                return true;
            }
        });
        builder.setView(layout);
        builder.setCancelable(false);
        disableTime();
    }


    private void showDatePicker() {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        dateTime.setDate(year, monthOfYear, dayOfMonth);
                        displayDate();
                        enableTime();
                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setDoneText(context.getString(R.string.dialog_message_ok))
                .setCancelText(context.getString(R.string.dialog_message_cancel));

        DateTime timeToSet;
        if (!this.dateTime.dateSetted()) {
            timeToSet = DateTime.getCurrentDateTime();
        } else {
            timeToSet = this.dateTime;
        }
        int y = timeToSet.getYear();
        int m = timeToSet.getMonth();
        int d = timeToSet.getDay();
        cdp.setPreselectedDate(y, m, d);

        cdp.show(fragmentManager, FRAG_TAG_DATE_PICKER);
    }

    private void showTimePicker() {

        RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                        dateTime.setTime(hourOfDay,minute);
                        displayTime();
                    }
                })
                .setDoneText(context.getString(R.string.dialog_message_ok))
                .setCancelText(context.getString(R.string.dialog_message_cancel));

        if(dateTime.timeSetted())
            rtpd.setStartTime(dateTime.getHour(),dateTime.getMinute());

        rtpd.show(fragmentManager, FRAG_TAG_TIME_PICKER);
    }

    public NewSubtaskDialog(Context context, Subtask subtask) {
        this(context);
        this.name.setText(subtask.getName());
        this.description.setText(subtask.getDescription());
        this.dateTime = new DateTime(subtask.getTaskDate(),subtask.getTaskTime());

        displayDate();
        displayTime();
        if(subtask.getTaskDate()!=null){
            enableTime();
        }
    }

    public abstract void onItemEntered(String name, String description, Date taskDate, Date taskTime);

    public void onItemCanceled() {

    }

    @Override
    public void show() {
        builder.show();
    }

    @Override
    public void setTitle(CharSequence title) {
        builder.setTitle(title);
    }

    private void displayDate() {
        if (this.dateTime.dateSetted()) {
            dateText.setText(this.dateTime.formatDate());
        } else {
            dateText.setText(context.getString(R.string.dialog_new_subtask_select_date));
        }

    }

    private void displayTime() {
        if (this.dateTime.timeSetted()) {
            timeText.setText(this.dateTime.formatTime());
        } else {
            timeText.setText(context.getString(R.string.dialog_new_subtask_select_time));
        }
    }

    private void invalidateDate() {
        this.dateTime.invalidateDate();
        displayDate();
        invalidateTime();
        disableTime();

    }
    private void invalidateTime(){
        this.dateTime.invalidateTime();
        displayTime();
    }
    private void enableTime(){
        this.timeText.setVisibility(View.VISIBLE);
        this.clearTime.setVisibility(View.VISIBLE);
        this.timeIcon.setVisibility(View.VISIBLE);

    }
    private void disableTime(){
        this.timeText.setVisibility(View.GONE);
        this.clearTime.setVisibility(View.GONE);
        this.timeIcon.setVisibility(View.GONE);
    }
}
