package logo.vvguoliang.com.logo.Utils;

import java.io.IOException;

public class Utils_passwod {

	private static Utils_passwod passwod = null;

	public static Utils_passwod getInstance() {
		synchronized (Utils_passwod.class) {
			if (passwod == null) {
				passwod = new Utils_passwod();
			}
		}
		return passwod;
	}

	/** 解密 */
	public String main(String mid, String key) throws IOException {
		
		String mmm = new String(Base64.decode(mid), "UTF-8");
		char str[] = mmm.toCharArray();
		int k;
		for (k = 0; k < str.length; k++) {
			str[k] = (char) (str[k] ^ key.charAt(k % 7));
		}
		String s = new String(str);
		return toStringHex(s);
	}

	private String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	// 字符串转十六进制
	public String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	// 加密
	public String jiaMi(String vauls, String key) {
		char str[] = toHexString(vauls).toCharArray();
		int k;
		for (k = 0; k < str.length; k++) {
			str[k] = (char) (str[k] ^ key.charAt(k % 8));
		}
		String s = new String(str);
		try {
			String mmm = Base64.encode(s.getBytes());
			return mmm;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String enUnicode(String content) {// 将汉字转换为16进制数
		String enUnicode = null;
		for (int i = 0; i < content.length(); i++) {
			if (i == 0) {
				enUnicode = getHexString(Integer.toHexString(content.charAt(i))
						.toUpperCase());
			} else {
				enUnicode = enUnicode
						+ getHexString(Integer.toHexString(content.charAt(i))
								.toUpperCase());
			}
		}
		return enUnicode;
	}

	private static String getHexString(String hexString) {
		String hexStr = "";
		for (int i = hexString.length(); i < 4; i++) {
			if (i == hexString.length())
				hexStr = "0";
			else
				hexStr = hexStr + "0";
		}
		return hexStr + hexString;
	}
}
