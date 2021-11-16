package br.com.lrsbackup.LRSManager.services.model;

import java.util.ArrayList;
import java.util.List;

import br.com.lrsbackup.LRSManager.persistence.model.LRSProtectedDir;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessage;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;

public class LRSProtectedDirServiceModel {
	public LRSResponseInfo responseInfo = new LRSResponseInfo();
	public List<LRSProtectedDir> directories = new ArrayList<>();
	public LRSResponseMessages messages = new LRSResponseMessages();
	
	public LRSProtectedDirServiceModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LRSProtectedDirServiceModel(LRSResponseInfo pInfo, List<LRSProtectedDir> dirs, LRSResponseMessages pMessages) {	
		this.responseInfo = pInfo;
		this.directories = dirs;
		this.messages = pMessages;	
	}
	
}
