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
import android.widget.TextView;

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

public abstract class DetailSubtaskDialog extends AppCompatDialog {

    private Context context;
    private FragmentManager fragmentManager;

    private TextView name;
    private TextView description;
    private TextView descriptionLabel;
    private TextView dateText;
    private TextView timeText;
    private View layout;

    private DateTime dateTime;
    private AlertDialog.Builder builder;

    public DetailSubtaskDialog(final Context context) {
        super(context);
        this.context = context;
        this.fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

        layout = LayoutInflater.from(context).inflate(R.layout.dialog_detail_subtask, null);

        this.name = (TextView) layout.findViewById(R.id.dialog_detail_subtask_name);
        this.description = (TextView) layout.findViewById(R.id.dialog_detail_subtask_description);
        this.descriptionLabel = (TextView) layout.findViewById(R.id.dialog_detail_subtask_description_label);

        this.dateText = (TextView) layout.findViewById(R.id.dialog_detail_subtask_date);
        this.timeText = (TextView) layout.findViewById(R.id.dialog_detail_subtask_time);

        this.dateTime = new DateTime();
        builder = new AlertDialog.Builder(context);

        builder.setPositiveButton(context.getResources().getString(R.string.dialog_message_ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (DetailSubtaskDialog.this.name.getText().length() > 0) {
                    onItemEntered(name.getText().toString(), description.getText().toString(), dateTime.getDate(), dateTime.getTime());
                    dismiss();
                }
            }
        });
        builder.setView(layout);
    }

    public DetailSubtaskDialog(Context context, Subtask subtask) {
        this(context);
        this.name.setText(subtask.getName());
        if(subtask.getDescription() != null && !subtask.getDescription().isEmpty()) {
            this.description.setText(subtask.getDescription());
        }else{
            this.descriptionLabel.setVisibility(View.GONE);
        }
        this.dateTime = new DateTime(subtask.getTaskDate(),subtask.getTaskTime());

        displayDate();
        displayTime();
    }

    public abstract void onItemEntered(String name, String description, Date taskDate, Date taskTime);

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
            dateText.setText(context.getString(R.string.dialog_detail_subtasks_no_date));
        }

    }

    private void displayTime() {
        if (this.dateTime.timeSetted()) {
            timeText.setText(this.dateTime.formatTime());
        } else {
            timeText.setText(context.getString(R.string.dialog_detail_subtasks_no_time));
        }
    }
}
