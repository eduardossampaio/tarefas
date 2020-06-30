package com.apps.esampaio.legacy.core.entities;

import java.io.File;

/**
 * Created by eduardo on 15/08/2016.
 */

public class BackupItem {
    private File file;
    private Task task;
    private DateTime backupDate;

    public BackupItem(File file, Task task, DateTime backupDate) {
        this.file = file;
        this.task = task;
        this.backupDate = backupDate;
    }


    public boolean equalsName(BackupItem item) {
        return item.getTask().getName().equals(getTask().getName());
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public DateTime getBackupDate() {
        return backupDate;
    }

    public void setBackupDate(DateTime backupDate) {
        this.backupDate = backupDate;
    }
}
