package com.zyl.library.objects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-08-10.
 */

public class BaseResponse {
	private
	@SerializedName("data")
	ArrayList<UpdateInfo> data;
	private
	@SerializedName("error")
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDatas(ArrayList<UpdateInfo> datas) {
		this.data = datas;
	}

	public Object getData() {
		return data;
	}
}
