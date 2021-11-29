package br.com.lrsbackup.LRSManager.services.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;


import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSParameterForm;
import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSParameterRepository;
import br.com.lrsbackup.LRSManager.services.model.LRSParameterServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;
import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSRequestConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSRequestIDGenerator;


@RestController
public class LRSServiceParameters {

	private LRSParameterRepository parameterRepository;
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private LRSApplicationVersion appDetails = new LRSApplicationVersion();
	private HttpStatus finalHttpStatus;
	private LRSRequestConsoleOut requestConsoleOut = new LRSRequestConsoleOut();
	
	public LRSServiceParameters() {
		super();
		this.responseInfo.setAppName(appDetails.getApplicationName());
		this.responseInfo.setServiceName(appDetails.getServiceName());
		this.responseInfo.setServiceVersion(appDetails.getServiceVersion());
	}
	
	@RequestMapping(value = "LRSManager/parameters/v1/getall", method = RequestMethod.GET)
    public ResponseEntity getAll(HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();		
		setRespInfoInitialData(request);
		finalHttpStatus = HttpStatus.OK;
		List<LRSParameter> parameters = parameterRepository.findAll();
		setRespInfoFootData(finalHttpStatus);
		messages.addMessage("Transaction Ok!");
		
		LRSParameterServiceModel response = new LRSParameterServiceModel(responseInfo,parameters,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	@RequestMapping(value ="LRSManager/parameters/v1/getbyname", method = RequestMethod.GET)
    public ResponseEntity getbyname(String name, HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();		
		
		setRespInfoInitialData(request);
		
		LRSParameter param = parameterRepository.findByname(name);
		List<LRSParameter> parameters = new ArrayList<>();
		parameters.add(param);
		
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		messages.addMessage("Transaction Ok!");
		
		LRSParameterServiceModel response = new LRSParameterServiceModel(responseInfo,parameters,messages);
		
		
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="LRSManager/parameters/v1/getbyid", method = RequestMethod.GET)
    public ResponseEntity getbyid(Long id, HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		setRespInfoInitialData(request);
		
		LRSParameter param = parameterRepository.findByid(id);
		List<LRSParameter> parameters = new ArrayList<>();
		parameters.add(param);
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		messages.addMessage("Transaction Ok!");
		
		LRSParameterServiceModel response = new LRSParameterServiceModel(responseInfo,parameters,messages);

		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="LRSManager/parameters/v1/insertnew", method = RequestMethod.POST)
    public ResponseEntity insertNew(HttpServletRequest request, @RequestBody LRSParameterForm parameter) {
		LRSParameterServiceModel response = new LRSParameterServiceModel();
		List<LRSParameter> parameters = new ArrayList<>();
		setRespInfoInitialData(request);
		requestConsoleOut.println(request,parameter);
		LRSResponseMessages messages = new LRSResponseMessages();
		
		//Check if the name was before used
		LRSParameter paramExists =  parameterRepository.findByname(parameter.getName()); 
		
		if (paramExists == null)  {
		
			//Insert new Parameter
			LRSParameter param = parameter.convertToParameter();
			parameterRepository.save(param);
			
			//Create a Response
			parameters.add(param);
			messages.addMessage("Parameter ".concat(param.getName()).concat(" successfully added"));
			
			finalHttpStatus = HttpStatus.OK;
			
		} else {	
			//Just create a response using a stored parameter data
			parameters.add(paramExists);
			messages.addMessage("Parameter ".concat(parameter.getName()).concat(" already exists in database. Transaction was not commited"));	
			finalHttpStatus = HttpStatus.CONFLICT;
		}		
		
		setRespInfoFootData(finalHttpStatus);
		response = new LRSParameterServiceModel(responseInfo,parameters,messages);
		requestConsoleOut.println(request,response);
			
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="LRSManager/parameters/v1/updatevalue", method = RequestMethod.PUT)
	public ResponseEntity updateValue(HttpServletRequest request, @RequestBody LRSParameterForm parameter) {
		LRSParameterServiceModel response;
		List<LRSParameter> parameters = new ArrayList<>();
		LRSResponseMessages messages = new LRSResponseMessages();	
		
		setRespInfoInitialData(request);

		//Search by Parameter
		LRSParameter param = parameterRepository.findByname(parameter.getName());
		
		//Parameter Found ?
		if (param != null) {
			param.setValue(parameter.getValue());
			parameterRepository.saveAndFlush(param);
			messages.addMessage("Parameter ".concat(param.getName()).concat(" was successfully updated"));
			finalHttpStatus = HttpStatus.OK;				
		} else {
			messages.addMessage("Parameter ".concat(parameter.getName()).concat(" was not found. Transaction was not commited"));
			finalHttpStatus = HttpStatus.CONFLICT;
		}
		
		//Create a Response
		setRespInfoFootData(finalHttpStatus);
		parameters.add(param);
		response = new LRSParameterServiceModel(responseInfo,parameters,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="LRSManager/parameters/v1/delete", method = RequestMethod.DELETE)
    public ResponseEntity delete(String name, HttpServletRequest request) {
		LRSParameterServiceModel response;
		setRespInfoInitialData(request);
		List<LRSParameter> parameters = new ArrayList<>();
		LRSResponseMessages messages = new LRSResponseMessages();		
		
		//Search by Parameter
		LRSParameter param = parameterRepository.findByname(name);
		
		//Parameter Found ?
		if (param != null) {
			parameterRepository.delete(param);
			messages.addMessage("Parameter ".concat(param.getName()).concat(" was successfully deleted"));
			finalHttpStatus = HttpStatus.OK;
		} else {			
			messages.addMessage("Parameter ".concat(name).concat(" was not found. Transaction was not commited"));
			finalHttpStatus = HttpStatus.CONFLICT;
		}
		
		//Create a Response
		parameters.add(param);
		setRespInfoFootData(finalHttpStatus);
		response = new LRSParameterServiceModel(responseInfo,parameters,messages);
		requestConsoleOut.println(request,response);
		
		
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
