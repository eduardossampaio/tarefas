package com.apps.esampaio.tarefas.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Subtask;

public class DetailSubtaskActivity extends AppCompatActivity {
    private Subtask subtask;
    private EditText nameText;
    private EditText descriptionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_subtask);

        subtask = ( Subtask) getIntent().getExtras().getSerializable("subtask");

        nameText = (EditText)findViewById(R.id.activity_detail_subtask_name_edittext);
        descriptionText = (EditText) findViewById(R.id.activity_detail_subtask_description_edittext);

        if ( subtask!= null){
            nameText.setText(subtask.getName());
            descriptionText.setText(subtask.getDescription());
        }

    }

}
