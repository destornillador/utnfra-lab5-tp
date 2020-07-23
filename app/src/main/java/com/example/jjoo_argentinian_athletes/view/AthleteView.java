package com.example.jjoo_argentinian_athletes.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jjoo_argentinian_athletes.R;
import com.example.jjoo_argentinian_athletes.model.AthleteModel;

public class AthleteView {
    Activity activity;

    AthleteModel athleteModel;

    ImageView athleteProfilePhoto;
    ImageView atheteSocialNetworkInstagram;
    ImageView athleteSocialNetworkTwitter;
    TextView athleteFullName;
    TextView athleteSport;
    TextView athleteSportEvents;
    TextView athleteOlympicGamesAttend;

    public AthleteView(Activity activity) {
        this.activity = activity;
    }

    public void setAthleteModel(AthleteModel athlete) {
        athleteModel = athlete;
    }

    public void populateView() {
        initializeViewElements();
        if ( ! athleteModel.hasThisSocialNetwork("instagram")) {
            atheteSocialNetworkInstagram.setVisibility(View.GONE);
        }

        if ( ! athleteModel.hasThisSocialNetwork("twitter")) {
            athleteSocialNetworkTwitter.setVisibility((View.GONE));
        }
        atheteSocialNetworkInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(athleteModel.getSocialNetworks().get("instagram")));
                activity.startActivity(intent);
            }
        });

        athleteSocialNetworkTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(athleteModel.getSocialNetworks().get("twitter")));
                activity.startActivity(intent);
            }
        });

        athleteProfilePhoto.setImageBitmap(BitmapFactory.decodeFile(athleteModel.getProfilePhotoLocalFilePath()));
        athleteFullName.setText(athleteModel.getFullName());
        athleteSport.setText(athleteModel.getSport());
        athleteSportEvents.setText(athleteModel.getSportEventsToString());
        athleteOlympicGamesAttend.setText(athleteModel.getOlympicGamesAttendToString());
    }

    private void initializeViewElements() {
         athleteProfilePhoto = activity.findViewById(R.id.athlete_profile_photo);
         atheteSocialNetworkInstagram = activity.findViewById(R.id.athlete_social_network_instagram);
         athleteSocialNetworkTwitter = activity.findViewById(R.id.athlete_social_network_twitter);
         athleteFullName =  activity.findViewById(R.id.athlete_fullname);
         athleteSport = activity.findViewById(R.id.tr_athlete_sport_tv);
         athleteSportEvents = activity.findViewById(R.id.tr_athlete_sport_events_tv);
         athleteOlympicGamesAttend = activity.findViewById(R.id.tr_athlete_olympic_games_attent_tv);
    }






}
