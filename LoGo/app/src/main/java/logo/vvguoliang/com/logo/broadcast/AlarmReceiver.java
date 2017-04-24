package logo.vvguoliang.com.logo.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import logo.vvguoliang.com.logo.service.MyService;

public class AlarmReceiver extends BroadcastReceiver {

	@SuppressLint("LongLogTag")
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("AlarmReceiver-->onReceive", "1");
		Intent intent2 = new Intent();
		intent2.setClass(context, MyService.class);
		context.startService(intent2);
	}

}
