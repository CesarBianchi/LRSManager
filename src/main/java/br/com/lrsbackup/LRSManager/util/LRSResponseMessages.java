package br.com.lrsbackup.LRSManager.util;

import java.util.ArrayList;
import java.util.List;

public class LRSResponseMessages {
	private List<LRSResponseMessage> messages = new ArrayList<>();

	public LRSResponseMessages() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LRSResponseMessages(List<LRSResponseMessage> messages) {
		this.messages = messages;
	}

	public List<LRSResponseMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<LRSResponseMessage> messages) {
		this.messages = messages;
	}	
	
	public void addMessage(String msg) {
		this.messages.add(new LRSResponseMessage(msg));
		
		return;
	}	
	
}
