package br.com.lrsbackup.LRSManager.services.model;

import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSUpdEnginePathForm;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;

public class LRSConfigServiceModelEng {
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private LRSUpdEnginePathForm uploadEngineAddress = new LRSUpdEnginePathForm();
	private LRSResponseMessages messages = new LRSResponseMessages();

	
	public LRSConfigServiceModelEng() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LRSConfigServiceModelEng(LRSResponseInfo responseInfo, String enabled) {
		this.responseInfo = responseInfo;
	}

	public LRSConfigServiceModelEng(LRSResponseInfo responseInfo, LRSResponseMessages pMsgs) {
		this.responseInfo = responseInfo;
		this.messages = pMsgs;
	}
	
	public LRSConfigServiceModelEng(LRSResponseInfo responseInfo, LRSResponseMessages messages, LRSUpdEnginePathForm lRSUpdateEngineAddress) {
		super();
		this.responseInfo = responseInfo;
		this.messages = messages;
		this.uploadEngineAddress = lRSUpdateEngineAddress;
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

	public LRSUpdEnginePathForm getLRSUpdateEngineAddress() {
		return uploadEngineAddress;
	}

	public void setLRSUpdateEngineAddress(LRSUpdEnginePathForm lRSUpdateEngineAddress) {
		uploadEngineAddress = lRSUpdateEngineAddress;
	}
	
	
	
	
}
