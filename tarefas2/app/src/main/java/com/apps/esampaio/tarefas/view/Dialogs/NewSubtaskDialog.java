package com.apps.esampaio.tarefas.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Subtask;

import java.util.Date;

/**
 * Created by eduardo on 28/06/2016.
 */

public abstract class NewSubtaskDialog extends AppCompatDialog {


    private Button createButton;
    private Button cancelButton;

    private EditText name;
    private EditText description;
    private EditText date;
    private EditText time;

    private AlertDialog.Builder builder;

    public NewSubtaskDialog(final Context context){
        super(context);

        View layout = (View) LayoutInflater.from(context).inflate(R.layout.dialog_new_subtask,null);

        this.name        = (EditText) layout.findViewById(R.id.dialog_new_subtask_name);
        this.description = (EditText) layout.findViewById(R.id.dialog_new_subtask_description);
        this.date        = (EditText) layout.findViewById(R.id.dialog_new_subtask_date);
        this.time        = (EditText) layout.findViewById(R.id.dialog_new_subtask_time);

        builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.dialog_new_subtask_title));
        builder.setPositiveButton(context.getResources().getString(R.string.dialog_message_ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( NewSubtaskDialog.this.name.getText().length() >0 ) {
                    onItemEntered(name.getText().toString(),description.getText().toString(),null);
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

        builder.setView(layout);
        this.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public NewSubtaskDialog(Context context, Subtask subtask){
        this(context);
        this.name.setText(subtask.getName());
        this.description.setText(subtask.getDescription());
    }

    public  abstract void onItemEntered(String name, String description, Date date);

    public void onItemCanceled(){

    }

    @Override
    public void show() {
        builder.show();

    }
}
