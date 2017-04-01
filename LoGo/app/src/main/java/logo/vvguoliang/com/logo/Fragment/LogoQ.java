package logo.vvguoliang.com.logo.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import logo.vvguoliang.com.logo.BaseActivity.BaseFragment;
import logo.vvguoliang.com.logo.R;

/**
 * Created by vvguoliang on 2017/4/1.
 */
@SuppressLint("ValidFragment")
public class LogoQ extends BaseFragment implements View.OnClickListener {


    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public LogoQ(Context context) {
        super();
        this.context = context;
    }

    public LogoQ() {
        super();
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_logoq;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {

    }
}
