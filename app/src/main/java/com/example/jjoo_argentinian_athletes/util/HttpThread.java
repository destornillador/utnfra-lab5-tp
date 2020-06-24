package com.example.jjoo_argentinian_athletes.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.jjoo_argentinian_athletes.model.Athlete;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class HttpThread extends Thread {
    String url;
    EHttpManagerValidMethod httpMethod;
    Handler handler;
    EHttpThreadReason reason;
    Integer position;
    Boolean cacheOnDisk;
    Context context;

    public HttpThread(String url, EHttpManagerValidMethod httpMethod, Handler handler, EHttpThreadReason reason) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.handler = handler;
        this.reason = reason;
        this.cacheOnDisk = false;
        this.context = null;
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
        HttpManager hm = new HttpManager();
        byte[] res;

        Message msg = new Message();

        switch (reason) {
            case POPULATE_MODEL:
                try {
                    //res = hm.request(url, httpMethod, cacheOnDisk);
                    res = hm.request(this);
                    List<Athlete> athleteList = AthleteApiHelper.generateListAthleteFromJson(new JSONArray(new String(res)));
                    msg.obj = athleteList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case POPULATE_PHOTO:
                //res = hm.request(url, httpMethod, cacheOnDisk, context);
                res = hm.request(this);
                msg.arg1 = position;
                msg.obj = res;
                break;
        }

        this.handler.sendMessage(msg);
    }
}