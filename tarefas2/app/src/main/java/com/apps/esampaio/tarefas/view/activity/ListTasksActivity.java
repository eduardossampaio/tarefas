package com.apps.esampaio.tarefas.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import com.apps.esampaio.tarefas.BuildConfig;
import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.actions.NotificationScheduler;
import com.apps.esampaio.tarefas.core.Constants;
import com.apps.esampaio.tarefas.core.Settings;
import com.apps.esampaio.tarefas.view.dialogs.MessageDialog;
import com.apps.esampaio.tarefas.view.fragment.BackedUpTaskFragment;
import com.apps.esampaio.tarefas.view.fragment.CompletedTasksFragment;
import com.apps.esampaio.tarefas.view.fragment.ListTasksFragment;
import com.apps.esampaio.tarefas.view.fragment.TodayTasksFragment;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.model.HelpLiveo;
import br.liveo.model.Navigation;
import br.liveo.navigationliveo.NavigationLiveo;

public class ListTasksActivity extends NavigationLiveo implements OnItemClickListener{


    private HelpLiveo mHelpLiveo;

    //fragments
    private Fragment allTasksFragment;
    private Fragment todaysTasksFragment;
    private Fragment completedTasksFragment;


    @Override
    public void onInt(Bundle savedInstanceState) {
        new NotificationScheduler(this).schedule();
        allTasksFragment = new ListTasksFragment();
        todaysTasksFragment = new TodayTasksFragment();
        completedTasksFragment = new CompletedTasksFragment();
        buildNavigationMenu();
        showVersionNotes();
        showUserLicense();

    }

    private void buildNavigationMenu(){
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add(getString(R.string.navigation_drawer_all_tasks));
        mHelpLiveo.add(getString(R.string.navigation_drawer_today_tasks));
        mHelpLiveo.add(getString(R.string.navigation_drawer_completed_tasks));
        if(Settings.getInstance(this).manualBackup()) {
            mHelpLiveo.add(getString(R.string.navigation_drawer_backed_up_tasks));
        }
        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.add(getString(R.string.navigation_drawer_settings));
        mHelpLiveo.add(getString(R.string.navigation_drawer_rate));

        with(this, Navigation.THEME_DARK)
                .addAllHelpItem(mHelpLiveo.getHelp())
                .removeHeader()
                .startingPosition(0)
                .build();
        changeDrawerLayoutWitdh();
    }

    private void changeDrawerLayoutWitdh(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int newWidth=(int)(width* 0.7);
        RelativeLayout drawerLayout = (RelativeLayout) findViewById(R.id.containerList);
        if(drawerLayout!=null)
            drawerLayout.getLayoutParams().width = newWidth;
    }


    @Override
    public void onItemClick(int position) {
        String itemName = mHelpLiveo.get(position).getName();
        if(itemName.equals(getString(R.string.navigation_drawer_all_tasks))){
            setContentFragment(allTasksFragment);
            changeSubtitle(R.string.all_task_fragment_title);
            //today tasks
        }else if(itemName.equals(getString(R.string.navigation_drawer_today_tasks))){
            setContentFragment(todaysTasksFragment);
            changeSubtitle(R.string.today_task_fragment_title);
            //completed tasks
        }else if(itemName.equals(getString(R.string.navigation_drawer_completed_tasks))) {
            setContentFragment(completedTasksFragment);
            changeSubtitle(R.string.completed_task_fragment_title);
        }else if(itemName.equals(getString(R.string.navigation_drawer_backed_up_tasks))){
            setContentFragment(new BackedUpTaskFragment());
            changeSubtitle(R.string.backup_task_fragment_title);
            //configuration
        }else if(itemName.equals(getString(R.string.navigation_drawer_settings))){
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            //rate
        }else if(itemName.equals(getString(R.string.navigation_drawer_rate))){
            openAppInStore();
        }
        //All tasks
    }
    private void changeSubtitle(int messageId){
        String message = getString(messageId);
        if( getSupportActionBar() != null)
            getSupportActionBar().setSubtitle(message);
    }
    private void openAppInStore(){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.esampaio.apps.tarefas"));
            startActivity(intent);
        }catch (Exception e){
            MessageDialog messageDialog = new MessageDialog(this,getString(R.string.dialog_message_error),getString(R.string.navigation_drawer_error_open_store));
            messageDialog.show();
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

    private void showUserLicense(){
        final SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE_VERSION_NOTES_KEY, 0);
        boolean agreed = sharedPreferences.getBoolean(Constants.USE_TERMS_AGREED_KEY,false);
        if(agreed)
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.license_title));
        builder.setMessage(getString(R.string.license_terms));
        builder.setPositiveButton(R.string.license_agree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Constants.USE_TERMS_AGREED_KEY,true);
                editor.apply();
            }
        });
        builder.setNegativeButton(R.string.license_disagree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        builder.setCancelable(false);
        builder.show();

    }

}
