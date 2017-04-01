package logo.vvguoliang.com.logo.Fragment;

import android.content.Context;
import android.view.View;

import logo.vvguoliang.com.logo.BaseActivity.BaseFragment;
import logo.vvguoliang.com.logo.R;

/**
 * Created by vvguoliang on 2017/4/1.
 */

public class LogoMy extends BaseFragment implements View.OnClickListener {

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public LogoMy(Context context) {
        super();
        this.context = context;
    }

    public LogoMy() {
        super();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_logomy;
    }

    @Override
    protected void initView() {

    }
}
