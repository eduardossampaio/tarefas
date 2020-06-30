package com.apps.esampaio.tarefas.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Subtask;

import org.w3c.dom.Text;

public class DetailSubtask extends AppCompatActivity {
    private Subtask subtask;
    private TextView title;
    private TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_subtask);

        subtask= (Subtask) getIntent().getExtras().get("subtask");

//        title= (TextView) findViewById(R.id.activity_detail_subtask_title);
//        description= (TextView) findViewById(R.id.activity_detail_subtask_description);

        title.setText(subtask.getName());
        description.setText(subtask.getDescription());
    }
}
