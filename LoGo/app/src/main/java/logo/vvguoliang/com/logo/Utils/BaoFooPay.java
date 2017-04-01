package logo.vvguoliang.com.logo.Utils;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Title: BaoFooPay.java
 * @Package com.sxsfinance.sxsfinance_android_libs_Utils
 * @Description: TODO 宝付
 * @author A18ccms A18ccms_gmail_com
 * @date 2016-3-22 上午10:03:45
 * @version V1.0
 */
public class BaoFooPay {

	private static BaoFooPay baoFooPay = null;

	public static BaoFooPay getInstance() {
		synchronized (BaoFooPay.class) {
			if (baoFooPay == null) {
				baoFooPay = new BaoFooPay();
			}
		}
		return baoFooPay;
	}

	public String PAY_BUSINESS = "PAY_BUSINESS";
	public String PAY_TOKEN = "PAY_TOKEN";
	public String PAY_RESULT = "PAY_RESULT";
	public String PAY_MESSAGE = "PAY_MESSAGE";
	public int PAY_RESULT_CODE = 199;
	public String PAY_SDK_STORE_V1 = "PAY_SDK_STORE_V1";
	public String PAY_SDK_STORE_KEY = "sdk@bfv1";
	public String PAY_SDK_STORE_CARD = "PAY_SDK_STORE_CARD";

	public String URL_BAOFOO_GATEWAY_TEST = "https://tgw.baofoo.com/apipay/orderRequest";
	public String URL_BAOFOO_GATEWAY_BUSINESS = "https://gw.baofoo.com/apipay/orderRequest";
	
	public int REQUEST_CODE_BAOFOO_SDK = 100;
	public int LIST_BANK = 0x1;
	public String getHtmlData(String paysdkurl , String tradeNo) {
//		https://tgw.baofoo.com/apipay/orderRequest
		StringBuilder formHtml = new StringBuilder();
		formHtml.append("<html><head><meta charset='utf-8'></head>");
		formHtml.append("<body onload='payform.submit()'><form name='payform' action='");
		formHtml.append(paysdkurl); // 提交地址
		formHtml.append("' method='post'>");
		// 参数列表
		formHtml.append("<input type='hidden' name='tradeNo' value='")
				.append(tradeNo).append("' />");
		formHtml.append("<input type='hidden' name='version' value='1.0.0' />");
		formHtml.append("</form></body>").append("").append("</html>");
		return formHtml.toString();
	}

	/**
	 * 宝付的银行卡选择，获取序列号
	 * 
	 * 这个跟下面的listview 银行列表相关 这个方法在Handler调用
	 */

	public String sendBanklist() throws Exception {
		String URL_BAOFOO_GATEWAY_TEST = "https://tgw.baofoo.com/apipay/sdk";
		String orderNo = null; // 保留请求序列号，此序列号与商户自身订单号无关，请注意
		URL myURL = new URL(URL_BAOFOO_GATEWAY_TEST);
		HttpURLConnection con = (HttpURLConnection) myURL.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setUseCaches(false);
		con.setConnectTimeout(30000);
		con.connect();
		// DataOutputStream out = new DataOutputStream(con.getOutputStream());
		// out.writeBytes(Primes);
		// out.flush();
		// out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				con.getInputStream()));

		String s = reader.readLine();
		reader.close();
		con.disconnect();
		JSONObject ret = new JSONObject(s);
		String code = ret.getString("retCode");
		String retmsg = ret.getString("retMsg");

		orderNo = ret.getString("tradeNo");
		return orderNo;
	}

	/**
	 * 
	 * @Title: BaoFooPay.java
	 * @Package com.sxsfinance.sxsfinance_android_libs_Utils
	 * @Description: TODO 银行ID
	 * @author A18ccms A18ccms_gmail_com
	 * @date 2016-3-22 上午10:35:31
	 * @version V1.0
	 */
	public List<Map<String, String>> maps() {
		List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		map = new HashMap<String, String>();
		map.put("name", "中国工商银行");
		map.put("id", "ICBC");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "中国农业银行");
		map.put("id", "ABC");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "中国建设银行");
		map.put("id", "CCB");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "中国银行");
		map.put("id", "BOC");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "中国交通银行");
		map.put("id", "BCOM");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "兴业银行");
		map.put("id", "CIB");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "中信银行");
		map.put("id", "CITIC");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "中国光大银行");
		map.put("id", "CEB");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "平安银行");
		map.put("id", "PAB");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "中国邮政储蓄银行");
		map.put("id", "PSBC");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "上海银行");
		map.put("id", "SHB");
		maps.add(map);
		map = new HashMap<String, String>();
		map.put("name", "浦东发展银行");
		map.put("id", "SPDB");
		maps.add(map);
		return maps;
	}

	private final static String URL_BAOFOO_GATEWAY = "http://tgw.baofoo.com/rsa/merchantPost.action";// ""https://tgw.baofoo.com/apipay/sdk";
	private final static String URL_OK = "0000";

	// 请求商户服务，获取订单交易流水号,本方法写于商户的服务器端,请求宝付接口，获取交易流水号
	public List<Map<String, String>> getOrderNo(Context context,
			String ipt_money_ed, String id_card_ed, String name_ed,
			String bank_card_ed, String mobile_ed, String pay_code)
			throws Exception {
		String msg = "";
		String orderNo = null; // 保留请求序列号，此序列号与商户自身订单号无关，请注意

		BigDecimal orderMoney = new BigDecimal(ipt_money_ed);
		String txn_amt = String.valueOf(orderMoney.multiply(
				new BigDecimal("100")).intValue());
		// String pay_code = onReadIsCode(context) + "";//这是银行卡ID
		String params = getParams5(txn_amt, pay_code, bank_card_ed, id_card_ed,
				name_ed, mobile_ed);
		URL myURL = new URL(URL_BAOFOO_GATEWAY);
		HttpURLConnection con = (HttpURLConnection) myURL.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setUseCaches(false);
		con.setConnectTimeout(30000);
		con.connect();

		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(params);
		out.flush();
		out.close();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				con.getInputStream()));

		String s = reader.readLine();
		reader.close();
		con.disconnect();
		JSONObject ret = new JSONObject(s);
		String code = ret.getString("retCode");
		String retmsg = ret.getString("retMsg");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (URL_OK.equals(code)) {
			orderNo = ret.getString("tradeNo");
			msg = "";
		} else {
			msg = retmsg;
		}
		Map<String, String> map = new HashMap<String, String>();
		// {"tradeNo":"201506040110000400478535","retMsg":"","retCode":"0000"}
		map.put("orderNo", orderNo);
		map.put("msg", msg);
		list.add(map);
		return list;
	}

	private String getParams5(String txn_amt, String pay_code, String acc_no,
			String id_card, String id_holder, String mobile) throws Exception {
		// String returnUrl =
		// //"http://tgw.baofoo.com/testPage/server/4.0/ok/1";
		// returnUrl = URLEncoder.encode(returnUrl,"utf-8"); // 服务器通知地址 ,需URL编码
		StringBuilder s = new StringBuilder("");
		s.append("&txn_amt=").append(txn_amt);
		s.append("&pay_code=").append(pay_code);
		s.append("&acc_no=").append(acc_no);
		s.append("&id_card=").append(id_card);
		s.append("&id_holder=").append(URLEncoder.encode(id_holder, "utf-8"));
		s.append("&mobile=").append(mobile);
		return s.toString();
	}
}
