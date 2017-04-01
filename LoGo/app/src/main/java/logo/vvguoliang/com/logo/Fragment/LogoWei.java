package logo.vvguoliang.com.logo.Fragment;

import android.content.Context;
import android.view.View;

import logo.vvguoliang.com.logo.BaseActivity.BaseFragment;
import logo.vvguoliang.com.logo.R;

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
    protected int getLayout() {
        return R.layout.fragment_logowei;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {

    }
}
