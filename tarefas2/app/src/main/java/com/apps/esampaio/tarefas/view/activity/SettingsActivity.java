package com.apps.esampaio.tarefas.view.activity;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

import com.apps.esampaio.tarefas.R;

public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private static void changePreferenceSummary(Preference preference,String value){
        if (preference instanceof ListPreference) {
            ListPreference listPref = (ListPreference) preference;
            int newIndex = listPref.findIndexOfValue(value);
            CharSequence newEntry = listPref.getEntries()[newIndex];
            preference.setSummary(newEntry);
        }
    }

    public static class MyPreferenceFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            findPreference("preference_notify_before").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    changePreferenceSummary(preference,newValue.toString());
                    return true;
                }
            });

            findPreference("preference_order_type").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    changePreferenceSummary(preference,newValue.toString());
                    return true;
                }
            });
        }

    }



}
