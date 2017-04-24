package logo.vvguoliang.com.logo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import logo.vvguoliang.com.logo.BaseService.ProcessUtil;

public class NewService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCreate(){
		init();
	}

	private void init(){
		Log.d("NewService-->init", "1");
		new Thread(){
			public void run(){
				try {
					while(true){
						if (!ProcessUtil.checkIsExist(getApplicationContext(), "logo.vvguoliang.com.logo.service.MyService")) {
							Log.d("NewService-->thread", "true");
							Intent intent = new Intent(getApplicationContext(), MyService.class);
							startService(intent);
						}
						Thread.sleep(500);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}.start();
	}

	public void onDestroy(){
		Log.i("MyService-->ondesotry", "1");
		Intent localIntent = new Intent();
		localIntent.setClass(this, NewService.class);  //销毁时重新启动Service
		this.startService(localIntent);
	}

}
