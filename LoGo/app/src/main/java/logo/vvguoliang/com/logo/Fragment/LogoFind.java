package logo.vvguoliang.com.logo.Fragment;

import android.content.Context;
import android.view.View;

import logo.vvguoliang.com.logo.BaseActivity.BaseFragment;
import logo.vvguoliang.com.logo.R;

/**
 * Created by vvguoliang on 2017/4/1.
 */

public class LogoFind extends BaseFragment implements View.OnClickListener {

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public LogoFind(Context context) {
        super();
        this.context = context;
    }

    public LogoFind() {
        super();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayout() {
        return R.layout.ftagment_logofind;
    }

    @Override
    protected void initView() {

    }
}
