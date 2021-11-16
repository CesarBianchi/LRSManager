package br.com.lrsbackup.LRSManager.services.model;

import java.util.ArrayList;
import java.util.List;

import br.com.lrsbackup.LRSManager.persistence.model.LRSQueueFile;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessage;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;

public class LRSQueueFileServiceModel {
	public LRSResponseInfo responseInfo = new LRSResponseInfo();
	public List<LRSQueueFile> directories = new ArrayList<>();
	public LRSResponseMessages messages = new LRSResponseMessages();
	
	public LRSQueueFileServiceModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public LRSQueueFileServiceModel(LRSResponseInfo pInfo, List<LRSQueueFile> pFiles, LRSResponseMessages pMessages) {	
		this.responseInfo = pInfo;
		this.directories = pFiles;
		this.messages = pMessages;
	}
}
