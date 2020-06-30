package com.apps.esampaio.legacy.core;

import android.content.Context;

import com.apps.esampaio.legacy.core.backups.AnnotationExcludeExtrategy;
import com.apps.esampaio.legacy.core.entities.BackupItem;
import com.apps.esampaio.legacy.core.entities.DateTime;
import com.apps.esampaio.legacy.core.entities.Task;
import com.apps.esampaio.legacy.core.utils.DateUtils;
import com.apps.esampaio.legacy.core.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eduardo on 14/08/2016.
 */

public class Backup {
    private Context context;
    public Backup(Context context){
        this.context =context;
    }

    public void saveTask(Task task) throws Exception{
        GsonBuilder builder = new GsonBuilder().setExclusionStrategies(new AnnotationExcludeExtrategy());
        Gson gson = builder.create();
        String jsonTask = gson.toJson(task);
        if(jsonTask !=null){
            String dateFormated = DateUtils.formatDate(System.currentTimeMillis(),Constants.BACKUP_DATE_FORMAT);
            String fileName = StringUtils.append(false,Constants.BASE_SAVE_DIR+"/",task.getName(),"-",dateFormated,".json");
            File baseDir = new File(fileName);
            //TODO verificar se já tem backup salvo
            Files.saveToFile(baseDir,jsonTask);
        }
    }

    public List<BackupItem> getBackupedTasks() throws Exception {
        List<BackupItem> items = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder().setExclusionStrategies(new AnnotationExcludeExtrategy());
        Gson gson = builder.create();
        File[] savedTasksFiles = Files.readDir(new File(Constants.BASE_SAVE_DIR));
        for (File file:savedTasksFiles) {
            String contentFile = Files.readFile(file);
            int sepatarorIndex = file.getName().indexOf("-");
            int formatIndex = file.getName().indexOf(".json");
            String dateFormated = file.getName().substring(sepatarorIndex+1,formatIndex);
            Task task = gson.fromJson(contentFile,Task.class);
            Date date = DateUtils.toDate(Constants.BACKUP_DATE_FORMAT, dateFormated);
            items.add(new BackupItem(file,task,new DateTime(date,date)));
        }
        return items;
    }
    public void restore(BackupItem item){
        Task task = item.getTask();
        Tasks tasks = new Tasks(this.context);
        tasks.addTask(task);
    }
    public void delete(BackupItem item){
        item.getFile().delete();
    }
}
