package br.com.lrsbackup.LRSManager.services.model;

import java.util.ArrayList;
import java.util.List;

import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;

public class LRSParameterServiceModel {

	public LRSResponseInfo responseInfo = new LRSResponseInfo();
	public List<LRSParameter> parameters = new ArrayList<>();
	public LRSResponseMessages messages = new LRSResponseMessages();
	
	
	public LRSParameterServiceModel() {
		super();
	}
	
	public LRSParameterServiceModel(LRSResponseInfo pInfo, List<LRSParameter> pParams, LRSResponseMessages pMessages) {			
		this.responseInfo = pInfo;
		this.parameters = pParams;
		this.messages = pMessages;
	}
	
}
