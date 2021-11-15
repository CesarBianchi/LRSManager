package br.com.lrsbackup.LRSManager.services.model;

import java.util.ArrayList;
import java.util.List;

import br.com.lrsbackup.LRSManager.persistence.model.LRSQueueFile;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessage;

public class LRSQueueFileServiceModel {
	public LRSResponseInfo responseInfo = new LRSResponseInfo();
	public List<LRSQueueFile> files = new ArrayList<>();
	public List<LRSResponseMessage> messages = new ArrayList<>();
	
	public LRSQueueFileServiceModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public LRSQueueFileServiceModel(LRSResponseInfo pInfo, List<LRSQueueFile> pFiles, String msg) {	
		LRSResponseMessage message = new LRSResponseMessage();
		
		this.responseInfo = pInfo;
		this.files = pFiles;
		message.setMessage(msg);
		this.messages.add(message);
	}
}
