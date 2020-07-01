package com.apps.esampaio.legacy.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.esampaio.legacy.actions.NotificationScheduler;

import br.liveo.model.HelpLiveo;

public class MainActivity extends AppCompatActivity {


    private HelpLiveo mHelpLiveo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NotificationScheduler(this).schedule();
    }




}
