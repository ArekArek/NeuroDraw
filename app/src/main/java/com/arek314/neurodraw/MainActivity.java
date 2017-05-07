package com.arek314.neurodraw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    private TreeSet<Integer> toggleStates = new TreeSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    public void drawArmies(View view) {

        if (toggleStates.size() == 0) {
            Toast.makeText(this, R.string.no_player_checked, Toast.LENGTH_SHORT).show();
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> tmpStringSet = new HashSet<>();
        tmpStringSet.add("error");
        int armiesNumber = sharedPreferences.getStringSet("accessible_armies", tmpStringSet).size();

        if (toggleStates.size() > armiesNumber) {
            Toast.makeText(this, R.string.too_much_player_checked, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ShowDrawedArmiesActivity.class);
            savePlayersToPreferences();
            startActivity(intent);
        }
    }

    private void savePlayersToPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("players", getCheckedPlayers(toggleStates));
        editor.commit();
    }

    public void singlePlayerToogleOn(View view) {
        if (((Switch) view).isChecked())
            toggleStates.add((Integer) view.getTag());
        else
            toggleStates.remove(view.getTag());

    }

    private Set<String> getCheckedPlayers(TreeSet<Integer> checkedToggles) {
        Set<String> checkedPlayers = new HashSet<>();
        ListView tmpListView = (ListView) findViewById(R.id.start_players_list);
        ;
        TextView tmpTextView;

        for (int x : checkedToggles) {
            System.out.println(tmpListView.getChildAt(x));

            tmpTextView = (TextView) tmpListView.getChildAt(x).findViewById(R.id.single_player_name);
            if (tmpTextView != null)
                checkedPlayers.add(tmpTextView.getText().toString());
        }

        return checkedPlayers;
    }
}
