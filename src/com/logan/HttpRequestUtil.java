package com.logan;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * HttpRequest util class
 * 
 * @author RongHeMaRongHe
 * @version v1.0
 *
 */
public class HttpRequestUtil {

	/**
	 * Http client Post method
	 * 
	 * @param url
	 * @param paramters
	 * @return String
	 * @author RongHeMaRongHe
	 * 
	 */
	public static String postRequest(String url, Map<String, String> headers, Map<String, String> paramters) {
		// create http client instance
		HttpClient httpClient = new HttpClient();
		// create post method instance
		PostMethod postMethod = new PostMethod(url);
		// set headers
		// postMethod.setRequestHeader("Connection", "close");
		if (headers.size() > 0)
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
				postMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		// postMethod.setRequestHeader("Content-type","application/x-www-form-urlencoded");

		// add parameters
		if (paramters.size() > 0)
			for (Map.Entry<String, String> entry : paramters.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
				postMethod.addParameter(entry.getKey(), entry.getValue());
			}

		// using the system default strategy, request three times.
		httpClient.getParams().setBooleanParameter("http.portocol.expect-continue", false);

		String result = null;

		try {

			httpClient.executeMethod(postMethod);

			result = new String(postMethod.getResponseBodyAsString());

		} catch (HttpException e) {
			System.out.println("«ÎºÏ≤È ‰»ÎµƒURL!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Internet Error!");
			e.printStackTrace();
		} finally {
			// release connection
			postMethod.releaseConnection();

			// close http client
			if (httpClient != null) {
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				httpClient = null;
			}
		}
		return result;
	}

	
	/**
	 *  Send get request
	 * @param url
	 * @param headers
	 * @param parameters
	 * @return
	 */
	public static String getRequest(String url, Map<String, String> headers, Map<String, String> parameters) {

		// create http client
		HttpClient httpClient = new HttpClient();

		// join paramters
		String paramStr = "";
		// for(String key : parameters.keySet()) {
		// paramStr = paramStr + "&" + key + "=" + parameters.get(key);
		// }
		if (parameters != null) {
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				paramStr = paramStr + "&" + entry.getKey() + "=" + entry.getValue();
			}
			// remove the first &
			paramStr = paramStr.substring(1);
		}

		GetMethod method = new GetMethod(url + "?" + paramStr);

		if (headers.size() > 0)
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
				method.setRequestHeader(entry.getKey(), entry.getValue());
			}

		String result = null;
		try {

			httpClient.executeMethod(method);

			result = method.getResponseBodyAsString();

		} catch (HttpException e) {
			System.out.println("Please check your URL!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Internet Error!");
			e.printStackTrace();
		} finally {
			// release connection
			method.releaseConnection();

			if (httpClient != null) {
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				httpClient = null;
			}

		}
		return result;
	}

}
