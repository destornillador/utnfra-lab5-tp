package com.example.jjoo_argentinian_athletes.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.jjoo_argentinian_athletes.model.Athlete;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AthleteApiHelper {

    /**
     * Generate an ArrayList<Athlete> from a JSON Array object
     *
     * @return ArrayList<Athlete>
     */

    public static List<Athlete> generateListAthleteFromJson(JSONArray jsonArray) {
        List<Athlete> athleteList = new ArrayList<Athlete>();

        FIXME: https://www.androidhive.info/2012/01/android-json-parsing-tutorial/
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                athleteList.add(new Athlete(
                        obj.getString("fullname"),
                        obj.getString("profilePhotoUrl"),
                        null,
                        obj.getString("sport"),
                        null,
                        null
                ));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }

    return athleteList;
    }

    /*
    public static Map<String,String> jsonArrayToMapString(JSONArray jsonArray) {
        for (obj
    }
     */
}
