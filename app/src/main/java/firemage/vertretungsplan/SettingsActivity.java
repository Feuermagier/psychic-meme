package firemage.vertretungsplan;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new View(this));
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.menu_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            findPreference(getString(R.string.pref_school_class_number_key)).setOnPreferenceChangeListener(this);
            onPreferenceChange(findPreference(getString(R.string.pref_school_class_number_key)), PreferenceManager.getDefaultSharedPreferences(getContext()).getString(findPreference(getString(R.string.pref_school_class_number_key)).getKey(), ""));
            findPreference(getString(R.string.pref_school_class_letter_key)).setOnPreferenceChangeListener(this);
            onPreferenceChange(findPreference(getString(R.string.pref_school_class_letter_key)), PreferenceManager.getDefaultSharedPreferences(getContext()).getString(findPreference(getString(R.string.pref_school_class_letter_key)).getKey(), ""));
            findPreference(getString(R.string.pref_sync_frequency_key)).setOnPreferenceChangeListener(this);
            onPreferenceChange(findPreference(getString(R.string.pref_sync_frequency_key)), PreferenceManager.getDefaultSharedPreferences(getContext()).getString(findPreference(getString(R.string.pref_sync_frequency_key)).getKey(), ""));

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            if(preference instanceof ListPreference) {
                ((ListPreference) preference).setValue(value.toString());
                preference.setSummary(((ListPreference) preference).getEntry());
            } else
                preference.setSummary(value.toString());

            return true;
        }
    }

}
