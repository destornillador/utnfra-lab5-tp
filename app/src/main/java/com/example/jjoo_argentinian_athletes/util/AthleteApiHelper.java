package com.example.jjoo_argentinian_athletes.util;

import com.example.jjoo_argentinian_athletes.model.Athlete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AthleteApiHelper {

    /**
     * Generate an ArrayList<Athlete> from a JSON Array object
     *
     * @return ArrayList<Athlete>
     */
    /*
    public static List<Athlete> generateListPersonFromJson(JSONArray jsonArray) {
        List<Athlete> athleteList = new ArrayList<Athlete>();

        FIXME: https://www.androidhive.info/2012/01/android-json-parsing-tutorial/
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                athleteList.add(new Athlete(
                        obj.getString("fullname"),
                        obj.getString("profilePhotoUrl"),
                        obj.getJSONArray("socialNetworks"),
                        obj.getString("sport"),
                        obj.getString("sportEvents"),
                        obj.getString("olympicGamesAttend")
                ));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }

    return athleteList;
    }
     */

    /*
    public static Map<String,String> jsonArrayToMapString(JSONArray jsonArray) {
        for (obj
    }
     */
}