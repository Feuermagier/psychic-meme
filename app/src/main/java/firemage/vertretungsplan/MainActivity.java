package firemage.vertretungsplan;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<ChangeContainer> allChangesToday = new ArrayList<>();
    private static ArrayList<ChangeContainer> allChangesTomorrow = new ArrayList<>();

    private ListView changesView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_today:
                    changesView.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, allChangesToday));
                    return true;
                case R.id.navigation_tomorrow:
                    changesView.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, allChangesTomorrow));
                    return true;
            }
            return false;
        }

    };





    public static void addNewSyncResultForToday(ArrayList<ChangeContainer> newEntrys) {
        boolean unknownEntries = false;
        for(ChangeContainer container : newEntrys) {
            if(!allChangesToday.contains(container))
                unknownEntries = true;
        }
        if(unknownEntries) {
            //TODO: Add Code for information
        }
        allChangesToday.clear();
        allChangesToday.addAll(newEntrys);
    }

    public static void addNewSyncResultForTomorrow(ArrayList<ChangeContainer> newEntrys) {
        boolean unknownEntries = false;
        for(ChangeContainer container : newEntrys) {
            if(!allChangesTomorrow.contains(container))
                unknownEntries = true;
        }
        if(unknownEntries) {
            //TODO: Add Code for information
        }
        allChangesTomorrow.clear();
        allChangesTomorrow.addAll(newEntrys);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.getMenu().findItem(R.id.navigation_today).setTitle(getResources().getString(R.string.title_today, DateFormat.getDateInstance().format(new Date())));
        navigation.getMenu().findItem(R.id.navigation_tomorrow).setTitle(getResources().getString(R.string.title_tomorrow, DateFormat.getDateInstance().format(new Date())));

        changesView = (ListView)findViewById(R.id.changes_list);
        changesView.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, allChangesToday));
        changesView.setEmptyView(findViewById(R.id.empty));

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        JobInfo info = new JobInfo.Builder(1234, new ComponentName(this, NetworkingService.class))
                .setPeriodic(60000 * Long.parseLong(PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.pref_sync_frequency_key), "")))
                .build();
        ((JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE)).schedule(info);
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
}
