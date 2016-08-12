package com.zyl.updater;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016-08-01.
 */

public class HttpClient {
	//	private Class<API> entityClass;
	//	private API t;
	public static ApiService service;

	public static void init(String url) {
		//		Type genType = getClass().getGenericSuperclass();
		//		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		//		entityClass = (Class) params[0];
		Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
				.client(new OkHttpClient())
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
		service = retrofit.create(ApiService.class);
	}

	public static ApiService getRequest() {
		return service;
	}
}
