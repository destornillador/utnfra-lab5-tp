package com.example.jjoo_argentinian_athletes.util;

import com.example.jjoo_argentinian_athletes.model.AthleteModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHelperAthlete {

    public static AthleteModel getAthleteFromJson(JSONObject athlete) {
        String fullName;
        String profilePhotoUrl;
        Map<String, String> socialNetworks = new HashMap<String, String>();
        String sport;
        List<String> sportEvents = new ArrayList<>();
        List<String> olympicGamesAttend = new ArrayList<>();
        String profilePhotoLocalFilePath = null;

        try {
            // Set all values that are not collection
            fullName = athlete.getString("fullName");
            profilePhotoUrl = athlete.getString("profilePhotoUrl");
            sport = athlete.getString("sport");

            if (athlete.has("profilePhotoLocalFilePath")) {
                profilePhotoLocalFilePath = athlete.getString("profilePhotoLocalFilePath");
            }

            // Get all JSON Object
            JSONObject socialNetworksJson = athlete.getJSONObject("socialNetworks");

            // Iterate each JSON Object
            if (! (socialNetworksJson.isNull("instagram") &&
                    socialNetworksJson.isNull("twitter"))) {
                for (int i = 0; i < socialNetworksJson.names().length(); i++) {
                    String key = socialNetworksJson.names().getString(i);
                    socialNetworks.put(key, socialNetworksJson.getString(key));
                }
            }

            // Get all JSON Array
            JSONArray sportEventsJson = athlete.getJSONArray("sportEvents");
            JSONArray olympicGamesAttendJson = athlete.getJSONArray("olympicGamesAttend");

            for (int j = 0; j < sportEventsJson.length(); j++) {
                sportEvents.add(sportEventsJson.getString(j));
            }

            for (int j = 0; j < olympicGamesAttendJson.length(); j++) {
                olympicGamesAttend.add(olympicGamesAttendJson.getString(j));
            }

            return new AthleteModel(
                    fullName,
                    profilePhotoUrl,
                    socialNetworks,
                    sport,
                    sportEvents,
                    olympicGamesAttend,
                    profilePhotoLocalFilePath);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return AthleteModel.EMPTY;
    }
}