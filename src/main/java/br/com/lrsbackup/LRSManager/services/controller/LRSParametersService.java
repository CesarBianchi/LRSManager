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
import br.com.lrsbackup.LRSManager.persistence.model.LRSOptionsCloudProvider;
import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSParameterRepository;
import br.com.lrsbackup.LRSManager.services.model.LRSCloudEnabledServiceModel;
import br.com.lrsbackup.LRSManager.services.model.LRSParameterServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSRequestIDGenerator;


@RestController
public class LRSParametersService {

	@Autowired
	private LRSParameterRepository parameterRepository;
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private LRSApplicationVersion appDetails = new LRSApplicationVersion();
	private HttpStatus finalHttpStatus;
	private String requestID = new String();
	
	
	public LRSParametersService() {
		super();
		this.responseInfo.setAppName(appDetails.getApplicationName());
		this.responseInfo.setServiceName(appDetails.getServiceName());
		this.responseInfo.setServiceVersion(appDetails.getServiceVersion());
	}
	
	@RequestMapping(value = "/parameters/getall", method = RequestMethod.GET)
    public ResponseEntity getAll(HttpServletRequest request) {
				
		setRespInfoInitialData(request);
		
		List<LRSParameter> parameters = parameterRepository.findAll();
		LRSParameterServiceModel response = new LRSParameterServiceModel(responseInfo,parameters,"");
		
				
		finalHttpStatus = HttpStatus.OK;
		requestConsoleOut(request,response);
		setRespInfoFootData(finalHttpStatus);

		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	@RequestMapping(value ="/parameters/getbyname", method = RequestMethod.GET)
    public ResponseEntity getbyname(String name, HttpServletRequest request) {

		setRespInfoInitialData(request);
		
		LRSParameter param = parameterRepository.findByname(name);
		List<LRSParameter> parameters = new ArrayList<>();
		parameters.add(param);
		LRSParameterServiceModel response = new LRSParameterServiceModel(responseInfo,parameters,"");
		
		finalHttpStatus = HttpStatus.OK;
		requestConsoleOut(request,response);
		setRespInfoFootData(finalHttpStatus);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="/parameters/getbyid", method = RequestMethod.GET)
    public ResponseEntity getbyid(Long id, HttpServletRequest request) {

		setRespInfoInitialData(request);
		
		LRSParameter param = parameterRepository.findByid(id);
		List<LRSParameter> parameters = new ArrayList<>();
		parameters.add(param);
		LRSParameterServiceModel response = new LRSParameterServiceModel(responseInfo,parameters,"");

		finalHttpStatus = HttpStatus.OK;
		requestConsoleOut(request,response);
		setRespInfoFootData(finalHttpStatus);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="/parameters/insertnew", method = RequestMethod.POST)
    public ResponseEntity insertNew(HttpServletRequest request, @RequestBody LRSParameterForm parameter) {
		LRSParameterServiceModel response = new LRSParameterServiceModel();
		
		setRespInfoInitialData(request);
		requestConsoleOut(request,parameter);
		
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
			finalHttpStatus = HttpStatus.OK;
			
		} else {	
			//Just create a response using a stored parameter data
			List<LRSParameter> parameters = new ArrayList<>();
			parameters.add(paramExists);
			String cMsg = "Parameter ".concat(parameter.getName()).concat(" already exists in database. Transaction was not commited");
			response = new LRSParameterServiceModel(responseInfo,parameters,cMsg);
			
			finalHttpStatus = HttpStatus.CONFLICT;
		}		
		requestConsoleOut(request,response);
		setRespInfoFootData(finalHttpStatus);	
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	public ResponseEntity updateValue(HttpServletRequest request, @RequestBody LRSParameterForm parameter) {
		LRSParameterServiceModel response;
		
		setRespInfoInitialData(request);

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
			
			finalHttpStatus = HttpStatus.OK;
			
		} else {
			
			//Create a Response
			List<LRSParameter> parameters = new ArrayList<>();
			parameters.add(param);
			String cMsg = "Parameter ".concat(parameter.getName()).concat(" was not found. Transaction was not commited");
			response = new LRSParameterServiceModel(responseInfo,parameters,cMsg);
			
			finalHttpStatus = HttpStatus.CONFLICT;
		}
		
		requestConsoleOut(request,response);
		setRespInfoFootData(finalHttpStatus);
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="/parameters/delete", method = RequestMethod.DELETE)
    public ResponseEntity delete(String name, HttpServletRequest request) {
		LRSParameterServiceModel response;
		
		setRespInfoInitialData(request);
		
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

			finalHttpStatus = HttpStatus.OK;
			
		} else {
			
			//Create a Response
			List<LRSParameter> parameters = new ArrayList<>();
			parameters.add(param);
			String cMsg = "Parameter ".concat(name).concat(" was not found. Transaction was not commited");
			response = new LRSParameterServiceModel(responseInfo,parameters,cMsg);
			finalHttpStatus = HttpStatus.CONFLICT;
		}

		requestConsoleOut(request,response);
		setRespInfoFootData(finalHttpStatus);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }

	@RequestMapping(value = "/configs/awsisenabled", method = RequestMethod.GET)
    public ResponseEntity awsisenabled(HttpServletRequest request) {
		LRSCloudEnabledServiceModel response = new LRSCloudEnabledServiceModel();
		
		setRespInfoInitialData(request);
		
		LRSParameter parameter = this.getParameterCloudProviderEnabled(LRSOptionsCloudProvider.AWS);
		if (parameter != null) {
			response = new LRSCloudEnabledServiceModel(responseInfo,parameter.getValue());
		} else {
			response = new LRSCloudEnabledServiceModel(responseInfo,"false");
		}

		finalHttpStatus = HttpStatus.OK;
		requestConsoleOut(request,response);
		setRespInfoFootData(finalHttpStatus);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	@RequestMapping(value = "/configs/azureisenabled", method = RequestMethod.GET)
    public ResponseEntity azureisenabled(HttpServletRequest request) {
		LRSCloudEnabledServiceModel response = new LRSCloudEnabledServiceModel();
		
		setRespInfoInitialData(request);
		
		LRSParameter parameter = this.getParameterCloudProviderEnabled(LRSOptionsCloudProvider.AZURE);
		if (parameter != null) {
			response = new LRSCloudEnabledServiceModel(responseInfo,parameter.getValue());
		} else {
			response = new LRSCloudEnabledServiceModel(responseInfo,"false");
		}

		finalHttpStatus = HttpStatus.OK;
		requestConsoleOut(request,response);
		setRespInfoFootData(finalHttpStatus);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	@RequestMapping(value = "/configs/oracleisenabled", method = RequestMethod.GET)
    public ResponseEntity oracleisenabled(HttpServletRequest request) {
		LRSCloudEnabledServiceModel response = new LRSCloudEnabledServiceModel();
		
		setRespInfoInitialData(request);
		
		LRSParameter parameter = this.getParameterCloudProviderEnabled(LRSOptionsCloudProvider.ORACLE);
		if (parameter != null) {
			response = new LRSCloudEnabledServiceModel(responseInfo,parameter.getValue());
		} else {
			response = new LRSCloudEnabledServiceModel(responseInfo,"false");
		}

		finalHttpStatus = HttpStatus.OK;
		requestConsoleOut(request,response);
		setRespInfoFootData(finalHttpStatus);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	
	@RequestMapping(value = "/configs/getCloudCredentials", method = RequestMethod.GET)
    public ResponseEntity getCloudCredentials(HttpServletRequest request, String cloudProviderName) {
		LRSParameterServiceModel response = new LRSParameterServiceModel();
		String cCloudUserParamName = new String();
		String cCloudKeyParamName = new String();
		List<LRSParameter> parameters = new ArrayList<>();
		
		if (cloudProviderName != null) {
		
			setRespInfoInitialData(request);
			
			if (cloudProviderName.toUpperCase().trim().equals(LRSOptionsCloudProvider.AWS.toString())) {
				cCloudUserParamName = "UserCloudAWS";
				cCloudKeyParamName = "KeyCloudAWS";
			} else if ((cloudProviderName.toUpperCase().trim().equals(LRSOptionsCloudProvider.AZURE.toString()))) {
				cCloudUserParamName = "UserCloudAzure";
				cCloudKeyParamName = "KeyCloudAzure";
			} else if ((cloudProviderName.toUpperCase().trim().equals(LRSOptionsCloudProvider.ORACLE.toString())))  {
				cCloudUserParamName = "UserCloudOracle";
				cCloudKeyParamName = "KeyCloudOracle";
			}
			
			//Search UserParam
			LRSParameter paramUser = parameterRepository.findByname(cCloudUserParamName);
			LRSParameter paramKey = parameterRepository.findByname(cCloudKeyParamName);
			parameters.add(paramUser);
			parameters.add(paramKey);
			
			response = new LRSParameterServiceModel(responseInfo,parameters,"");
			
	
			finalHttpStatus = HttpStatus.OK;
			requestConsoleOut(request,response);
			setRespInfoFootData(finalHttpStatus);
		} else {
			finalHttpStatus = HttpStatus.BAD_REQUEST;
		}
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	
	
	private LRSParameter getParameterCloudProviderEnabled(LRSOptionsCloudProvider cloudProvider) {
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
	
	private void requestConsoleOut(HttpServletRequest request, Object response) {
		new LRSConsoleOut("Resource invoked: ".concat(request.getRequestURI()));
		new LRSConsoleOut(response);
		
		return;
	}
	
}
