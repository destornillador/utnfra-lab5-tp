package com.example.jjoo_argentinian_athletes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.controller.AthleteController;
import com.example.jjoo_argentinian_athletes.util.ApiHelperAthlete;
import com.example.jjoo_argentinian_athletes.view.AthleteView;

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
        AthleteView athleteView = new AthleteView(this);

        Intent intent_activity_athlete = getIntent();
        Bundle intent_extras = intent_activity_athlete.getExtras();
        try {
            AthleteController athleteController = new AthleteController(ApiHelperAthlete.getAthleteFromJson(new JSONObject(intent_extras.getString("ITEM_ATHLETE"))));
            athleteController.setView(athleteView);
            athleteController.populateView();

            // Set Name to Toolbar
            toolbar.setTitle(this.getResources().getString(R.string.title_activity_athlete).toUpperCase().concat(" | ").concat(athleteController.getAthlete().getFullName()));

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