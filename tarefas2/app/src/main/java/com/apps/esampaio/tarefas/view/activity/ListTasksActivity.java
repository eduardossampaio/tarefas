package com.apps.esampaio.tarefas.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apps.esampaio.tarefas.BuildConfig;
import com.apps.esampaio.tarefas.Constants;
import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.Tasks;
import com.apps.esampaio.tarefas.actions.NotificationScheduler;
import com.apps.esampaio.tarefas.entities.Task;
import com.apps.esampaio.tarefas.view.dialogs.ConfirmationDialog;
import com.apps.esampaio.tarefas.view.dialogs.MessageDialog;
import com.apps.esampaio.tarefas.view.dialogs.NewTaskDialog;
import com.apps.esampaio.tarefas.view.dialogs.OptionsDialog;
import com.apps.esampaio.tarefas.view.activity.adapter.ListTaskAdapter;
import com.apps.esampaio.tarefas.view.fragment.CompletedTasksFragment;
import com.apps.esampaio.tarefas.view.fragment.ListTasksFragment;
import com.apps.esampaio.tarefas.view.fragment.TodayTasksFragment;

import java.util.List;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.model.HelpLiveo;
import br.liveo.model.Navigation;
import br.liveo.navigationliveo.NavigationLiveo;

public class ListTasksActivity   extends NavigationLiveo implements OnItemClickListener{


    private HelpLiveo mHelpLiveo;

    //fragments
    private Fragment allTasksFragment;
    private Fragment todaysTasksFragment;
    private Fragment completedTasksFragment;


    @Override
    public void onInt(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        new NotificationScheduler(this).schedule();

        mHelpLiveo = new HelpLiveo();

        mHelpLiveo.add("=Todas as tarefas=");
        mHelpLiveo.add("=tarefas de hoje=");
        mHelpLiveo.add("=tarefas concluídas=");
        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.add("=configurações=");
        mHelpLiveo.add("=avalie=");

        with(this, Navigation.THEME_LIGHT) // default theme is dark
                .addAllHelpItem(mHelpLiveo.getHelp())
                .removeHeader()
                .startingPosition(0)
                .build();

        new NotificationScheduler(this).schedule();
        getToolbar().setTitleTextColor(Color.WHITE);


        allTasksFragment = new ListTasksFragment();
        todaysTasksFragment = new TodayTasksFragment();
        completedTasksFragment = new CompletedTasksFragment();

        showVersionNotes();
        changeDrawerLayoutWitdh();
    }

    private void changeDrawerLayoutWitdh(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int newWidth=(int)(width* 0.7);
        RelativeLayout drawerLayout = (RelativeLayout) findViewById(R.id.containerList);
        if(drawerLayout!=null)
            drawerLayout.getLayoutParams().width = newWidth;
    }


    @Override
    public void onItemClick(int position) {
        if(position==0){
            setContentFragment(allTasksFragment);
        }else if(position ==1){
            setContentFragment(todaysTasksFragment);
        }else if(position ==2){
            setContentFragment(completedTasksFragment);
        }else if(position ==3){

        }else if(position ==4){

        }
    }

    private void setContentFragment(Fragment fragment){
        FragmentManager mFragmentManager = getSupportFragmentManager();

        if (fragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    private void showVersionNotes() {
        String versionName = BuildConfig.VERSION_NAME;

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE_VERSION_NOTES_KEY, 0);
        boolean displayed = sharedPreferences.getBoolean(versionName,false);
        if ( ! displayed){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(versionName,true);
            Dialog dialog = new MessageDialog(this,R.string.dialog_release_notes_title,R.string.change_notes);
            dialog.show();
            editor.apply();
        }
    }



}
