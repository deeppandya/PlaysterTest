package mainpackage.playstertest;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.support.multidex.MultiDex;

import mainpackage.playstertest.receiver.NetworkStateReceiver;
import mainpackage.playstertest.support.Scopes;
import mortar.MortarScope;

/**
 * Created by deeppandya on 2017-04-10.
 */

public class PlaysterApp extends Application {

    private MortarScope rootScope;

    @Override public Object getSystemService(String name) {
        if (rootScope == null) rootScope = MortarScope.buildRootScope().build(Scopes.APP);
        return rootScope.hasService(name) ? rootScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
