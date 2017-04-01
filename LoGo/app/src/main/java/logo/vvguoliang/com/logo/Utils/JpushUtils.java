package logo.vvguoliang.com.logo.Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 极光推送工具类
 * Created by liuyouxi on 2016/12/15.
 */
public class JpushUtils {

  public static final String KEY_APP_KEY = "JPUSH_APPKEY";

  public static boolean isEmpty(String s) {
    if (null == s)
      return true;
    if (s.length() == 0)
      return true;
    if (s.trim().length() == 0)
      return true;
    return false;
  }

  // 校验Tag Alias 只能是数字,英文字母和中文
  public static boolean isValidTagAndAlias(String s) {
    Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
    Matcher m = p.matcher(s);
    return m.matches();
  }

  // 取得AppKey
  public static String getAppKey(Context context) {
    Bundle metaData = null;
    String appKey = null;
    try {
      ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
          context.getPackageName(), PackageManager.GET_META_DATA);
      if (null != ai)
        metaData = ai.metaData;
      if (null != metaData) {
        appKey = metaData.getString(KEY_APP_KEY);
        if ((null == appKey) || appKey.length() != 24) {
          appKey = null;
        }
      }
    } catch (PackageManager.NameNotFoundException e) {

    }
    return appKey;
  }

}
