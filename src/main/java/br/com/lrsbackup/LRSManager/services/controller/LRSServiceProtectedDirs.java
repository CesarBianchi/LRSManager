package br.com.lrsbackup.LRSManager.services.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lrsbackup.LRSManager.persistence.model.LRSProtectedDir;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSProtectedDirRepository;
import br.com.lrsbackup.LRSManager.services.model.LRSProtectedDirServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSRequestConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSRequestIDGenerator;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import java.util.List;

@RestController
public class LRSServiceProtectedDirs {

	
	@Autowired
	private LRSProtectedDirRepository protectedDirRepository;
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private LRSApplicationVersion appDetails = new LRSApplicationVersion();
	private LRSRequestConsoleOut requestConsoleOut = new LRSRequestConsoleOut();
	private HttpStatus finalHttpStatus;
	
	public LRSServiceProtectedDirs() {
		super();
		this.responseInfo.setAppName(appDetails.getApplicationName());
		this.responseInfo.setServiceName(appDetails.getServiceName());
		this.responseInfo.setServiceVersion(appDetails.getServiceVersion());
	}
	
	
	@RequestMapping(value = "/protecteddirs/getall", method = RequestMethod.GET)
	public ResponseEntity getall(HttpServletRequest request) {
		
		
		setRespInfoInitialData(request);
		
		List<LRSProtectedDir> dirs = protectedDirRepository.findAll();	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSProtectedDirServiceModel response = new LRSProtectedDirServiceModel(responseInfo,dirs,"");
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	
	
	
	private void setRespInfoInitialData(HttpServletRequest request) {
		this.responseInfo.setRequestTime(java.time.LocalTime.now().toString().substring(0,12));
		this.responseInfo.setResourceName(request.getRequestURI());
		this.responseInfo.setClientIP(request.getRemoteAddr());
		
		return;
	}
	
	private void setRespInfoFootData(HttpStatus httpStat) {
		this.responseInfo.setHttpStatus(finalHttpStatus);
		this.responseInfo.setRequestID(new LRSRequestIDGenerator().getNewRequestID());
		this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));
	}
}
