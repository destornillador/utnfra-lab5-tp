package com.example.jjoo_argentinian_athletes.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.jjoo_argentinian_athletes.model.AthleteModel;
import com.example.jjoo_argentinian_athletes.model.SportModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HttpThread extends Thread {
    String url;
    EHttpManagerValidMethod httpMethod;
    Handler handler;
    EHttpThreadReason reason;
    Integer position;
    Boolean cacheOnDisk = false;
    Context context = null;
    String sport;

    public HttpThread(String url, EHttpManagerValidMethod httpMethod, Handler handler, EHttpThreadReason reason) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.handler = handler;
        this.reason = reason;
    }

    public HttpThread(String url, EHttpManagerValidMethod httpMethod, Handler handler, EHttpThreadReason reason,
                      String sport) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.handler = handler;
        this.reason = reason;
        this.sport = sport;
    }

    public HttpThread(String url, EHttpManagerValidMethod httpMethod, Handler handler, EHttpThreadReason reason,
                      Integer position, Boolean cacheOnDisk, Context context) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.handler = handler;
        this.reason = reason;
        this.position = position;
        this.cacheOnDisk = cacheOnDisk;
        this.context = context;
    }

    @Override
    public void run() {
        // FIXME: Ver de meter esta variable dentro de los if de abajo
        // Si llamo el thread de sport, no tiene sentido declarar un array de Athletes
        List<AthleteModel> athleteList = new ArrayList<>();
        List<SportModel> sportList = new ArrayList<>();

        HttpManager hm = new HttpManager();

        Message msg = new Message();

        byte[] res = hm.request(this);

        if (res == null) {
            // Handle Internet connection errors
            msg.obj = athleteList;
            handler.sendMessage(msg);
            return;
        }

        switch (reason) {
            case POPULATE_MODEL_ATHLETE:
                try {
                    JSONArray athletesJson = new JSONArray(new String(res));
                    for (int i = 0; i < athletesJson.length(); i++) {
                        athleteList.add(ApiHelperAthlete.getAthleteFromJson(athletesJson.getJSONObject(i)));
                    }
                    msg.obj = athleteList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case POPULATE_MODEL_SPORT:
                try {
                    JSONArray sportJson = new JSONArray(new String(res));
                    for (int i = 0; i < sportJson.length(); i++) {
                        sportList.add(ApiHelperSport.getSportFromJson(sportJson.getJSONObject(i)));
                    }
                    msg.obj = sportList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case POPULATE_ATHLETE_MODEL_BY_SPORT:
                try {
                    JSONArray athletesJson = new JSONArray(new String(res));
                    for (int i = 0; i < athletesJson.length(); i++) {
                        AthleteModel athlete = ApiHelperAthlete.getAthleteFromJson(athletesJson.getJSONObject(i));
                        if (!athlete.getSport().toLowerCase().contentEquals(sport.toLowerCase())) {
                            continue;
                        }
                        athleteList.add(athlete);
                    }
                    msg.obj = athleteList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case POPULATE_IMAGE:
                msg.arg1 = position;
                msg.obj = res;
                break;
        }

        handler.sendMessage(msg);
    }
}