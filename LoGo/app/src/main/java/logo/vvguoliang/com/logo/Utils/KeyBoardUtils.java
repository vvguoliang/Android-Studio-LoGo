package logo.vvguoliang.com.logo.Utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @Title: KeyBoardUtils.java
 * @Package
 * @Description: TODO 打开或关闭软键盘
 * @author A18ccms A18ccms_gmail_com
 * @date 2016-3-8 下午4:23:46
 * @version V1.0
 */
public class KeyBoardUtils {

	private static KeyBoardUtils keyBoardUtils = null;

	public static KeyBoardUtils getInstance() {
		synchronized (KeyBoardUtils.class) {
			if (keyBoardUtils == null) {
				keyBoardUtils = new KeyBoardUtils();
			}
		}
		return keyBoardUtils;
	}

	/**
	 * 打卡软键盘
	 *
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public void openKeybord(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 打卡软键盘
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public void openKeybord(Button mButton, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mButton, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 打卡软键盘
	 *
	 * @param mLinearLayout
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public void openKeybord(LinearLayout mLinearLayout, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mLinearLayout, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	// 通过定时器强制隐藏虚拟键盘
	public void timerHideKeyboard(final View v) {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
				} else {
					timer.cancel();
				}
			}
		}, 10);
	}

	/**
	 * 关闭软键盘
	 *
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public void closeKeybord(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive())
			imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	/**
	 * 关闭软键盘
	 *
	 * @param mLinearLayout
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public void closeKeybord(LinearLayout mLinearLayout, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive())
			imm.hideSoftInputFromWindow(mLinearLayout.getWindowToken(), 0);
	}

	/**
	 * 关闭软键盘
	 *
	 * @param mContext
	 *            上下文
	 */
	public void closeKeybord(Button mButton, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive())
			imm.hideSoftInputFromWindow(mButton.getWindowToken(), 0);
	}

	/**
	 * 关闭软键盘
	 *
	 * @param mImageView
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public void closeKeybord(ImageView mImageView, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive())
			imm.hideSoftInputFromWindow(mImageView.getWindowToken(), 0);
	}

	/**
	 * 关闭软键盘
	 *
	 * @param mContext
	 *            上下文
	 */
	public void closeKeybord(TextView mTextView, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive())
			imm.hideSoftInputFromWindow(mTextView.getWindowToken(), 0);
	}

	/**
	 * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
	 *
	 * @param v
	 * @param event
	 * @return
	 */
	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
					+ v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			} else {
				return true;
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
		return false;
	}

	/**
	 * 多种隐藏软件盘方法的其中一种
	 *
	 */
	public void hideSoftInput(View view , Context context) {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm.isActive())
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
}
