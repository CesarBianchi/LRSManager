package br.com.lrsbackup.LRSManager.services.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.lrsbackup.LRSManager.enums.LRSOptionsCloudProvider;
import br.com.lrsbackup.LRSManager.enums.LRSOptionsInventoryRequest;
import br.com.lrsbackup.LRSManager.persistence.model.LRSCloudInvetoryRequest;
import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSCloudInventoryRequestRepository;
import br.com.lrsbackup.LRSManager.services.model.LRSCloudInventoryRequestServiceModel;
import br.com.lrsbackup.LRSManager.services.model.LRSConfigServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSRequestConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSRequestIDGenerator;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;

@RestController
public class LRSServiceCloudInventory {

	@Autowired
	private LRSCloudInventoryRequestRepository cloudInvReqRepository;
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private LRSApplicationVersion appDetails = new LRSApplicationVersion();
	private HttpStatus finalHttpStatus;
	private LRSRequestConsoleOut requestConsoleOut = new LRSRequestConsoleOut();
	
	public LRSServiceCloudInventory() {
		super();
		this.responseInfo.setAppName(appDetails.getApplicationName());
		this.responseInfo.setServiceName(appDetails.getServiceName());
		this.responseInfo.setServiceVersion(appDetails.getServiceVersion());
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
	
	private LRSCloudInvetoryRequest newRequisition(String cloudProvider) {
		
		LRSCloudInvetoryRequest newRequest = new LRSCloudInvetoryRequest();
		newRequest.setCloudProvider(cloudProvider);
		newRequest.setProtocolID(this.responseInfo.getRequestID());
		newRequest.setRequestedDate(LocalDateTime.now());
		newRequest.setStatus(LRSOptionsInventoryRequest.PENDING.toString().trim());
		
		return newRequest;
		
	}
	
	@RequestMapping(value = "LRSManager/cloudinventory/v1/requestpubliccloudinventory", method = RequestMethod.POST)
    public ResponseEntity requestpubliccloudinventory(String cloudProvider, HttpServletRequest request) {
		boolean refreshInventory = false;
		LRSResponseMessages messages = new LRSResponseMessages();
		LRSCloudInventoryRequestServiceModel response = new LRSCloudInventoryRequestServiceModel();
		LRSCloudInvetoryRequest newRequest = new LRSCloudInvetoryRequest();
		
		List<LRSCloudInvetoryRequest> lastRequests = new ArrayList<>();
		LRSCloudInvetoryRequest lastRequest = new LRSCloudInvetoryRequest();
		
		String cloudAzure = LRSOptionsCloudProvider.AZURE.toString().toUpperCase();
		String cloudAWS = LRSOptionsCloudProvider.AWS.toString().toUpperCase();
		String cloudOracle = LRSOptionsCloudProvider.ORACLE.toString().toUpperCase();		
		String pendingStatus = LRSOptionsInventoryRequest.PENDING.toString().trim();
		
		setRespInfoInitialData(request);
		
		if (cloudProvider.trim().isEmpty()) {
			messages.addMessage("ERROR: The cloud provider name is empty. Please, use AWS, Azure or Oracle options");
			finalHttpStatus = HttpStatus.BAD_REQUEST;
		} else {
			
			//Check if cloudProvider param is valid
			if ((cloudProvider.trim().toUpperCase().equals(cloudAWS)) ||
				(cloudProvider.trim().toUpperCase().equals(cloudAzure))  ||
				(cloudProvider.trim().toUpperCase().equals(cloudOracle))) {
				
				//Verify if last request cloud inventory is "live" (< 12h)
				Duration minimalHour = Duration.ofHours(12);
				LocalDateTime minimalDateTime = LocalDateTime.now().minus(minimalHour);
				
				lastRequests = cloudInvReqRepository.findByLastRequest(cloudProvider,pendingStatus);
				
				if (lastRequests.size() > 0) {
					lastRequest = lastRequests.get(0);								
					if (lastRequest == null ) {
						refreshInventory = true;
					} else if (lastRequest.getRequestedDate().isBefore(minimalDateTime)) {
						refreshInventory = true;
					} 
				} else {
					refreshInventory = true;
				}
					
				if (refreshInventory == true) {
					
					newRequest = this.newRequisition(cloudProvider);
					cloudInvReqRepository.save(newRequest);

					messages.addMessage("A new Cloud Inventory request was successfully added. Please, can back again in 5 minutes with ProtocolID data to get it! ");
					finalHttpStatus = HttpStatus.OK;
				} else {
					messages.addMessage("Already a valid request pending. This Protocol ID is: ".concat(lastRequest.getProtocolID()));
					messages.addMessage("WARNING: The last cloud inventory data to ".concat(cloudProvider).concat(" public cloud was requested less than 12 hours. For cost savings reassons, a new request is not allowed."));
					
					newRequest = lastRequest;
					finalHttpStatus = HttpStatus.OK;
				}
				
			} else {
				messages.addMessage("ERROR: The cloud provider name is unknow. Please, certify if you are using AWS, Azure or Oracle");
				finalHttpStatus = HttpStatus.BAD_REQUEST;
			}
		}
		
		//Create a valid response
		response = new LRSCloudInventoryRequestServiceModel(responseInfo,newRequest,messages);
		setRespInfoFootData(finalHttpStatus);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	@RequestMapping(value = "LRSManager/cloudinventory/v1/updaterequisitionstatus", method = RequestMethod.PUT)
    public ResponseEntity updaterequisitionstatus(String cloudProvider, String protocolId, String newStatus, HttpServletRequest request) {
		boolean refreshInventory = false;
		LRSResponseMessages messages = new LRSResponseMessages();
		LRSCloudInventoryRequestServiceModel response = new LRSCloudInventoryRequestServiceModel();
		LRSCloudInvetoryRequest updRequest = new LRSCloudInvetoryRequest();
		
		String cloudAzure = LRSOptionsCloudProvider.AZURE.toString().toUpperCase();
		String cloudAWS = LRSOptionsCloudProvider.AWS.toString().toUpperCase();
		String cloudOracle = LRSOptionsCloudProvider.ORACLE.toString().toUpperCase();		
		
		String pendingStatus = LRSOptionsInventoryRequest.PENDING.toString().trim();
		String doneStatus = LRSOptionsInventoryRequest.DONE.toString().trim();
		String errorStatus = LRSOptionsInventoryRequest.ERROR.toString().trim();
		
		setRespInfoInitialData(request);
		
		if (cloudProvider.trim().isEmpty()) {
			messages.addMessage("ERROR: The cloudProvider param is empty. Please, use AWS, Azure or Oracle options");
			finalHttpStatus = HttpStatus.BAD_REQUEST;
		} else if (protocolId.trim().isEmpty()) {
			messages.addMessage("ERROR: The protocolId param  is empty. Please check it and try again ");
			finalHttpStatus = HttpStatus.BAD_REQUEST;
		} else {
			
			//Check if cloudProvider param is valid
			if ((cloudProvider.trim().toUpperCase().equals(cloudAWS)) ||
				(cloudProvider.trim().toUpperCase().equals(cloudAzure))  ||
				(cloudProvider.trim().toUpperCase().equals(cloudOracle))) {
				
				//Check if status param is valid
				if ((newStatus.trim().toUpperCase().equals(pendingStatus)) ||
					(newStatus.trim().toUpperCase().equals(doneStatus)) ||
					(newStatus.trim().toUpperCase().equals(errorStatus))) {
				
					//Verify if request exists
					updRequest = cloudInvReqRepository.findByprotocolID(protocolId.trim());
						
					if (updRequest != null) {
						//Verify if cloud provider param is the same of database record.
						if (updRequest.getCloudProvider().trim().toUpperCase().equals(cloudProvider.trim().toUpperCase())){ 
						
							//Update just if current status is different of new Status
							if (!updRequest.getStatus().equals(newStatus)) {
								updRequest.setStatus(newStatus);
								cloudInvReqRepository.saveAndFlush(updRequest);
							}
							
							messages.addMessage("The protocolID ".concat(protocolId).concat(" successfully updated to ".concat(newStatus)));
							finalHttpStatus = HttpStatus.OK;
						} else {
							messages.addMessage("The protocolID ".concat(protocolId).concat(" isn't valid. Please, check this parameter and try again"));
							finalHttpStatus = HttpStatus.BAD_REQUEST;
						}
					} else {
						messages.addMessage("The protocolID ".concat(protocolId).concat(" isn't valid. Please, check this parameter and try again"));
						finalHttpStatus = HttpStatus.BAD_REQUEST;
					}
				} else {
					messages.addMessage("ERROR: The newStatus param is unknow. Plese, certify if you are using PENDING, DONE or ERROR");
					finalHttpStatus = HttpStatus.BAD_REQUEST;
				}
			} else {
				messages.addMessage("ERROR: The cloud provider name is unknow. Plese, certify if you are using AWS, Azure or Oracle");
				finalHttpStatus = HttpStatus.BAD_REQUEST;
			}
		}
		
		//Create a valid response
		response = new LRSCloudInventoryRequestServiceModel(responseInfo,updRequest,messages);
		setRespInfoFootData(finalHttpStatus);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
    }
	
	
}
