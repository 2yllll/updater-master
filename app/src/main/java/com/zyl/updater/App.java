package com.zyl.updater;

import android.app.Application;

/**
 * Created by Administrator on 2016-08-10.
 */

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		HttpClient.init(Config.BASE_URL);
	}
}
