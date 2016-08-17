package com.apps.esampaio.tarefas.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Subtask;

/**
 * Created by eduardo on 28/06/2016.
 */

public abstract class NewSubtaskDialog extends AppCompatDialog {


    private Button createButton;
    private Button cancelButton;
    private TextView name;
    private TextView description;

    private AlertDialog.Builder builder;

    public NewSubtaskDialog(Context context){
        super(context);

        View layout = (View) LayoutInflater.from(context).inflate(R.layout.dialog_new_subtask,null);

        this.name        = (TextView) layout.findViewById(R.id.dialog_new_subtask_name);
        this.description        = (TextView) layout.findViewById(R.id.dialog_new_subtask_description);

        builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.dialog_new_subtask_title));
        builder.setPositiveButton(context.getResources().getString(R.string.dialog_message_ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( NewSubtaskDialog.this.name.getText().length() >0 ) {
                    onItemEntered(name.getText().toString(),description.getText().toString());
                    dismiss();
                }
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.dialog_message_cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(layout);

    }

    public NewSubtaskDialog(Context context, Subtask subtask){
        this(context);
        this.name.setText(subtask.getName());
        this.description.setText(subtask.getDescription());
    }

    public  abstract void onItemEntered(String name,String description);

    @Override
    public void show() {
        builder.show();

    }
}
