package com.capam.service;

import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;

import com.capam.common.AppConfig;

/**
 * Created by acer on 7/11/13.
 */
public class LectureService extends IntentService {
    private static final String LOG = LectureService.class.getName();

    public LectureService() {
        super("Configuration Loader Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

      /*  String url = AppConfig.BASE_URI+ intent.getExtras().getString("url_suffix");
        HttpCaller caller = new HttpCaller();
        JSONObject object = caller.request(url);

        String action = intent.getExtras().getString("action");

        ProcessorFactory.createProcessor(getApplicationContext(), action)
                .parse(object);*/

    }
}
