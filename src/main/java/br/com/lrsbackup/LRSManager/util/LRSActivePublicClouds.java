package br.com.lrsbackup.LRSManager.util;

import org.springframework.web.client.RestTemplate;

import br.com.lrsbackup.LRSManager.services.model.LRSConfigServiceModel;

public class LRSActivePublicClouds {
	private boolean awsIsOn = false;
	private boolean azureIsOn = false;
	private boolean oracleIsOn = false;
	private String cBaseURI = new LRSManagerAddress().getLRSManagerURI();
	
	public LRSActivePublicClouds() {
		//super();
		
		RestTemplate restTemplate = new RestTemplate();
		LRSConfigServiceModel awsIsOn = restTemplate.getForObject(cBaseURI.concat("/configs/v1/awsisenabled"), LRSConfigServiceModel.class); 
		LRSConfigServiceModel azureIsOn = restTemplate.getForObject(cBaseURI.concat("/configs/v1/azureisenabled"), LRSConfigServiceModel.class); 
		LRSConfigServiceModel oracleIsOn = restTemplate.getForObject(cBaseURI.concat("/configs/v1/oracleisenabled"), LRSConfigServiceModel.class); 
		
		this.awsIsOn = awsIsOn.getEnabled().toUpperCase().trim().equals("TRUE");
		this.azureIsOn = azureIsOn.getEnabled().toUpperCase().trim().equals("TRUE");
		this.oracleIsOn = oracleIsOn.getEnabled().toUpperCase().trim().equals("TRUE");
	}

	public boolean isAwsOn() {
		return awsIsOn;
	}

	public boolean isAzureOn() {
		return azureIsOn;
	}

	public boolean isOracleOn() {
		return oracleIsOn;
	}

	public String getcBaseURI() {
		return cBaseURI;
	}
	
	
}
