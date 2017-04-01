package logo.vvguoliang.com.logo.Utils;

/**
 * 广播常量类
 *
 * @author liuyouxi
 * @created by 2016/11/2
 */
public class CustomActions {

    /**
     * 清除信息
     */
    public static final String ACTION_EXIT_MSG = "exitMsg";

    /**
     * 用户登录的广播
     */
    public static final String ACTION_USER_LOGINED = "com.sxsfinance.SXS.USER_LOGINED";

    /**
     * 用户登出的广播
     */
    public static final String ACTION_USER_LOGOUT = "com.sxsfinance.SXS.USER_LOGOUT";

    /**
     * 极光推送
     */
    public static final String MESSAGE_RECEIVED_ACTION = "com.sxsfinance.SXS.MESSAGE_RECEIVED_ACTION";

    public static final String KEY_TITLE = "title";

    public static final String KEY_MESSAGE = "message";

    public static final String KEY_EXTRAS = "extras";

    // 用于判断进程是否运行
    public static final String Process_Name_sxsl = "com.sxsfinance.SXS.service.SxsLService";

    public static final String Process_Name_sxsr = "com.sxsfinance.SXS.service.SxsRService";

    public static final String Process_Name_sxsb = "com.sxsfinance.SXS.broadcast.SxsBroadcastReceiver";

    //小沙化缘
    public static final String PRODUCT_CURRENT = "com.sxsfinance.SXS.broadcast.PRODUCT_CURRENT";

    //师傅化缘
    public static final String PRODUCT_REGULAR = "com.sxsfinance.SXS.broadcast.PRODUCT_REGULAR";

    //四方化缘
    public static final String PRODUCT_SCATTER = "com.sxsfinance.SXS.broadcast.PRODUCT_SCATTER";

    //投资成功
    public static final String INVEST_SUCCESS = "com.sxsfinance.SXS.broadcast.INVEST_SUCCESS";

    //回到首页
    public static final String SELECT_HOME = "com.sxsfinance.SXS.broadcast.SELECT_HOME";

}
