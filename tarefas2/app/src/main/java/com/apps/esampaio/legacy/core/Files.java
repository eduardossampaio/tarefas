package com.apps.esampaio.legacy.core;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by eduardo on 14/08/2016.
 */

public class Files {
    public static void saveToFile(File file,String s) throws IOException {
       try {
           File parent = file.getParentFile();
           if (!parent.exists()) {
               parent.mkdirs();
           }
           if(!file.exists())
               file.createNewFile();
           FileOutputStream os =  new FileOutputStream(file);
           os.write(s.getBytes());
           os.close();
       }catch (Exception e){
           e.printStackTrace();
           Log.e("tarefas","Erro ao salvar para arquivo",e);
           throw e;
       }
    }

    public static boolean equals(File file,String content){
        return false;
    }

    public static String readFile(File file) throws Exception{
        FileReader fileReader = new FileReader(file);
        char [] buffer= new char[512];
        StringBuilder result=new StringBuilder();
        int bytesRead=0;
        while((bytesRead = fileReader.read(buffer))!=-1){
            result.append(buffer,0,bytesRead);
        };
        fileReader.close();
        return result.toString();
    }
    public static File[] readDir(File directory){
        if(!directory.isDirectory())
            return null;
        return directory.listFiles();
    }
}
