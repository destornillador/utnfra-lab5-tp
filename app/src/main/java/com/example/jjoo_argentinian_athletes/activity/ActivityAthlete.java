package com.example.jjoo_argentinian_athletes.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.controller.AthleteController;
import com.example.jjoo_argentinian_athletes.model.AthleteModel;
import com.example.jjoo_argentinian_athletes.util.ApiHelperAthlete;

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
        Bundle intent_extras = intent_activity_athlete.getExtras();
        try {
            final AthleteModel athlete = ApiHelperAthlete.getAthleteFromJson(new JSONObject(intent_extras.getString("ITEM_ATHLETE")));

            // Set Name to Toolbar
            toolbar.setTitle(this.getResources().getString(R.string.title_activity_athlete).toUpperCase().concat(" | ").concat(athlete.getFullName()));



            // FIXME: Esto debería ir en la Vista (MVC)
            ImageView athleteProfilePhoto = findViewById(R.id.athlete_profile_photo);
            ImageView atheteSocialNetworkInstagram = findViewById(R.id.athlete_social_network_instagram);
            ImageView athleteSocialNetworkTwitter = findViewById(R.id.athlete_social_network_twitter);
            TextView athleteFullName =  findViewById(R.id.athlete_fullname);
            TextView athleteSport = findViewById(R.id.tr_athlete_sport_tv);
            TextView athleteSportEvents = findViewById(R.id.tr_athlete_sport_events_tv);
            TextView athleteOlympicGamesAttend = findViewById(R.id.tr_athlete_olympic_games_attent_tv);

            // TODO: Probably this could be simplified in reusable code
            if ( ! athlete.hasThisSocialNetwork("instagram")) {
                atheteSocialNetworkInstagram.setVisibility(View.GONE);
            }

            if ( ! athlete.hasThisSocialNetwork("twitter")) {
                athleteSocialNetworkTwitter.setVisibility((View.GONE));
            }
            atheteSocialNetworkInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(athlete.getSocialNetworks().get("instagram")));
                    startActivity(intent);
                }
            });

            athleteSocialNetworkTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(athlete.getSocialNetworks().get("twitter")));
                    startActivity(intent);
                }
            });

            athleteProfilePhoto.setImageBitmap(BitmapFactory.decodeFile(athlete.getProfilePhotoLocalFilePath()));
            athleteFullName.setText(athlete.getFullName());
            athleteSport.setText(athlete.getSport());
            athleteSportEvents.setText(athlete.getSportEventsToString());
            athleteOlympicGamesAttend.setText(athlete.getOlympicGamesAttendToString());


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