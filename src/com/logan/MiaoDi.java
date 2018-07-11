package com.logan;

/**
 * MiaoDi 
 * @author RongHeMaRongHe
 *
 */
public class MiaoDi {

	private String url;
	private String accountSID;
	private String templateid ;
	private String authToken;
	private String phones;
	private String code;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAccountSID() {
		return accountSID;
	}
	public void setAccountSID(String accountSID) {
		this.accountSID = accountSID;
	}
	public String getTemplateid() {
		return templateid;
	}
	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Miao Di
	 * @param url
	 * 			 请求地址
	 * @param accountSID
	 * 			 开发者主账号ID（ACCOUNT SID）
	 * @param templateid
	 * 			 短信模板ID
	 * @param authToken
	 * 			 AUTH TOKEN
	 * @param phones 
	 * 			 phone number. if you want to pass multiple number, then split by ','
	 * @param code
	 * 			your verification code  
	 */
	public MiaoDi(String url, String accountSID, String templateid, String authToken, String phones, String code) {
		super();
		this.url = url;
		this.accountSID = accountSID;
		this.templateid = templateid;
		this.authToken = authToken;
		this.phones = phones;
		this.code = code;
	}

}
