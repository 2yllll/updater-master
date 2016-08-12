package com.zyl.updater;
import com.zyl.library.objects.BaseResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by Administrator on 2016-08-01.
 */

public interface ApiService {

	@FormUrlEncoded
	@POST("Hz_Data_I/hz_Action")
	Observable<BaseResponse> getAppVersion(@Field("xmlstr") String xmlstr);

}
