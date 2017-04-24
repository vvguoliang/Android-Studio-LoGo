package logo.vvguoliang.com.logo.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 类功能介绍
 *
 * @Title: BaseFragment.java
 * @Description: TODO Fragment 基类初始化 layout布局
 * @author: 秦国良
 * @data: 2016年5月16日 下午8:09:03
 * @ModifiedPerson:
 * @ModifiedPersonData：2016年5月16日下午8:09:03 @ModifyRemarks：
 * @version: V1.1.0
 * @Copyright 沙小僧
 */
public abstract class BaseFragment extends Fragment{
    private View mRootView;
    protected int onClickNum = 0;
    private long time = 0;
    private Activity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null)

        {
            String FRAGMENTS_TAG = "Android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);

        }
        mRootView = inflater.inflate(getLayout(), container, false);
        initView();
        return mRootView;
    }

    public View getmRootView() {
        return mRootView;
    }

    /**
     * 初始化Fragment的Layout
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 通过ID获取相应的View
     *
     * @return
     * @param start
     */
    protected View findViewById(int start) {
        if (mRootView == null) {
            return null;
        }
        return mRootView.findViewById(id);
    }

    /**
     * 判断是否是第一次看到这个页面 @Title: isFirstTime @author: xusonghui @Description:
     * TODO(这里用一句话描述这个方法的作用) @param: @return @return: boolean @throws
     */
    protected boolean isFirstTime() {
        return onClickNum <= 1;
    }

    /**
     * 在MainActivity里面会在点击的时候去调用该方法，用于在加载的时候加载数据，建议在这里面加载数据 @Title:
     * loadData @author: xusonghui @Description:
     * TODO(这里用一句话描述这个方法的作用) @param: @return: void @throws
     */
    public void loadData() {
        onClickNum++;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            loadData();
        } else {

        }
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
    }
}
