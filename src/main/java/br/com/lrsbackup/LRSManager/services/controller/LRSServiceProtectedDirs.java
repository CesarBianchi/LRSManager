package br.com.lrsbackup.LRSManager.services.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSParameterForm;
import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSProtectedDirForm;
import br.com.lrsbackup.LRSManager.persistence.model.LRSOptionsCloudProvider;
import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;
import br.com.lrsbackup.LRSManager.persistence.model.LRSProtectedDir;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSParameterRepository;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSProtectedDirRepository;
import br.com.lrsbackup.LRSManager.services.model.LRSParameterServiceModel;
import br.com.lrsbackup.LRSManager.services.model.LRSProtectedDirServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSRequestConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSRequestIDGenerator;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessage;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;

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
	
	
	@RequestMapping(value = "LRSManager/protecteddirs/v1/getall", method = RequestMethod.GET)
	public ResponseEntity getall(HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();
		

		
		setRespInfoInitialData(request);
		
		List<LRSProtectedDir> dirs = protectedDirRepository.findAll();	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		messages.addMessage("Transaction Ok!");
		
		
		LRSProtectedDirServiceModel response = new LRSProtectedDirServiceModel(responseInfo,dirs,messages);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "LRSManager/protecteddirs/v1/get", method = RequestMethod.GET)
	public ResponseEntity get(HttpServletRequest request, @RequestBody LRSProtectedDirForm pDir) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		List<LRSProtectedDir> dirs = new ArrayList<>();
		
		setRespInfoInitialData(request);
		
		LRSProtectedDir protectedDir = protectedDirRepository.findBydirname(pDir.getOriginalPath());
		
		if (protectedDir != null) { 
			dirs.add(protectedDir);
			finalHttpStatus = HttpStatus.OK;
			messages.addMessage("Transaction Ok!");
		} else {
			finalHttpStatus = HttpStatus.CONFLICT;
			messages.addMessage("OriginalFileName not found!");
		}
			
		setRespInfoFootData(finalHttpStatus);
		
		LRSProtectedDirServiceModel response = new LRSProtectedDirServiceModel(responseInfo,dirs,messages);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value ="LRSManager/protecteddirs/v1/new", method = RequestMethod.POST)
    public ResponseEntity insertNew(HttpServletRequest request, @RequestBody LRSProtectedDirForm pDir) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		LRSProtectedDirServiceModel response = new LRSProtectedDirServiceModel();
		List<LRSProtectedDir> dirs = new ArrayList<>();
		boolean lOk = true;
				
		setRespInfoInitialData(request);
		requestConsoleOut.println(request,pDir);
		
		//Check if the original path was before used
		LRSProtectedDir dirExists =  protectedDirRepository.findBydirname(pDir.getOriginalPath()); 
		
		if (dirExists == null)  {
		
			//check if mandatory fields  isn't empty
			if (pDir.getName().trim().isEmpty()) {
				messages.addMessage("The 'name' field is mandatory. Please, check it and try again");
				lOk = false;
				
			} else if (pDir.getOriginalPath().trim().isEmpty()) {
				messages.addMessage("The 'origin' Path field is mandatory. Please, check it and try again");
				lOk = false;

			} /*else if (this.awsCloudIsActive() && pDir.getPathaws().trim().isEmpty()) {
				messages.addMessage("The AWS Cloud is Active in this environment, so 'pathaws' field is mandatory. Please, check it and try again");
				lOk = false;

			} else if (this.azureCloudIsActive() && pDir.getPathazure().trim().isEmpty()) {
				messages.addMessage("The AZURE Cloud is Active in this environment, so 'pathazure' field is mandatory. Please, check it and try again");
				lOk = false;

			} else if (this.oracleCloudIsActive() && pDir.getPathoracle().trim().isEmpty()) {
				messages.addMessage("The ORACLE Cloud is Active in this environment, so 'pathoracle' field is mandatory. Please, check it and try again");
				lOk = false;

			}	*/	
				
			if (!lOk) { 
				finalHttpStatus = HttpStatus.BAD_REQUEST;
				setRespInfoFootData(finalHttpStatus);
			} else {	

				//Insert new Protected Dir
				finalHttpStatus = HttpStatus.OK;
				setRespInfoFootData(finalHttpStatus);

				LRSProtectedDir newDir = pDir.convertToProtectedDir();
				protectedDirRepository.save(newDir);
			
				//Create a Response
				dirs.add(newDir);
				messages.addMessage("Protected Directory '".concat(newDir.getOriginPath()).concat("' successfully mapped"));
				finalHttpStatus = HttpStatus.OK;
				setRespInfoFootData(finalHttpStatus);
			}			
		} else {	
			
			//Just create a response using a stored parameter data
			dirs.add(pDir.convertToProtectedDir());
			messages.addMessage("Protected Directory '".concat(pDir.getOriginalPath()).concat("' already mapped in database. Transaction was not commited"));
			
			finalHttpStatus = HttpStatus.CONFLICT;
			setRespInfoFootData(finalHttpStatus);
			
		}
		response = new LRSProtectedDirServiceModel(responseInfo,dirs,messages);
		requestConsoleOut.println(request,response);
			
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="LRSManager/protecteddirs/v1/update", method = RequestMethod.PUT)
    public ResponseEntity update(HttpServletRequest request, @RequestBody LRSProtectedDirForm pDir) {
		LRSResponseMessages messages = new LRSResponseMessages();
		LRSProtectedDirServiceModel response = new LRSProtectedDirServiceModel();
		List<LRSProtectedDir> dirs = new ArrayList<>();
		boolean lOk = true;
		
		setRespInfoInitialData(request);
		requestConsoleOut.println(request,pDir);
		
		//Check if the directory exists
		LRSProtectedDir dirExists =  protectedDirRepository.findBydirname(pDir.getOriginalPath()); 
		
		if (dirExists == null)  {
			messages.addMessage("The Original Path '".concat(pDir.getOriginalPath()).concat("' not found. Please, check it and try again"));
			lOk = false;
		} else {	
			
			//check if mandatory fields  isn't empty
			if (pDir.getName().trim().isEmpty()) {
				messages.addMessage("The 'name' field is mandatory. Please, check it and try again");
				lOk = false;
				
			} else if (pDir.getOriginalPath().trim().isEmpty()) {
				messages.addMessage("The 'origin' Path field is mandatory. Please, check it and try again");
				lOk = false;

			} /*else if (this.awsCloudIsActive() && pDir.getPathaws().trim().isEmpty()) {
				messages.addMessage("The AWS Cloud is Active in this environment, so 'pathaws' field is mandatory. Please, check it and try again");
				lOk = false;

			} else if (this.azureCloudIsActive() && pDir.getPathazure().trim().isEmpty()) {
				messages.addMessage("The AZURE Cloud is Active in this environment, so 'pathazure' field is mandatory. Please, check it and try again");
				lOk = false;

			} else if (this.oracleCloudIsActive() && pDir.getPathoracle().trim().isEmpty()) {
				messages.addMessage("The ORACLE Cloud is Active in this environment, so 'pathoracle' field is mandatory. Please, check it and try again");
				lOk = false;

			}	*/	
				
			if (!lOk) { 
				finalHttpStatus = HttpStatus.BAD_REQUEST;
				setRespInfoFootData(finalHttpStatus);
			} else {	

				//Update Protected Dir
				LRSProtectedDir newDirDefines = pDir.convertToProtectedDir();
				
				//Just update the cloud destination paths and the protected dir name.
				dirExists.setName(newDirDefines.getName());
				dirExists.setContent(newDirDefines.getContent());
				dirExists.setDestinationPath_AWS(newDirDefines.getDestinationPath_AWS());
				dirExists.setDestinationPath_Azure(newDirDefines.getDestinationPath_Azure());
				dirExists.setDestinationPath_Oracle(newDirDefines.getDestinationPath_Oracle());
				
				protectedDirRepository.saveAndFlush(dirExists);
			
				finalHttpStatus = HttpStatus.OK;
				setRespInfoFootData(finalHttpStatus);
				
				//Create a Response
				dirs.add(dirExists);
				messages.addMessage("Protected Directory '".concat(dirExists.getOriginPath()).concat("' successfully updated"));
				finalHttpStatus = HttpStatus.OK;
				setRespInfoFootData(finalHttpStatus);
			}	
			
		}		
		response = new LRSProtectedDirServiceModel(responseInfo,dirs,messages);
		requestConsoleOut.println(request,response);
			
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
	
	@RequestMapping(value ="LRSManager/protecteddirs/v1/delete", method = RequestMethod.DELETE)
    public ResponseEntity delete(HttpServletRequest request, @RequestBody LRSProtectedDirForm pDir) {
		LRSResponseMessages messages = new LRSResponseMessages();
		LRSProtectedDirServiceModel response = new LRSProtectedDirServiceModel();
		List<LRSProtectedDir> dirs = new ArrayList<>();
		boolean lOk = true;
		
		setRespInfoInitialData(request);
		requestConsoleOut.println(request,pDir);
		
		//Check if the directory exists
		LRSProtectedDir dirExists =  protectedDirRepository.findBydirname(pDir.getOriginalPath()); 
		
		if (dirExists == null)  {
			messages.addMessage("The Original Path '".concat(pDir.getOriginalPath()).concat("' not found. Please, check it and try again"));
			lOk = false;
		} else {	
			
			//check if mandatory fields  isn't empty
			if (pDir.getName().trim().isEmpty()) {
				messages.addMessage("The 'name' field is mandatory. Please, check it and try again");
				lOk = false;
				
			} else if (pDir.getOriginalPath().trim().isEmpty()) {
				messages.addMessage("The 'origin' Path field is mandatory. Please, check it and try again");
				lOk = false;

			} /*else if (this.awsCloudIsActive() && pDir.getPathaws().trim().isEmpty()) {
				messages.addMessage("The AWS Cloud is Active in this environment, so 'pathaws' field is mandatory. Please, check it and try again");
				lOk = false;

			} else if (this.azureCloudIsActive() && pDir.getPathazure().trim().isEmpty()) {
				messages.addMessage("The AZURE Cloud is Active in this environment, so 'pathazure' field is mandatory. Please, check it and try again");
				lOk = false;

			} else if (this.oracleCloudIsActive() && pDir.getPathoracle().trim().isEmpty()) {
				messages.addMessage("The ORACLE Cloud is Active in this environment, so 'pathoracle' field is mandatory. Please, check it and try again");
				lOk = false;

			}	*/	
				
			if (!lOk) { 
				finalHttpStatus = HttpStatus.BAD_REQUEST;
				setRespInfoFootData(finalHttpStatus);
			} else {	

				//Delete Protected Dir
				protectedDirRepository.delete(dirExists);
			
				finalHttpStatus = HttpStatus.OK;
				setRespInfoFootData(finalHttpStatus);
				
				//Create a Response
				dirs.add(dirExists);
				messages.addMessage("Protected Directory '".concat(dirExists.getOriginPath()).concat("' successfully deleted"));
				finalHttpStatus = HttpStatus.OK;
				setRespInfoFootData(finalHttpStatus);
			}	
			
		}		
		response = new LRSProtectedDirServiceModel(responseInfo,dirs,messages);
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
	
	private boolean awsCloudIsActive() {
		boolean lReturn = false;

		LRSParameter param = new LRSServiceConfigs().getParameterCloudProviderEnabledName(LRSOptionsCloudProvider.AWS);
	
		if (param != null) {
			lReturn = param.convertToBoolean();
		}
		
		return lReturn;
		
	}
	
	private boolean azureCloudIsActive() {
		boolean lReturn = false;
		
		LRSServiceConfigs managerConfigs = new LRSServiceConfigs();
		LRSParameter param = managerConfigs.getParameterCloudProviderEnabledName(LRSOptionsCloudProvider.AZURE);
		
		if (param != null) {
			lReturn = param.convertToBoolean();
		}
		
		return lReturn;
		
	}
	
	private boolean oracleCloudIsActive() {
		boolean lReturn = false;
		
		LRSServiceConfigs managerConfigs = new LRSServiceConfigs();
		LRSParameter param = managerConfigs.getParameterCloudProviderEnabledName(LRSOptionsCloudProvider.ORACLE);
		
		if (param != null) {
			lReturn = param.convertToBoolean();
		}
		
		return lReturn;
		
	}
		
}
