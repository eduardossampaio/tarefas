package com.apps.esampaio.legacy.core;

import android.os.Environment;

/**
 * Created by eduardo on 30/06/2016.
 */

public interface Constants {
    //prefernce key to save versions notes
    String PREFERENCE_VERSION_NOTES_KEY = "VERSION_NOTES";

    String NOTIFICATION_RECEIVER_TAG = "EXECUTE_NOTIFICATION_TASK";

    String BASE_SAVE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/tarefas/backups";

    String BACKUP_DATE_FORMAT = "yyyyMMddHHmmss";

    String USE_TERMS_AGREED_KEY = "USE_TERMS_AGREED";

}
