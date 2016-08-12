package com.zyl.library;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.zyl.library.enums.Duration;
import com.zyl.library.objects.Version;

import java.net.MalformedURLException;
import java.net.URL;


class UtilsLibrary {

	static String getAppName(Context context) {
		return context.getString(context.getApplicationInfo().labelRes);
	}

	static String getAppPackageName(Context context) {
		return context.getPackageName();
	}

	static String getAppInstalledVersion(Context context) {
		String version = "0.0.0.0";

		try {
			version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return version;
	}

	static Boolean isUpdateAvailable(String installedVersion, String latestVersion) {
		Boolean res = false;

		if (!installedVersion.equals("0.0.0.0") && !latestVersion.equals("0.0.0.0")) {
			Version installed = new Version(installedVersion);
			Version latest = new Version(latestVersion);
			res = installed.compareTo(latest) < 0;
		}

		return res;
	}

	static Boolean isStringAVersion(String version) {
		return version.matches(".*\\d+.*");
	}

	static Boolean isStringAnUrl(String s) {
		Boolean res = false;
		try {
			new URL(s);
			res = true;
		} catch (MalformedURLException ignored) {
		}

		return res;
	}

	static Boolean getDurationEnumToBoolean(Duration duration) {
		Boolean res = false;

		switch (duration) {
			case INDEFINITE:
				res = true;
				break;
		}

		return res;
	}

	static Intent intentToUpdate(Context context, URL url) {
		return new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
	}

	static void goToUpdate(Context context, URL url) {
		context.startActivity(intentToUpdate(context, url));
	}

	static Boolean isAbleToShow(Integer successfulChecks, Integer showEvery) {
		return successfulChecks % showEvery == 0;
	}

	static Boolean isNetworkAvailable(Context context) {
		Boolean res = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null) {
				res = networkInfo.isConnected();
			}
		}

		return res;
	}

}
