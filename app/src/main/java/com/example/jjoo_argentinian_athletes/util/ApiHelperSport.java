package com.example.jjoo_argentinian_athletes.util;

import com.example.jjoo_argentinian_athletes.model.SportModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiHelperSport {

    public static SportModel getSportFromJson(JSONObject sport) {
        String name;
        String logoUrl;
        String logoLocalFilePath = null;

        try {
            name = sport.getString("name");
            logoUrl = sport.getString("logoUrl");

            if (sport.has("logoLocalFilePath")) {
                logoLocalFilePath = sport.getString("logoLocalFilePath");
            }

            return new SportModel(
                    name,
                    logoUrl,
                    logoLocalFilePath);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return SportModel.EMPTY;
    }
}