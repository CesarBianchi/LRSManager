package br.com.lrsbackup.LRSManager.services.model;

import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;

public class LRSCloudEnabledServiceModel {
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private String enabled = new String();
	
	
	
	public LRSCloudEnabledServiceModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LRSCloudEnabledServiceModel(LRSResponseInfo responseInfo, String enabled) {
		this.enabled = enabled;
		this.responseInfo = responseInfo;
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
	
	
	
}
