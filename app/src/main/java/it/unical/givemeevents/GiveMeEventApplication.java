package it.unical.givemeevents;

import android.app.Application;

import it.unical.givemeevents.network.FacebookGraphManager;

/**
 * Created by Manuel on 6/2/2018.
 */

public class GiveMeEventApplication extends Application {

    private FacebookGraphManager facebookGraphManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public FacebookGraphManager getFacebookGraphManager() {
        if (facebookGraphManager == null)
            facebookGraphManager = new FacebookGraphManager(getApplicationContext());
        return facebookGraphManager;
    }

}
