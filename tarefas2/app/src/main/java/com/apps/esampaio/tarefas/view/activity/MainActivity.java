package com.apps.esampaio.tarefas.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.apps.esampaio.tarefas.actions.NotificationScheduler;

import br.liveo.model.HelpLiveo;

public class MainActivity extends AppCompatActivity {


    private HelpLiveo mHelpLiveo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NotificationScheduler(this).schedule();
    }




}
