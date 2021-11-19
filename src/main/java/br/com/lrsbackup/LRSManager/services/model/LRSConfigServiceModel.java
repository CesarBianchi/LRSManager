package br.com.lrsbackup.LRSManager.services.model;

import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;

public class LRSConfigServiceModel {
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private String enabled = new String();
	private LRSResponseMessages messages = new LRSResponseMessages();
	
	
	public LRSConfigServiceModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LRSConfigServiceModel(LRSResponseInfo responseInfo, String enabled) {
		this.enabled = enabled;
		this.responseInfo = responseInfo;
	}

	public LRSConfigServiceModel(LRSResponseInfo responseInfo, String enabled, LRSResponseMessages pMsgs) {
		this.enabled = enabled;
		this.responseInfo = responseInfo;
		this.messages = pMsgs;
	}
	
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public LRSResponseInfo getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(LRSResponseInfo responseInfo) {
		this.responseInfo = responseInfo;
	}

	public LRSResponseMessages getMessages() {
		return messages;
	}

	public void setMessages(LRSResponseMessages messages) {
		this.messages = messages;
	}
	
}
