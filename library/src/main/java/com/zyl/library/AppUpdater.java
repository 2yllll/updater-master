package com.zyl.library;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.zyl.library.enums.AppUpdaterError;
import com.zyl.library.enums.Display;
import com.zyl.library.enums.Duration;
import com.zyl.library.objects.BaseResponse;
import com.zyl.library.objects.UpdateInfo;

import rx.Observable;


/**
 * @param <U> extends UpdateInfo 的类，升级信息的载体
 * @param <B> extends BaseResponse Gson转换器的尸体类
 */

public class AppUpdater<U extends UpdateInfo, B extends BaseResponse> implements IAppUpdater {
	private Context context;
	private LibraryPreferences libraryPreferences;
	private Display display;
	private Duration duration;
	private Integer showEvery;
	private Boolean showAppUpdated;
	private String titleUpdate, descriptionUpdate, btnDismiss, btnUpdate, btnDisable; // 可升级
	private String titleNoUpdate, descriptionNoUpdate; // 不可升级
	private int iconResId;

	private AlertDialog alertDialog;
	private Snackbar snackbar;

	private UtilsAsync<U, B> async = null;
	private Observable<B> observable = null;

	public AppUpdater(Context context) {
		this.context = context;
		this.libraryPreferences = new LibraryPreferences(context);
		this.display = Display.DIALOG;
		this.duration = Duration.NORMAL;
		this.showEvery = 1;
		this.showAppUpdated = false;
		this.iconResId = R.drawable.ic_info_white_24dp;
		this.async = new UtilsAsync<U, B>();

		// Dialog
		this.titleUpdate = context.getResources().getString(R.string.appupdater_update_available);
		this.titleNoUpdate = context.getResources().getString(R.string.appupdater_update_not_available);
		this.btnUpdate = context.getResources().getString(R.string.appupdater_btn_update);
		this.btnDismiss = context.getResources().getString(R.string.appupdater_btn_dismiss);
		this.btnDisable = context.getResources().getString(R.string.appupdater_btn_disable);
	}

	@Override
	public AppUpdater setDisplay(Display display) {
		this.display = display;
		return this;
	}

	@Override
	public AppUpdater setDuration(Duration duration) {
		this.duration = duration;
		return this;
	}

	@Override
	public AppUpdater showEvery(Integer times) {
		this.showEvery = times;
		return this;
	}

	@Override
	public AppUpdater showAppUpdated(Boolean res) {
		this.showAppUpdated = res;
		return this;
	}

	@Override
	public AppUpdater setDialogTitleWhenUpdateAvailable(@NonNull String title) {
		this.titleUpdate = title;
		return this;
	}

	@Override
	public AppUpdater setDialogTitleWhenUpdateAvailable(@StringRes int textResource) {
		this.titleUpdate = context.getString(textResource);
		return this;
	}

	@Override
	public AppUpdater setDialogDescriptionWhenUpdateAvailable(@NonNull String description) {
		this.descriptionUpdate = description;
		return this;
	}

	@Override
	public AppUpdater setDialogDescriptionWhenUpdateAvailable(@StringRes int textResource) {
		this.descriptionUpdate = context.getString(textResource);
		return this;
	}

	@Override
	public AppUpdater setDialogTitleWhenUpdateNotAvailable(@NonNull String title) {
		this.titleNoUpdate = title;
		return this;
	}

	@Override
	public AppUpdater setDialogTitleWhenUpdateNotAvailable(@StringRes int textResource) {
		this.titleNoUpdate = context.getString(textResource);
		return this;
	}

	@Override
	public AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@NonNull String description) {
		this.descriptionNoUpdate = description;
		return this;
	}

	@Override
	public AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@StringRes int textResource) {
		this.descriptionNoUpdate = context.getString(textResource);
		return this;
	}

	@Override
	public AppUpdater setDialogButtonUpdate(@NonNull String text) {
		this.btnUpdate = text;
		return this;
	}

	@Override
	public AppUpdater setDialogButtonUpdate(@StringRes int textResource) {
		this.btnUpdate = context.getString(textResource);
		return this;
	}

	@Override
	public AppUpdater setDialogButtonDismiss(@NonNull String text) {
		this.btnDismiss = text;
		return this;
	}

	@Override
	public AppUpdater setDialogButtonDismiss(@StringRes int textResource) {
		this.btnDismiss = context.getString(textResource);
		return this;
	}

	@Override
	public AppUpdater setDialogButtonDoNotShowAgain(@NonNull String text) {
		this.btnDisable = text;
		return this;
	}

	@Override
	public AppUpdater setDialogButtonDoNotShowAgain(@StringRes int textResource) {
		this.btnDisable = context.getString(textResource);
		return this;
	}

	@Override
	public AppUpdater setIcon(@DrawableRes int iconRes) {
		this.iconResId = iconRes;
		return this;
	}

	@Override
	public AppUpdater setRequest(@NonNull Observable observable) {
		this.observable = observable;
		return this;
	}

	@Override
	public AppUpdater init() {
		start();
		return this;
	}

	@Override
	public void start() {
		if (async == null) {
			Log.e("Updater", "必须调用setClient方法");
			return;
		}
		async.getServerAppVersion(context, observable, false, new
				LibraryListener() {
					@Override
					public void onSuccess(UpdateInfo update) {
						if (UtilsLibrary.isUpdateAvailable(UtilsLibrary.getAppInstalledVersion(context),
								update.getServerAppVersion())) {
							Integer successfulChecks = libraryPreferences.getSuccessfulChecks();
							if (UtilsLibrary.isAbleToShow(successfulChecks, showEvery)) {
								switch (display) {
									case DIALOG:
										alertDialog = UtilsDisplay.showUpdateAvailableDialog(context,
												titleUpdate, getDescriptionUpdate(context, update, Display
														.DIALOG), btnDismiss, btnUpdate, btnDisable,
												update.getDownloadUrl());
										alertDialog.show();
										break;
									case SNACKBAR:
										snackbar = UtilsDisplay.showUpdateAvailableSnackbar(context,
												getDescriptionUpdate(context, update, Display.SNACKBAR),
												UtilsLibrary.getDurationEnumToBoolean(duration),
												update.getDownloadUrl());
										snackbar.show();
										break;
									case NOTIFICATION:
										UtilsDisplay.showUpdateAvailableNotification(context, context.getResources().getString(R.string.appupdater_update_available),
												getDescriptionUpdate(context, update, Display.NOTIFICATION)
												, update.getDownloadUrl(), iconResId);
										break;
								}
							}
							libraryPreferences.setSuccessfulChecks(successfulChecks + 1);
						} else if (showAppUpdated) {
							switch (display) {
								case DIALOG:
									alertDialog = UtilsDisplay.showUpdateNotAvailableDialog(context, titleNoUpdate, getDescriptionNoUpdate(context));
									alertDialog.show();
									break;
								case SNACKBAR:
									snackbar = UtilsDisplay.showUpdateNotAvailableSnackbar(context, getDescriptionNoUpdate(context), UtilsLibrary.getDurationEnumToBoolean(duration));
									snackbar.show();
									break;
								case NOTIFICATION:
									UtilsDisplay.showUpdateNotAvailableNotification(context, context.getResources().getString(R.string.appupdater_update_not_available), getDescriptionNoUpdate(context), iconResId);
									break;
							}
						}
					}

					@Override
					public void onFailed(AppUpdaterError error) {
						if (error == AppUpdaterError.XML_URL_MALFORMED) {
							throw new IllegalArgumentException("无效的XML文件!");
						}
					}
				});
	}

	@Override
	public void dismiss() {
		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.dismiss();
		}
		if (snackbar != null && snackbar.isShown()) {
			snackbar.dismiss();
		}
	}

	private String getDescriptionUpdate(Context context, UpdateInfo update, Display display) {
		if (descriptionUpdate == null) {
			switch (display) {
				case DIALOG:
					if (!TextUtils.isEmpty(update.getReleaseMemo())) {
						return String.format(context.getResources().getString(R.string
										.appupdater_update_available_description_dialog_before_release_notes),
								update.getServerAppVersion(), update.getReleaseMemo());
					} else {
						return String.format(context.getResources().getString(R.string.
								appupdater_update_available_description_dialog), update
								.getServerAppVersion(), UtilsLibrary.getAppName(context));
					}

				case SNACKBAR:
					return String.format(context.getResources().getString(R.string
							.appupdater_update_available_description_snackbar), update.getServerAppVersion());

				case NOTIFICATION:
					return String.format(context.getResources().getString(R.string
							.appupdater_update_available_description_notification), update.getServerAppVersion());

			}
		}
		return descriptionUpdate;

	}

	private String getDescriptionNoUpdate(Context context) {
		if (descriptionNoUpdate == null) {
			return String.format(context.getResources().getString(R.string.appupdater_update_not_available_description), UtilsLibrary.getAppName(context));
		} else {
			return descriptionNoUpdate;
		}
	}

}
