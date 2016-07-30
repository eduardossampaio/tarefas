package com.apps.esampaio.tarefas.view.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.entities.Subtask;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;

import java.lang.reflect.Method;

public class SubtaskDialogActivity extends AppCompatActivity {


    private Button createButton;
    private Button cancelButton;

    private EditText name;
    private EditText description;
    private EditText date;
    private EditText time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_subtask);

        this.name        = (EditText) findViewById(R.id.dialog_new_subtask_name);
        this.description = (EditText) findViewById(R.id.dialog_new_subtask_description);
        this.date        = (EditText) findViewById(R.id.dialog_new_subtask_date);
        this.time        = (EditText) findViewById(R.id.dialog_new_subtask_time);

        cancelButton    = (Button) findViewById(R.id.dialog_button_1);
        createButton    = (Button) findViewById(R.id.dialog_button_2);

        cancelButton.setText("cancel");
        createButton.setText("create");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        this.date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    DatePickerBuilder dpb = new DatePickerBuilder()
                            .setFragmentManager(getSupportFragmentManager())
                            .setStyleResId(R.style.BetterPickersDialogFragment)
                            .setYear(2016)
                            .addDatePickerDialogHandler(new DatePickerDialogFragment.DatePickerDialogHandler(){
                                @Override
                                public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
                                    date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

                                }
                            })
                            .setYearOptional(true);

                    dpb.show();

                    return true;
                }
                return false;
            }
        });
        this.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });
        Subtask subtask = (Subtask) getIntent().getExtras().getSerializable("subtask");

        if(subtask != null){
            this.name.setText(subtask.getName());
            this.description.setText(subtask.getDescription());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_subtask_menu, menu);

        return true;
    }

}
