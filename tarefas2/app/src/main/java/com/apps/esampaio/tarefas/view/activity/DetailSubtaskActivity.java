package com.apps.esampaio.tarefas.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Subtask;

import java.lang.reflect.Method;

public class DetailSubtaskActivity extends AppCompatActivity {
    private Subtask subtask;
    private EditText nameText;
    private EditText descriptionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_subtask);

//        subtask = ( Subtask) getIntent().getExtras().getSerializable("subtask");
//
//        nameText = (EditText)findViewById(R.id.activity_detail_subtask_name_edittext);
//        descriptionText = (EditText) findViewById(R.id.activity_detail_subtask_description_edittext);
//        if(nameText!= null) {
//            nameText.clearFocus();
//            descriptionText.clearFocus();
//        }
//
//        if ( subtask!= null){
//            nameText.setText(subtask.getName());
//            descriptionText.setText(subtask.getDescription());
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_subtask_menu, menu);

        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

}
