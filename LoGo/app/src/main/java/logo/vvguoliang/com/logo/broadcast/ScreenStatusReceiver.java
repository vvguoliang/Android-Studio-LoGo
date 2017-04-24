package logo.vvguoliang.com.logo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * 屏幕状态广播
 * @author yuegy
 *
 */
public class ScreenStatusReceiver extends BroadcastReceiver {
	String SCREEN_ON = "android.intent.action.USER_PRESENT";

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.e("ScreenStatusReceiver", SCREEN_ON);

		// 屏幕唤醒
		if(SCREEN_ON.equals(intent.getAction())){
			Log.e("ScreenStatusReceiver", SCREEN_ON);
		}
	}


}
