package br.com.lrsbackup.LRSManager.services.model;

import java.util.ArrayList;
import java.util.List;

import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessage;

public class LRSParameterServiceModel {

	public LRSResponseInfo responseInfo = new LRSResponseInfo();
	public List<LRSParameter> parameters = new ArrayList<>();
	public List<LRSResponseMessage> messages = new ArrayList<>();
	
	
	public LRSParameterServiceModel() {
		super();
	}
	
	public LRSParameterServiceModel(LRSResponseInfo pInfo, List<LRSParameter> pParams, String msg) {	
		LRSResponseMessage message = new LRSResponseMessage();
		
		this.responseInfo = pInfo;
		this.parameters = pParams;
		message.setMessage(msg);
		this.messages.add(message);
	}
	
}
