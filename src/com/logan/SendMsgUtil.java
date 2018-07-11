package com.logan;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Test to send message util
 * 
 * @author RongHeMaRongHe
 *
 */
public class SendMsgUtil {

	/**
	 * 中國
	 * @param phones
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	private static String sendMsg(String phones) throws HttpException, IOException {

		String url = "http://gbk.sms.webchinese.cn";
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
		// params.put("Uid", "LoganMaMMS");
		// params.put("Key", "d41d8cd98f00b204e980");
		// params.put("smsMob", phones);
		// params.put("smsText", "验证码:"+generateRandomCode()+",如非本人操作，请勿回复！【Logan
		// Ma】.");

		NameValuePair[] data = { new NameValuePair("Uid", "LoganMaMMS"),
				new NameValuePair("Key", "testloganmaronghe123456"), new NameValuePair("smsMob", phones),
				new NameValuePair("smsText", "验证码:" + generateRandomCode() + ",如非本人操作，请勿回复！【Logan Ma】."), };

		// String result = HttpRequestUtil.postRequest(url, params);
		postMethod.setRequestBody(data);

		int num = httpClient.executeMethod(postMethod);
		System.out.println(num);
		Header[] headers = postMethod.getRequestHeaders();
		int statusCode = postMethod.getStatusCode();
		System.out.println("statusCode :" + statusCode);
		for (Header header : headers) {
			System.out.println(header.toString());
		}
		String result = new String(postMethod.getResponseBodyAsString().getBytes("gbk"));
		return result;
	}

	public static String generateRandomCode() {
		String code = "";
		for (int i = 0; i < 6; i++) {
			code = code + (int) (Math.random() * 9);
		}
		return code;
	}

	public static void main(String[] args) throws HttpException, IOException {
		String code = generateRandomCode();
		System.out.println("您将收到的验证码为：" + code);
		String url = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
		String accountSID = "7e6db5f9010649bc8db75e551378d604";
		String templateid = "379232854";
		String authToken = "8c24aa2272164890896c93889311b63f";
		String phones = "18742530580";
		MiaoDi miaodi = new MiaoDi(url, accountSID, templateid, authToken, phones, code);
		System.out.println(MiaodiSendMsg(miaodi));
	}


	/**
	 * MiaoDi Send Verification Code
	 * @param miaodi
	 * @return
	 */
	public static String MiaodiSendMsg(MiaoDi miaodi) {
		// request params
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-type","application/x-www-form-urlencoded");

		Map<String, String> params = new HashMap<String, String>();
		params.put("accountSid", miaodi.getAccountSID());
		params.put("to", miaodi.getPhones());// splited by ,

		// params.put("smsContent", content);
		params.put("templateid", miaodi.getTemplateid());
		params.put("param", miaodi.getCode());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf.format(new Date());
		params.put("timestamp", date);
		params.put("sig", AppMD5Util.getMD5(miaodi.getAccountSID() + miaodi.getAuthToken() + date));// 32位

		return HttpRequestUtil.postRequest(miaodi.getUrl(), headers, params);
	}
}
