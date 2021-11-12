package br.com.lrsbackup.LRSManager.util;

import javax.servlet.http.HttpServletRequest;

public class LRSRequestConsoleOut {

	
	
	public LRSRequestConsoleOut() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LRSRequestConsoleOut(HttpServletRequest request, Object response) {
		this.println(request, response);
	}
	
	public void println(HttpServletRequest request, Object response) {
		new LRSConsoleOut("Resource invoked: ".concat(request.getRequestURI()));
		new LRSConsoleOut(response);
		
		return;
	}

	
}
