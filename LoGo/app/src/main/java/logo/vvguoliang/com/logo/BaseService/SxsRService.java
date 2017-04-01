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
public class SxsRService extends Service {
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
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        keepService1();
        SxsRService.this.startService(new Intent(SxsRService.this, SxsLService.class));
        SxsRService.this.bindService(new Intent(SxsRService.this, SxsLService.class), conn, Context.BIND_IMPORTANT);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        SxsRService.this.startService(new Intent(SxsRService.this, SxsLService.class));
        SxsRService.this.bindService(new Intent(SxsRService.this, SxsLService.class), conn, Context.BIND_IMPORTANT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SxsRService.this.bindService(new Intent(SxsRService.this, SxsLService.class), conn, Context.BIND_IMPORTANT);
        keepService1();
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    class MyBinder extends ProcessService.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void startService() throws RemoteException {
            SxsRService.this.startService(new Intent(SxsRService.this, SxsLService.class));
            SxsRService.this.bindService(new Intent(SxsRService.this, SxsLService.class), conn, Context.BIND_IMPORTANT);
        }

        @Override
        public void stopService() throws RemoteException {

        }

    }

    /**
     * 判断Service1是否还在运行，如果不是则启动Service1
     */
    private void keepService1() {
        boolean isRun = Utils.isServiceWork(SxsRService.this, CustomActions.Process_Name_sxsr);
        if (!isRun) {
            SxsRService.this.startService(new Intent(SxsRService.this, SxsLService.class));
            SxsRService.this.bindService(new Intent(SxsRService.this, SxsLService.class), conn, Context.BIND_IMPORTANT);
        }
    }

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            SxsRService.this.startService(new Intent(SxsRService.this, SxsLService.class));
            SxsRService.this.bindService(new Intent(SxsRService.this, SxsLService.class), conn, Context.BIND_IMPORTANT);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            unbindService(conn);
        }
    }
}
