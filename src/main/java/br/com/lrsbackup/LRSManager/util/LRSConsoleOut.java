package br.com.lrsbackup.LRSManager.util;

import com.google.gson.Gson;

public class LRSConsoleOut {

	private Gson gson = new Gson();
	
	public LRSConsoleOut() {
		super();
	}

	public LRSConsoleOut(String msg) {
		printMsg(msg);
	}
	
	public LRSConsoleOut(Object obj) {
		printMsg(gson.toJson(obj));
	}
		
	private void printMsg(String msg) {
		System.out.println(java.time.LocalTime.now().toString().substring(0,12).concat(" [APP] ").concat(msg) );
	}
	
}
