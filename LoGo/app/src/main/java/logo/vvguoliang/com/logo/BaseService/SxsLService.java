package logo.vvguoliang.com.logo.BaseService;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import logo.vvguoliang.com.logo.ProcessService;
import logo.vvguoliang.com.logo.Utils.CustomActions;
import logo.vvguoliang.com.logo.Utils.Utils;

/**
 * Created by vvguoliang on 2017/1/9.
 */
@SuppressWarnings("WrongConstant")
public class SxsLService extends Service {
    private MyBinder binder;
    private MyConn conn;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        if (conn == null) {
            conn = new MyConn();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SxsLService.this.bindService(new Intent(SxsLService.this, SxsRService.class), conn, Context.BIND_IMPORTANT);
        keepService1();
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private class MyBinder extends ProcessService.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }

        @Override
        public void startService() throws RemoteException {
            SxsLService.this.startService(new Intent(SxsLService.this, SxsRService.class));
            SxsLService.this.bindService(new Intent(SxsLService.this, SxsRService.class), conn, Context.BIND_IMPORTANT);
        }

        @Override
        public void stopService() throws RemoteException {

        }

    }

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            SxsLService.this.startService(new Intent(SxsLService.this, SxsRService.class));
            SxsLService.this.bindService(new Intent(SxsLService.this, SxsRService.class), conn, Context.BIND_IMPORTANT);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        keepService1();
        SxsLService.this.startService(new Intent(SxsLService.this, SxsRService.class));
        SxsLService.this.bindService(new Intent(SxsLService.this, SxsRService.class), conn, Context.BIND_IMPORTANT);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        SxsLService.this.startService(new Intent(SxsLService.this, SxsRService.class));
        SxsLService.this.bindService(new Intent(SxsLService.this, SxsRService.class), conn, Context.BIND_IMPORTANT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            unbindService(conn);
        }
    }

    /**
     * 判断Service1是否还在运行，如果不是则启动Service1
     */
    private void keepService1() {
        boolean isRun = Utils.isServiceWork(this, CustomActions.Process_Name_sxsl);
        if (!isRun) {
            SxsLService.this.startService(new Intent(SxsLService.this, SxsRService.class));
            SxsLService.this.bindService(new Intent(SxsLService.this, SxsRService.class), conn, Context.BIND_IMPORTANT);
        }
    }
}
