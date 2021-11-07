package br.com.lrsbackup.LRSManager.util;

public class LRSResponseMessage {
	private String message = new String();
	
	public LRSResponseMessage() {
		super();
	}

	public LRSResponseMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
