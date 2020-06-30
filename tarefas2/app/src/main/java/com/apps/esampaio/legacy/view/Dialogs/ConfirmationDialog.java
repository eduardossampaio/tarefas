package com.apps.esampaio.legacy.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;

import com.apps.esampaio.legacy.R;

/**
 * Created by eduardo on 28/06/2016.
 */

public class ConfirmationDialog extends MessageDialog implements DialogInterface.OnClickListener{


    public ConfirmationDialog(Context context,String message){
        super(context,context.getString(R.string.app_name),message);
    }

    @Override
    protected void createDialog() {
        super.createDialog();
        builder.setPositiveButton(context.getString(R.string.dialog_message_ok),this);
        builder.setNegativeButton(context.getString(R.string.dialog_message_cancel),null);

    }

    public void onClick(DialogInterface dialog, int which){
    }
}
