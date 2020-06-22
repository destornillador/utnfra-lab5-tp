package com.example.jjoo_argentinian_athletes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jjoo_argentinian_athletes.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityAthlete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete);

        // Toolbar
        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);

        // Populate Activity
        Intent intent_activity_athlete = getIntent();
        Bundle extras_from_activity_athlete_preview = intent_activity_athlete.getExtras();
        Log.d("test",extras_from_activity_athlete_preview.getString("ITEM_ATHLETE"));
        try {
            JSONObject bundle_athlete = new JSONObject(extras_from_activity_athlete_preview.getString("ITEM_ATHLETE"));
            // FIXME: Esto debería ir en la Vista (MVC)
            TextView athleteFullName =  findViewById(R.id.athlete_fullname);
            TextView athleteSport = findViewById(R.id.tr_athlete_sport_tv);
            TextView athleteSportEvents = findViewById(R.id.tr_athlete_sport_events_tv);
            TextView athleteOlympicGamesAttend = findViewById(R.id.tr_athlete_olympic_games_attent_tv);

            athleteFullName.setText(bundle_athlete.getString("fullName"));
            athleteSport.setText(bundle_athlete.getString("sport"));

            // FIXME: Collection objects need to be iterated
            athleteSportEvents.setText(bundle_athlete.getString("sportEvents"));
            athleteOlympicGamesAttend.setText(bundle_athlete.getString("olympicGamesAttend"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Toolbar

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.finish();
                break;
        }
        return false;
    }

}