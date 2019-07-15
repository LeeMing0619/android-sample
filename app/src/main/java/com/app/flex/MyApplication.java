package com.app.flex;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class MyApplication extends Application
{

    public static String profileURI="";
    public static String profileDisplayURI="";
    @Override
    public void onCreate() {
        super.onCreate();

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(new TwitterAuthConfig("HqbZnuZBoGHR4B0kegVVBSMe1",
                        "dFDrCGtOm24nspRkje4yzjB9q4W8IMwDIc0KHfVEHM7bSGS1Bf"))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
