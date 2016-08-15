package com.apps.esampaio.tarefas.core;

import android.content.Context;
import android.os.Environment;
import android.util.JsonWriter;

import com.apps.esampaio.tarefas.core.entities.BackupItem;
import com.apps.esampaio.tarefas.core.entities.Subtask;
import com.apps.esampaio.tarefas.core.entities.Task;
import com.apps.esampaio.tarefas.core.entities.persistence.DAO.Impl.TaskDAOImpl;
import com.apps.esampaio.tarefas.core.entities.persistence.DAO.TaskDAO;
import com.apps.esampaio.tarefas.core.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
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
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonTask = gson.toJson(task);
        if(jsonTask !=null){
            String fileName = StringUtils.append(false,Constants.BASE_SAVE_DIR+"/"+task.getName(),".json");
            File baseDir = new File(fileName);
            //TODO verificar se já tem backup salvo
            Files.saveToFile(baseDir,jsonTask);
        }
    }

    public List<BackupItem> getBackupedTasks() throws Exception {
        List<BackupItem> items = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        File[] savedTasksFiles = Files.readDir(new File(Constants.BASE_SAVE_DIR));
        for (File file:savedTasksFiles) {
            String contentFile = Files.readFile(file);
            Task task = gson.fromJson(contentFile,Task.class);
            items.add(new BackupItem(file,task,null));
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
