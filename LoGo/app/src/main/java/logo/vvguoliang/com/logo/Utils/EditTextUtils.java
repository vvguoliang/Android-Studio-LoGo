package logo.vvguoliang.com.logo.Utils;

import android.widget.EditText;

/**
 * EditText工具类
 * Created by liuyouxi on 2016/12/13.
 */

public class EditTextUtils {

  //获得光标
  public static void editTextCursorVisible(EditText editText) {
    editText.requestFocus();
    editText.setCursorVisible(true);
  }

  //失去光标
  public static void editTextCursorGone(EditText editText) {
    editText.clearFocus();
    editText.setCursorVisible(false);
  }

}
