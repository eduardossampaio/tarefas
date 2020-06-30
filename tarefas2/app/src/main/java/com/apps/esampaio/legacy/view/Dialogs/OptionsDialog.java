package com.apps.esampaio.legacy.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by eduardo on 28/06/2016.
 */

public class OptionsDialog extends Dialog implements DialogInterface.OnClickListener{
    private String [] messages;
    private Context context;
    private AlertDialog.Builder builder;
    public OptionsDialog(Context context,int [] messageIds){
        super(context);
        this.context = context;

        messages = new String[messageIds.length];
        for (int i = 0; i < messageIds.length; i++) {
            int id = messageIds[i];
            messages[i] = context.getResources().getString(id);
        }
        createDialog();
    }

    public OptionsDialog(Context context,String [] messages){
        super(context);
        this.messages = messages;
        this.context = context;
        createDialog();
    }

    private void  createDialog(){
        builder = new AlertDialog.Builder(context);
        builder.setItems(messages, this);

    }

    @Override
    public void show() {
        this.builder.show();
    }

    public void onClick(DialogInterface dialog, int which){

    }



}
