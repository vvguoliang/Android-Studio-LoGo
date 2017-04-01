package logo.vvguoliang.com.logo.Utils;

import org.json.JSONArray;

import java.io.Serializable;

public class HomePage_Entity implements Serializable {
	/**
	 * @Title: HomePage_Entity.java
	 * @Package com.sxsfinance.sxsfinance_android_libs.Base
	 * @Description: TODO 实体类 基类
	 * @author A18ccms A18ccms_gmail_com
	 * @date 2016-3-11 下午3:10:04
	 * @version V1.0
	 */
	private static final long serialVersionUID = 1L;
	
	String code;
	String num;
	JSONArray data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public JSONArray getData() {
		return data;
	}

	public void setData(JSONArray data) {
		this.data = data;
	}

}
