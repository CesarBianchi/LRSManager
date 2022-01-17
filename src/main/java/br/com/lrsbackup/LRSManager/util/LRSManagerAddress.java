package br.com.lrsbackup.LRSManager.util;

import java.util.Properties;

public class LRSManagerAddress {
	
	private String LRSManagerURI = new String();

	public LRSManagerAddress() {
		super();
		this.setLRSManagerURI();
	}

	private void setLRSManagerURI() {
	
		boolean inEclipse = !(System.getenv("eclipse42") == null);
		
		if (inEclipse) {
			this.LRSManagerURI = "http://127.0.0.1:8080/LRSManager";
		} else {
			this.LRSManagerURI = "http://192.168.1.12:6001/LRSManager"; //TODO - Not HARDCODED
		}
		
		
	}

	public String getLRSManagerURI() {
		return LRSManagerURI;
	}
	
	
	
}
