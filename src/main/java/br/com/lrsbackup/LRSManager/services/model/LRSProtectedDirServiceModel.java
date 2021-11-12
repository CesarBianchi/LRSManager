package br.com.lrsbackup.LRSManager.services.model;

import java.util.ArrayList;
import java.util.List;

import br.com.lrsbackup.LRSManager.persistence.model.LRSProtectedDir;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessage;

public class LRSProtectedDirServiceModel {
	public LRSResponseInfo responseInfo = new LRSResponseInfo();
	public List<LRSProtectedDir> directories = new ArrayList<>();
	public List<LRSResponseMessage> messages = new ArrayList<>();
	
	public LRSProtectedDirServiceModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LRSProtectedDirServiceModel(LRSResponseInfo pInfo, List<LRSProtectedDir> dirs, String msg) {	
		LRSResponseMessage message = new LRSResponseMessage();
		
		this.responseInfo = pInfo;
		this.directories = dirs;
		message.setMessage(msg);
		this.messages.add(message);
	}
	
}
