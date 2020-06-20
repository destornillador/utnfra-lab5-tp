package com.example.jjoo_argentinian_athletes.util;

import android.os.Handler;
import android.os.Message;

import com.example.jjoo_argentinian_athletes.model.Athlete;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

enum EHttpThreadReason {
    POPULATE_MODEL, POPULATE_PHOTO
}

public class HttpThread extends Thread {

    String url;
    String httpMethod;
    Handler handler;
    EHttpThreadReason reason;
    Integer position;

    public HttpThread(String url, String httpMethod, Handler handler, EHttpThreadReason reason) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.handler = handler;
        this.reason = reason;
    }

    public HttpThread(String url, String httpMethod, Handler handler, EHttpThreadReason reason, Integer position) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.handler = handler;
        this.reason = reason;
        this.position = position;
    }

    @Override
    public void run() {
        HttpManager hm = new HttpManager();
        byte[] res = hm.request(url, HttpManagerValidMethod.GET, null);

        Message msg = new Message();

        /*
        switch (reason) {
            case POPULATE_MODEL:
                try {
                    List<Athlete> pmList = AthleteApiHelper.generateListPersonFromJson(new JSONArray(new String(res)));
                    msg.obj = pmList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case POPULATE_PHOTO:
                msg.arg1 = position;
                msg.obj = res;
                break;
        }
        */
        this.handler.sendMessage(msg);
    }
}