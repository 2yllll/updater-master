package com.zyl.library;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.zyl.library.enums.AppUpdaterError;
import com.zyl.library.enums.Display;
import com.zyl.library.enums.Duration;
import com.zyl.library.objects.UpdateInfo;

import rx.Observable;


public interface IAppUpdater {
	/**
	 * 显示提示升级的方式(notification,dialog,snackbar)
	 *
	 * @param display
	 * @return this
	 */
	AppUpdater setDisplay(Display display);

	/**
	 * SnackBar显示时长类型
	 *
	 * @param duration
	 * @return this
	 */
	AppUpdater setDuration(Duration duration);

	/**
	 * Set the times the app ascertains that a new update is available and display a dialog, Snackbar or notification. It makes the updates less invasive. Default: 1.
	 *
	 * @param times every X times
	 * @return this
	 */
	AppUpdater showEvery(Integer times);

	/**
	 * 没更新的时候是否显示提示. Default: false.
	 *
	 * @param res
	 * @return this
	 */
	AppUpdater showAppUpdated(Boolean res);

	/**
	 * 有更新时 dialog的title
	 *
	 * @param title
	 * @return this
	 */
	AppUpdater setDialogTitleWhenUpdateAvailable(@NonNull String title);

	/**
	 * 有更新时 dialog的title
	 *
	 * @param textResource
	 * @return this
	 */
	AppUpdater setDialogTitleWhenUpdateAvailable(@StringRes int textResource);

	/**
	 * 有更新时 dialog的描述
	 *
	 * @param description
	 * @return this
	 */
	AppUpdater setDialogDescriptionWhenUpdateAvailable(@NonNull String description);

	/**
	 * 有更新时 dialog的描述
	 *
	 * @param textResource
	 * @return this
	 */
	AppUpdater setDialogDescriptionWhenUpdateAvailable(@StringRes int textResource);

	/**
	 * 无更新时 dialog的title
	 *
	 * @param title
	 * @return this
	 */
	AppUpdater setDialogTitleWhenUpdateNotAvailable(@NonNull String title);

	/**
	 * 无更新时 dialog的title
	 *
	 * @param textResource
	 * @return this
	 */
	AppUpdater setDialogTitleWhenUpdateNotAvailable(@StringRes int textResource);

	/**
	 * 无更新时 dialog的描述
	 *
	 * @param description
	 * @return this
	 */
	AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@NonNull String description);

	/**
	 * 无更新时 dialog的描述
	 *
	 * @param textResource
	 * @return this
	 */
	AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@StringRes int textResource);

	/**
	 * dialog的"确定"button文字
	 *
	 * @param text
	 * @return this
	 */
	AppUpdater setDialogButtonUpdate(@NonNull String text);

	/**
	 * dialog的"确定"button文字
	 *
	 * @param textResource
	 * @return this
	 */
	AppUpdater setDialogButtonUpdate(@StringRes int textResource);

	/**
	 * dialog的"取消"button文字
	 *
	 * @param text
	 * @return this
	 */
	AppUpdater setDialogButtonDismiss(@NonNull String text);

	/**
	 * dialog的"取消"button文字
	 *
	 * @param textResource
	 * @return this
	 */
	AppUpdater setDialogButtonDismiss(@StringRes int textResource);

	/**
	 * dialog的"不再显示"button文字
	 *
	 * @param text
	 * @return this
	 */
	AppUpdater setDialogButtonDoNotShowAgain(@NonNull String text);

	/**
	 * dialog的"不再显示"button文字
	 *
	 * @param textResource
	 * @return this
	 */
	AppUpdater setDialogButtonDoNotShowAgain(@StringRes int textResource);

	/**
	 * 通知栏小图标
	 *
	 * @param iconRes
	 * @return this
	 */
	AppUpdater setIcon(@DrawableRes int iconRes);

	/**
	 * 执行
	 *
	 * @return this
	 * @deprecated use {@link #start()} instead
	 */
	AppUpdater init();

	/**
	 * 执行
	 */
	void start();

	/**
	 * Dismisses dialog 或者snackbar.
	 */
	void dismiss();

	/**
	 * Retrofit 请求的Observable
	 */
	AppUpdater setRequest(@NonNull Observable observable);


	interface LibraryListener {
		void onSuccess(UpdateInfo update);

		void onFailed(AppUpdaterError error);
	}
}
