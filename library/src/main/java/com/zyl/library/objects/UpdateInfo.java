package com.zyl.library.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class UpdateInfo {
	private
	@SerializedName("version")
	String version;

	private
	@SerializedName("memo")
	String memo;

	private
	@SerializedName("url")
	URL url;

	private
	@SerializedName("versionCode")
	String versionCode;

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public UpdateInfo() {
	}

	public UpdateInfo(String latestVersion, String memo, URL url) {
		this.version = latestVersion;
		this.url = url;
		this.memo = memo;
	}

	public String getServerAppVersion() {
		return version;
	}

	public void setServerAppVersion(String version) {
		this.version = version;
	}

	public String getReleaseMemo() {
		return memo;
	}

	public void setReleaseMemo(String memo) {
		this.memo = memo;
	}

	public URL getDownloadUrl() {
		return url;
	}

	public void setDownloadUrl(URL url) {
		this.url = url;
	}
}
