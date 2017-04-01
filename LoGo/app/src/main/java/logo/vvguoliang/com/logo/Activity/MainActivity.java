package logo.vvguoliang.com.logo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import logo.vvguoliang.com.logo.BaseActivity.BaseActivity;
import logo.vvguoliang.com.logo.BaseService.SxsLService;
import logo.vvguoliang.com.logo.BaseService.SxsRService;
import logo.vvguoliang.com.logo.Fragment.LogoFind;
import logo.vvguoliang.com.logo.Fragment.LogoMy;
import logo.vvguoliang.com.logo.Fragment.LogoQ;
import logo.vvguoliang.com.logo.Fragment.LogoWei;
import logo.vvguoliang.com.logo.R;
import logo.vvguoliang.com.logo.Utils.ToatUtils;
import logo.vvguoliang.com.logo.View.MainActivityView;

public class MainActivity extends BaseActivity implements MainActivityView.OnItemClickListener {

    private Context mContext;// 上下文

    private Activity activity;

    private MainActivityView mainActivityView;

    private List<Fragment> listfragment = new ArrayList<>();

    private List<Fragment> listnewftagment = new ArrayList<>();

    private String[] titles = {"首页", "产品", "我的", "发现"};
    private int[] selectedImage = {R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
    private int[] unSelectedImage = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    private int mHeight;

    private boolean isGetHeight = true;

    private int mSelectPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {
        for (int i = 0; i <= 3; i++) {
            listfragment.add(null);
        }
        listnewftagment.add(new LogoWei(MainActivity.this));
        listnewftagment.add(new LogoQ(MainActivity.this));
        listnewftagment.add(new LogoFind(MainActivity.this));
        listnewftagment.add(new LogoMy(MainActivity.this));

        // 获取屏幕宽度
        Display dm = getWindowManager().getDefaultDisplay();
        final int screenWith = dm.getWidth();
        mainActivityView = (MainActivityView) findViewById(R.id.act_main_tab);
        // 初始化获取底部导航自身高度
        final ViewTreeObserver vt = mainActivityView.getViewTreeObserver();
        vt.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (isGetHeight) {
                    mHeight = mainActivityView.getHeight();
                    mainActivityView.setLayout(titles, selectedImage, unSelectedImage, screenWith, mHeight, MainActivity.this);
                    mainActivityView.setColorLing(mSelectPosition);
                    mainActivityView.setOnItemClickListener(MainActivity.this);
                    isGetHeight = false;
                }
                return true;
            }
        });
        showFragment(mSelectPosition);

    }

    @Override
    public void onItemClick(int position) {
        showFragment(position);
    }

    /**
     * 动态添加和显示fragment
     *
     * @param position
     */
    private void showFragment(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        setImmerStateBar(false);
        for (int i = 0; i < listfragment.size(); i++) {
            if (position == i) {
                if (listfragment.get(i) == null) {
                    listfragment.remove(i);
                    listfragment.add(i, listnewftagment.get(i));
                    if (!listnewftagment.get(i).isAdded()) {
                        transaction.add(R.id.cat_main_fragment_content, listfragment.get(i));
                    } else {
                        transaction.show(listfragment.get(i));
                    }
                } else {
                    transaction.show(listfragment.get(i));
                }
            }
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        for (int i = 0; i < listfragment.size(); i++) {
            if (listfragment.get(i) != null) {
                transaction.hide(listfragment.get(i));
            }
        }
    }

    /**
     * 连续按两次返回键就退出
     */
    private int keyBackClickCount = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyBackClickCount++) {
                case 0:
                    ToatUtils.showShort1(this, "再按一次退出");
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 3000);
                    break;
                case 1:
                    startService(new Intent(MainActivity.this, SxsLService.class));
                    startService(new Intent(MainActivity.this, SxsRService.class));

                    Intent intent = new Intent();
                    Context c = null;
                    try {
                        c = createPackageContext("com.sxsfinance.SXS", Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    intent.setClassName(c, "com.sxsfinance.SXS.broadcast.SxsBroadcastReceiver");
                    intent.setAction("com.sxsfinance.SXS");
                    intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
                    sendBroadcast(intent);

                    finish();
                    break;
                default:
                    break;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
