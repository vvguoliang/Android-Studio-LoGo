package logo.vvguoliang.com.logo.Utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.text.DecimalFormat;

public class TryAbnormal {
	public static void Logeg(Context context, JSONException e) {
		Log.e("========>>>>" + context, e.getMessage());
	}

	public static void catche(Context context, Exception e) {
		Log.e("=======>>>>" + context, e.getMessage());

	}

	public static void transformAbnormal(String... strings) {
		for (int i = 0; i < strings.length; i++) {
			if (strings[i].equals("") || strings[i] == null) {
				return;
			}
		}
	}

	public static int stringToDouble(String string) {
		int i = 0;
		try {
			if (string.equals("") || string == null || string.equals("null")) {
				return 0;
			}
			double d = Double.parseDouble(string);
			String aString = new DecimalFormat("0").format(d);
			return Integer.parseInt(aString);
		} catch (Exception e) {
			return 0;
		}
	}

	public static double stringToInteger(String string) {
		try {
			if (string.equals("") || string == null || string.equals("null")) {
				return 0.0;
			}
			return Double.parseDouble(string);
		} catch (Exception e) {
			return 0.0;
		}
	}
}
