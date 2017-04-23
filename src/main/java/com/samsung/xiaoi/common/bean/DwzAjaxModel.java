package com.samsung.xiaoi.common.bean;

public class DwzAjaxModel {
	
	private int statusCode;
	private String message;
	private String navTabId;
	private String rel;
	private String callbackType;
	private String forwardUrl;
	
	private DwzAjaxModel(int statusCode, String message){
		this.statusCode = statusCode;
		this.message = message;
	}
	
	/*private DwzAjaxModel(int statusCode, String message, String navTabId, String rel, String callbackType, String forwardUrl){
		this(statusCode, message);
		this.navTabId = navTabId;
		this.rel = rel;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
	}*/
	
	public static DwzAjaxModel success(){
		return new DwzAjaxModel(200, "操作成功");
	}
	
	public static DwzAjaxModel succesForward(String forwardUrl){
		return success().callbackType("forward").forwardUrl(forwardUrl);
	}
	
	public static DwzAjaxModel successNavTab(String navTabId){
		return success().callbackType("closeCurrent").navTabId(navTabId);
	}
	
	/**
	 * 
	 * @param rel
	 * @return
	 */
	public static DwzAjaxModel successRel(String rel){
		return success().rel(rel);
	}
	
	/**
	 * 操作失败
	 * @return
	 */
	public static DwzAjaxModel failure(){
		return new DwzAjaxModel(300, "操作失败");
	}
	
	/**
	 * session会话超时
	 * @return
	 */
	public static DwzAjaxModel sessionTimeout(){
		return new DwzAjaxModel(301, "会话超时");
	}
	
	public DwzAjaxModel navTabId(String navTabId){
		this.navTabId = navTabId;
		return this;
	}
	
	public DwzAjaxModel rel(String rel){
		this.rel = rel;
		return this;
	}
	
	public DwzAjaxModel callbackType(String callbackType){
		this.callbackType = callbackType;
		return this;
	}
	
	public DwzAjaxModel forwardUrl(String forwardUrl){
		this.forwardUrl = forwardUrl;
		return this;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public String getMessage() {
		return message;
	}
	public String getNavTabId() {
		return navTabId;
	}
	public String getRel() {
		return rel;
	}
	public String getCallbackType() {
		return callbackType;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	
}
