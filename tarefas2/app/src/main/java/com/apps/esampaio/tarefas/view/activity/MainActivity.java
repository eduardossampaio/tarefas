package com.apps.esampaio.tarefas.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.apps.esampaio.tarefas.R;
import com.apps.esampaio.tarefas.actions.NotificationScheduler;
import com.apps.esampaio.tarefas.view.fragment.ListTasksFragment;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.model.HelpLiveo;
import br.liveo.model.Navigation;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements OnItemClickListener{


    private HelpLiveo mHelpLiveo;


    @Override
    public void onInt(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new NotificationScheduler(this).schedule();

        mHelpLiveo = new HelpLiveo();

        mHelpLiveo.add("inbox");
        mHelpLiveo.addSubHeader("categories"); //Item subHeader
        mHelpLiveo.add("starred");
        mHelpLiveo.add("sent_mail");
        mHelpLiveo.add("R.string.drafts");
        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.add("trash");
        mHelpLiveo.add("spam");

        with(this, Navigation.THEME_LIGHT) // default theme is dark
                .addAllHelpItem(mHelpLiveo.getHelp())
                .removeHeader()
                .build();

        new NotificationScheduler(this).schedule();
    }




    @Override
    public void onItemClick(int position) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        Fragment mFragment = new ListTasksFragment();
        mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
    }
}
