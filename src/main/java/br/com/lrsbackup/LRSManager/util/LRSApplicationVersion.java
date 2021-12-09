package br.com.lrsbackup.LRSManager.util;

public class LRSApplicationVersion {

	private String applicationName = new String();
	private String serviceName = new String();
	private String serviceVersion = new String();
	
	public LRSApplicationVersion() {
		super();
		this.applicationName = "LRSBackup";
		this.serviceName = "Manager Service";
		this.serviceVersion = "v1.00.00.0";
		
	}
 

	public String getApplicationName() {
		return applicationName;
	}


	public String getServiceName() {
		return serviceName;
	}


	public String getServiceVersion() {
		return serviceVersion;
	}
	
	
	
	
}
