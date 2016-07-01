package com.apps.esampaio.tarefas.view.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.apps.esampaio.tarefas.R;

/**
 * Created by eduardo on 30/06/2016.
 */

public class MessageDialog extends Dialog {
    protected Context context;
    protected AlertDialog.Builder builder;
    protected String message;
    protected String title;

    public MessageDialog(Context context,String title,String message){
        super(context);
        this.context = context;
        this.message=message;
        this.title = title;
        createDialog();
     }
    public MessageDialog(Context context,int titleId,int messageId){
        this(context,context.getString(titleId),context.getString(messageId));

    }

    protected void  createDialog(){
        builder = new AlertDialog.Builder(context);
        builder.setMessage(this.message);
        builder.setTitle(this.title);

    }

    @Override
    public void show() {
        this.builder.show();
    }
}
