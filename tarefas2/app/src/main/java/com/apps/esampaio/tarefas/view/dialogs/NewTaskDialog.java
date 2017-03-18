package com.apps.esampaio.tarefas.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.core.entities.Task;


public abstract class NewTaskDialog extends Dialog{
    private Button createButton;
    private Button cancelButton;
    private EditText title;
    protected AlertDialog.Builder builder;

    public NewTaskDialog(Context context){
        this(context,null);
    }

    public NewTaskDialog(Context context, Task task){


        super(context);

        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_new_task,null);
        this.title  = (EditText) layout.findViewById(R.id.new_task_task_name);

        if(task != null){
            this.title.setText(task.getName());
        }

        builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.new_task_title));

        builder.setPositiveButton(context.getString(R.string.dialog_message_ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( NewTaskDialog.this.title.getText().length() >0 ) {
                    onItemEntered(title.getText().toString());
                    dismiss();
                }
            }
        });
        builder.setNegativeButton(context.getString(R.string.dialog_message_cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(layout);
    }

    public  abstract void onItemEntered(String taskName);

    @Override
    public void show() {
        builder.show();
    }
}
