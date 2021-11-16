package br.com.lrsbackup.LRSManager.services.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lrsbackup.LRSManager.persistence.model.LRSOptionsCloudProvider;
import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSParameterRepository;
import br.com.lrsbackup.LRSManager.services.model.LRSConfigServiceModel;
import br.com.lrsbackup.LRSManager.services.model.LRSParameterServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSRequestConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSRequestIDGenerator;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessage;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;

@RestController
public class LRSServiceConfigs {

	@Autowired
	private LRSParameterRepository parameterRepository;
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private LRSApplicationVersion appDetails = new LRSApplicationVersion();
	private HttpStatus finalHttpStatus;
	private LRSRequestConsoleOut requestConsoleOut = new LRSRequestConsoleOut();
	
	public LRSServiceConfigs() {
		super();
		this.responseInfo.setAppName(appDetails.getApplicationName());
		this.responseInfo.setServiceName(appDetails.getServiceName());
		this.responseInfo.setServiceVersion(appDetails.getServiceVersion());
	}
	
	@RequestMapping(value = "LRSManager/configs/v1/awsisenabled", method = RequestMethod.GET)
    public ResponseEntity awsisenabled(HttpServletRequest request) {
		LRSConfigServiceModel response = new LRSConfigServiceModel();
		
		setRespInfoInitialData(request);
		
		LRSParameter parameter = this.getParameterCloudProviderEnabledName(LRSOptionsCloudProvider.AWS);
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		if (parameter != null) {
			response = new LRSConfigServiceModel(responseInfo,parameter.getValue());
		} else {
			response = new LRSConfigServiceModel(responseInfo,"false");
		}
		
		requestConsoleOut.println(request,response);
		
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	@RequestMapping(value = "LRSManager/configs/v1/azureisenabled", method = RequestMethod.GET)
    public ResponseEntity azureisenabled(HttpServletRequest request) {
		LRSConfigServiceModel response = new LRSConfigServiceModel();
		
		setRespInfoInitialData(request);
		
		LRSParameter parameter = this.getParameterCloudProviderEnabledName(LRSOptionsCloudProvider.AZURE);
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		if (parameter != null) {
			response = new LRSConfigServiceModel(responseInfo,parameter.getValue());
		} else {
			response = new LRSConfigServiceModel(responseInfo,"false");
		}
		
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	@RequestMapping(value = "LRSManager/configs/v1/oracleisenabled", method = RequestMethod.GET)
    public ResponseEntity oracleisenabled(HttpServletRequest request) {
		LRSConfigServiceModel response = new LRSConfigServiceModel();
		
		setRespInfoInitialData(request);
		
		LRSParameter parameter = this.getParameterCloudProviderEnabledName(LRSOptionsCloudProvider.ORACLE);
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		if (parameter != null) {
			response = new LRSConfigServiceModel(responseInfo,parameter.getValue());
		} else {
			response = new LRSConfigServiceModel(responseInfo,"false");
		}
		
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
		
	@RequestMapping(value = "LRSManager/configs/v1/getcloudcredentials", method = RequestMethod.GET)
    public ResponseEntity getCloudCredentials(HttpServletRequest request, String cloudProviderName) {
		LRSParameterServiceModel response = new LRSParameterServiceModel();
		String cCloudUserParamName = new String();
		String cCloudKeyParamName = new String();
		List<LRSParameter> parameters = new ArrayList<>();
		LRSResponseMessages messages = new LRSResponseMessages();	
		
		if (cloudProviderName != null) {
		
			setRespInfoInitialData(request);
			
			if (cloudProviderName.toUpperCase().trim().equals(LRSOptionsCloudProvider.AWS.toString())) {
				cCloudUserParamName = "UserCloudAWS";
				cCloudKeyParamName = "KeyCloudAWS";
				finalHttpStatus = HttpStatus.OK;
				messages.addMessage("Transaction OK!");
			} else if ((cloudProviderName.toUpperCase().trim().equals(LRSOptionsCloudProvider.AZURE.toString()))) {
				cCloudUserParamName = "UserCloudAzure";
				cCloudKeyParamName = "KeyCloudAzure";
				finalHttpStatus = HttpStatus.OK;
				messages.addMessage("Transaction OK!");
			} else if ((cloudProviderName.toUpperCase().trim().equals(LRSOptionsCloudProvider.ORACLE.toString())))  {
				cCloudUserParamName = "UserCloudOracle";
				cCloudKeyParamName = "KeyCloudOracle";
				finalHttpStatus = HttpStatus.OK;
				messages.addMessage("Transaction OK!");
			} else {
				finalHttpStatus = HttpStatus.CONFLICT;
				messages.addMessage("cloudProviderName not found in config list!");
			}
			
			//Search UserParam
			LRSParameter paramUser = parameterRepository.findByname(cCloudUserParamName);
			LRSParameter paramKey = parameterRepository.findByname(cCloudKeyParamName);
			parameters.add(paramUser);
			parameters.add(paramKey);		
			setRespInfoFootData(finalHttpStatus);
			response = new LRSParameterServiceModel(responseInfo,parameters,messages);

			requestConsoleOut.println(request,response);
			
		} else {
			finalHttpStatus = HttpStatus.PRECONDITION_REQUIRED;
			requestConsoleOut.println(request,response);
		}
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	public LRSParameter getParameterCloudProviderEnabledName(LRSOptionsCloudProvider cloudProvider) {
		String cCloudProviderParamName = new String();
		
		if (cloudProvider == LRSOptionsCloudProvider.AWS) {
			cCloudProviderParamName = "CloudAWSEnabled";
		} else if (cloudProvider == LRSOptionsCloudProvider.AZURE)  {
			cCloudProviderParamName = "CloudAzureEnabled";
		} else if (cloudProvider == LRSOptionsCloudProvider.ORACLE)  {
			cCloudProviderParamName = "CloudOracleEnabled";
		} else {
			cCloudProviderParamName = "";
		}
			
		LRSParameter parameter = parameterRepository.findByname(cCloudProviderParamName);
		
		
		return parameter;
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
