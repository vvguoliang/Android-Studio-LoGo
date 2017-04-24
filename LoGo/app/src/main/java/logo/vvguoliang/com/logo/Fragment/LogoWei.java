package logo.vvguoliang.com.logo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import logo.vvguoliang.com.logo.BaseActivity.BaseFragment;
import logo.vvguoliang.com.logo.R;
import logo.vvguoliang.com.logo.service.MyService;

/**
 * Created by vvguoliang on 2017/4/1.
 */

public class LogoWei extends BaseFragment implements View.OnClickListener {

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public LogoWei(Context context) {
        super();
        this.context = context;
    }

    public LogoWei() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), null);
        initView();
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_logowei;
    }

    @Override
    protected void initView() {
        startBtn = (Button) findViewById(R.id.start);
        stopBtn = (Button) findViewById(R.id.stop);
        bindEvent();
    }

    private Button startBtn;

    private Button stopBtn;

    @Override
    public void onClick(View v) {

    }

    public void onPause() {
        super.onPause();
    }

    private void bindEvent() {
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.i("MainActivity-->startbtn", "1");
                setJNIEnv();
                mainThread();

            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("MainActivity-->stopbtn", "1");
                Intent intent = new Intent(getActivity(), MyService.class);
                context.stopService(intent);
            }
        });
    }

    //由JNI中的线程回调
    private static void fromJNI(final int i) {
        Log.i("Java------>fromJNI", "" + i);
    }

    //设置环境参数
    private native void setJNIEnv();

    //守护进程的开启
    private native void mainThread();


    static {
        System.loadLibrary("MyService");
    }
}
