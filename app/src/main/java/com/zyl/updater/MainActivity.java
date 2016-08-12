package com.zyl.updater;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zyl.library.AppUpdater;
import com.zyl.library.enums.Display;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		JSONObject object = new JSONObject();
		try {
			object.put("YWID", Config.YW_ID_UPDATE);
			object.put("appname", Config.YW_NAME);
			object.put("system", Config.YW_PLATFORM);
			object.put("usertype", Config.YW_NONE);
			object.put("userid", Config.YW_NONE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		AppUpdater<Update, Response> updater = new AppUpdater<>(this)
				.setDisplay(Display.DIALOG)
				.showAppUpdated(true)
				.setRequest(HttpClient.getRequest().getAppVersion(object.toString()))
				.init();
	}
}
