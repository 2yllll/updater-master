package com.zyl.library;

import android.content.Context;

import com.zyl.library.enums.AppUpdaterError;
import com.zyl.library.objects.BaseResponse;
import com.zyl.library.objects.UpdateInfo;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


class UtilsAsync<U extends UpdateInfo, B extends BaseResponse> {

	public void getServerAppVersion(Context context, Observable<B> observable,
									Boolean fromUtils, final AppUpdater.LibraryListener listener) {
		LibraryPreferences libraryPreferences = new LibraryPreferences(context);
		if (UtilsLibrary.isNetworkAvailable(context)) {
			if (!fromUtils && !libraryPreferences.getAppUpdaterShow()) {
				return;
			}
		} else {
			listener.onFailed(AppUpdaterError.NETWORK_NOT_AVAILABLE);
			return;
		}
		observable.subscribeOn(Schedulers.io()).onErrorResumeNext(new Func1<Throwable,
				Observable<B>>() {
			@Override
			public Observable<B> call(Throwable throwable) {
				return Observable.empty();
			}
		}).map(new Func1<B, U>() {

			@Override
			public U call(B a) {
				U t = null;
				if (a.getData() instanceof ArrayList) {
					t = (U) ((ArrayList) a.getData()).get(0);

				} else if (a.getData() instanceof UpdateInfo) {
					t = (U) a.getData();
				}
				return t;
			}
		}).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<U>() {
			@Override
			public void call(U t) {
				if (UtilsLibrary.isStringAVersion(t.getServerAppVersion())) {
					listener.onSuccess(t);
				} else {
					listener.onFailed(AppUpdaterError.VERSION_INVALED);
				}
			}
		});
	}
}
