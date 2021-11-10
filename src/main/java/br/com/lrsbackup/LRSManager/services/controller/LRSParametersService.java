package br.com.lrsbackup.LRSManager.services.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.NO_RESPONSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSParameterForm;
import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSParameterRepository;
import br.com.lrsbackup.LRSManager.services.model.LRSParameterServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;


@RestController
public class LRSParametersService {

	@Autowired
	private LRSParameterRepository parameterRepository;
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private LRSApplicationVersion appDetails = new LRSApplicationVersion();
	private HttpStatus finalHttpStatus;
	
	
	public LRSParametersService() {
		super();
		this.responseInfo.setAppName(appDetails.getApplicationName());
		this.responseInfo.setServiceName(appDetails.getServiceName());
		this.responseInfo.setServiceVersion(appDetails.getServiceVersion());
	}
	
	@RequestMapping(value = "/parameters/getall", method = RequestMethod.GET)
    public ResponseEntity getAll(HttpServletRequest request) {
		
		
		this.responseInfo.setRequestTime(java.time.LocalTime.now().toString().substring(0,12));
		this.responseInfo.setResourceName(request.getRequestURI());
		this.responseInfo.setClientIPAddr(request.getRemoteAddr());
		
		List<LRSParameter> parameters = parameterRepository.findAll();
		
		this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));

		LRSParameterServiceModel response = new LRSParameterServiceModel(responseInfo,parameters,"");
		
		new LRSConsoleOut("Resource invoked: ".concat(request.getRequestURI()));
		new LRSConsoleOut(response);
				
		finalHttpStatus = HttpStatus.OK;
		this.responseInfo.setHttpStatus(finalHttpStatus);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	@RequestMapping(value ="/parameters/getbyname", method = RequestMethod.GET)
    public ResponseEntity getbyname(String name, HttpServletRequest request) {

		this.responseInfo.setRequestTime(java.time.LocalTime.now().toString().substring(0,12));
		this.responseInfo.setResourceName(request.getRequestURI());
		this.responseInfo.setClientIPAddr(request.getRemoteAddr());
		
		LRSParameter param = parameterRepository.findByname(name);
		List<LRSParameter> parameters = new ArrayList<>();
		parameters.add(param);
		LRSParameterServiceModel response = new LRSParameterServiceModel(responseInfo,parameters,"");
		
		this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));
		new LRSConsoleOut("Resource invoked: ".concat(request.getRequestURI()));
		new LRSConsoleOut(response);
		
		finalHttpStatus = HttpStatus.OK;
		this.responseInfo.setHttpStatus(finalHttpStatus);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="/parameters/getbyid", method = RequestMethod.GET)
    public ResponseEntity getbyid(Long id, HttpServletRequest request) {

		this.responseInfo.setRequestTime(java.time.LocalTime.now().toString().substring(0,12));
		this.responseInfo.setResourceName(request.getRequestURI());
		this.responseInfo.setClientIPAddr(request.getRemoteAddr());
		
		LRSParameter param = parameterRepository.findByid(id);
		List<LRSParameter> parameters = new ArrayList<>();
		parameters.add(param);
		LRSParameterServiceModel response = new LRSParameterServiceModel(responseInfo,parameters,"");

		this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));
		
		new LRSConsoleOut("Resource invoked: ".concat(request.getRequestURI()));
		new LRSConsoleOut(response);
		
		finalHttpStatus = HttpStatus.OK;
		this.responseInfo.setHttpStatus(finalHttpStatus);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="/parameters/insertnew", method = RequestMethod.POST)
    public ResponseEntity insertNew(HttpServletRequest request, @RequestBody LRSParameterForm parameter) {
		LRSParameterServiceModel response = new LRSParameterServiceModel();
		
		this.responseInfo.setRequestTime(java.time.LocalTime.now().toString().substring(0,12));
		this.responseInfo.setResourceName(request.getRequestURI());
		this.responseInfo.setClientIPAddr(request.getRemoteAddr());

		new LRSConsoleOut("Resource invoked: ".concat(request.getRequestURI()));
		new LRSConsoleOut("REQUEST");
		new LRSConsoleOut(parameter);
		
		//Check if the name was before used
		LRSParameter paramExists =  parameterRepository.findByname(parameter.getName()); 
		
		if (paramExists == null)  {
		
			//Insert new Parameter
			LRSParameter param = parameter.convertToParameter();
			parameterRepository.save(param);
			
			//Create a Response
			List<LRSParameter> parameters = new ArrayList<>();
			parameters.add(param);
			String cMsg = "Parameter ".concat(param.getName()).concat(" successfully added");
			
			response = new LRSParameterServiceModel(responseInfo,parameters,cMsg);
			
			this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));
			new LRSConsoleOut("RESPONSE");
			new LRSConsoleOut(response);
			
			finalHttpStatus = HttpStatus.OK;
			this.responseInfo.setHttpStatus(finalHttpStatus);
		} else {
		
			//Just create a response using a stored parameter data
			List<LRSParameter> parameters = new ArrayList<>();
			parameters.add(paramExists);
			String cMsg = "Parameter ".concat(parameter.getName()).concat(" already exists in database. Transaction was not commited");
			
			response = new LRSParameterServiceModel(responseInfo,parameters,cMsg);
			
			this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));
			new LRSConsoleOut("RESPONSE");
			new LRSConsoleOut(response);
			
			finalHttpStatus = HttpStatus.CONFLICT;
			this.responseInfo.setHttpStatus(finalHttpStatus);
			
		}				
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	public ResponseEntity updateValue(HttpServletRequest request, @RequestBody LRSParameterForm parameter) {
		LRSParameterServiceModel response;
		
		this.responseInfo.setRequestTime(java.time.LocalTime.now().toString().substring(0,12));
		this.responseInfo.setResourceName(request.getRequestURI());
		this.responseInfo.setClientIPAddr(request.getRemoteAddr());

		//Search by Parameter
		LRSParameter param = parameterRepository.findByname(parameter.getName());
		
		//Parameter Found ?
		if (param != null) {
		
			param.setValue(parameter.getValue());
			parameterRepository.saveAndFlush(param);
		
			//Create a Response
			List<LRSParameter> parameters = new ArrayList<>();
			parameters.add(param);
			String cMsg = "Parameter ".concat(param.getName()).concat(" was successfully updated");
			response = new LRSParameterServiceModel(responseInfo,parameters,cMsg);
		
			this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));
			new LRSConsoleOut("Resource invoked: ".concat(request.getRequestURI()));
			new LRSConsoleOut(response);
			
			finalHttpStatus = HttpStatus.OK;
			this.responseInfo.setHttpStatus(finalHttpStatus);
			
		} else {
			
			//Create a Response
			List<LRSParameter> parameters = new ArrayList<>();
			parameters.add(param);
			String cMsg = "Parameter ".concat(parameter.getName()).concat(" was not found. Transaction was not commited");
			response = new LRSParameterServiceModel(responseInfo,parameters,cMsg);
		
			this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));
			new LRSConsoleOut("Resource invoked: ".concat(request.getRequestURI()));
			new LRSConsoleOut(response);
			
			finalHttpStatus = HttpStatus.CONFLICT;
			this.responseInfo.setHttpStatus(finalHttpStatus);
		}
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="/parameters/delete", method = RequestMethod.DELETE)
    public ResponseEntity delete(String name, HttpServletRequest request) {
		LRSParameterServiceModel response;
		
		this.responseInfo.setRequestTime(java.time.LocalTime.now().toString().substring(0,12));
		this.responseInfo.setResourceName(request.getRequestURI());
		this.responseInfo.setClientIPAddr(request.getRemoteAddr());
		
		//Search by Parameter
		LRSParameter param = parameterRepository.findByname(name);
		
		//Parameter Found ?
		if (param != null) {
		
			parameterRepository.delete(param);
		
			//Create a Response
			List<LRSParameter> parameters = new ArrayList<>();
			parameters.add(param);
			String cMsg = "Parameter ".concat(param.getName()).concat(" was successfully deleted");
			response = new LRSParameterServiceModel(responseInfo,parameters,cMsg);
		
			this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));
			new LRSConsoleOut("Resource invoked: ".concat(request.getRequestURI()));
			new LRSConsoleOut(response);
			
			finalHttpStatus = HttpStatus.OK;
			this.responseInfo.setHttpStatus(finalHttpStatus);
			
		} else {
			
			//Create a Response
			List<LRSParameter> parameters = new ArrayList<>();
			parameters.add(param);
			String cMsg = "Parameter ".concat(name).concat(" was not found. Transaction was not commited");
			response = new LRSParameterServiceModel(responseInfo,parameters,cMsg);
		
			this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));
			new LRSConsoleOut("Resource invoked: ".concat(request.getRequestURI()));
			new LRSConsoleOut(response);
			
			finalHttpStatus = HttpStatus.CONFLICT;
			this.responseInfo.setHttpStatus(finalHttpStatus);
		}
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }


	
}
