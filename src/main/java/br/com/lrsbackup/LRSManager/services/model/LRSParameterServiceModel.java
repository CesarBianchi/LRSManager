package br.com.lrsbackup.LRSManager.services.model;

import java.util.ArrayList;
import java.util.List;

import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;

public class LRSParameterServiceModel {

	public String appName = new String();
	public String serviceName = new String();
	public String resourceName = new String();
	public String responseTime = new String();
	public List<LRSParameter> params = new ArrayList<>();
	
	public LRSParameterServiceModel(String resource, List<LRSParameter> pParams ) {
		super();
		this.appName = "LRSBackup";
		this.serviceName = "Manager Service";
		this.resourceName = resource;
		this.responseTime = java.time.LocalTime.now().toString().substring(0,12);
		this.params = pParams;
	}
	
	
	
}
