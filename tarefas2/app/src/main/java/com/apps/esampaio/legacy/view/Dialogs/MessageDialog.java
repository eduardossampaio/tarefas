package com.apps.esampaio.legacy.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;

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
