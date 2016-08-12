# updater-master

* 对[AppUpdater](https://github.com/javiersantos/AppUpdater)进行的一些自己需要的改动

## 使用
```JAVA
/**
 * AppUpdater <U,B>
 * @param <U> extends UpdateInfo 的类，升级信息的载体
 * @param <B> extends BaseResponse Gson转换器的尸体类
 */
AppUpdater<Update, Response> updater = new AppUpdater<Update, Response>(this)
				.setDisplay(Display.DIALOG)
				.showAppUpdated(true)
				.setRequest(HttpClient.getRequest().getAppVersion(object.toString()))
				.init();
```

```JAVA
//提示方式   DIALOG/SNACKBAR/NOTIFICATION
.setDisplay(Display.DIALOG)     
```
```JAVA
//没有可用更新的时候是否提示
.showAppUpdated(true)
```	
```JAVA
//没有可用更新的时候是否提示   @param rx.Observable<B>
.setRequest(HttpClient.getRequest().getAppVersion(object.toString()))
```
```JAVA
//执行
.init();
```
				
  
