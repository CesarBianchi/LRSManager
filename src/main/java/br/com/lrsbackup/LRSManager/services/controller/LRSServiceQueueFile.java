package br.com.lrsbackup.LRSManager.services.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSParameterForm;
import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSQueueFileFilterOptions;
import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSQueueFileForm;
import br.com.lrsbackup.LRSManager.persistence.model.LRSOptionsFileStatus;
import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;
import br.com.lrsbackup.LRSManager.persistence.model.LRSQueueFile;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSQueueFileRepository;
import br.com.lrsbackup.LRSManager.services.model.LRSParameterServiceModel;
import br.com.lrsbackup.LRSManager.services.model.LRSQueueFileServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSRequestConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSRequestIDGenerator;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;


@RestController
public class LRSServiceQueueFile {

	@Autowired
	private LRSQueueFileRepository queueFileRepository;
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private LRSApplicationVersion appDetails = new LRSApplicationVersion();
	private LRSRequestConsoleOut requestConsoleOut = new LRSRequestConsoleOut();
	private HttpStatus finalHttpStatus;
	
	
	public LRSServiceQueueFile() {
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
	
	@RequestMapping(value = "/queue/get/byid", method = RequestMethod.GET)
	public ResponseEntity getbyid(Long id, HttpServletRequest request) {
		
		String cMsg = new String("");
		List<LRSQueueFile> aFiles = new ArrayList<>();
		
		
		setRespInfoInitialData(request);
		if (id == null)  {
			finalHttpStatus = HttpStatus.BAD_REQUEST;
			cMsg = "The parameter 'id' is mandatory! Please, check it and try again!";
		} else {
			aFiles = queueFileRepository.findByid(id);	
			finalHttpStatus = HttpStatus.OK;
		}
		
		setRespInfoFootData(finalHttpStatus);
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,cMsg);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "/queue/get/byfullfilename", method = RequestMethod.GET)
	public ResponseEntity getbyfilename(String fullfilename, HttpServletRequest request) {
		
		String cMsg = new String("");
		List<LRSQueueFile> aFiles = new ArrayList<>();
		
		
		setRespInfoInitialData(request);
		if (fullfilename.isEmpty())  {
			finalHttpStatus = HttpStatus.BAD_REQUEST;
			cMsg = "The parameter 'fullfilename' is mandatory! Please, check it and try again!";
		} else {
			aFiles = queueFileRepository.findByoriginalfullname(fullfilename);	
			finalHttpStatus = HttpStatus.OK;
		}
		
		setRespInfoFootData(finalHttpStatus);
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,cMsg);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "/queue/get/readytoup", method = RequestMethod.GET)
	public ResponseEntity getready(HttpServletRequest request) {
		String cMsg = new String("These resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.READY_TO_UP.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,cMsg);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "/queue/get/uploading", method = RequestMethod.GET)
	public ResponseEntity getuploading(HttpServletRequest request) {
		String cMsg = new String("These resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.UPLOADING.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,cMsg);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "/queue/get/uploadedstandard", method = RequestMethod.GET)
	public ResponseEntity getupstd(HttpServletRequest request) {
		String cMsg = new String("These resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.UPLOADED_STANDARD.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,cMsg);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "/queue/get/convertingprocess", method = RequestMethod.GET)
	public ResponseEntity getconvprocess(HttpServletRequest request) {
		String cMsg = new String("These resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.CONVERTING_ARCHIEVE.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,cMsg);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "/queue/get/finished", method = RequestMethod.GET)
	public ResponseEntity getdone(HttpServletRequest request) {
		String cMsg = new String("These resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.DONE.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,"");
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "/queue/get/failed", method = RequestMethod.GET)
	public ResponseEntity getwitherr(HttpServletRequest request) {
		String cMsg = new String("These resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.ERROR.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,cMsg);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "/queue/get/byparams", method = RequestMethod.GET)
	public ResponseEntity getbyparams(HttpServletRequest request, @RequestBody LRSQueueFileFilterOptions queueOptions) {
		
		String cMsg = new String();
		String cloudProvider = queueOptions.getCloudProvider().trim();
		String status = queueOptions.getStatus().trim();
		LocalDateTime insStartDate = queueOptions.getInsertedStartDate();
		LocalDateTime insEndDate = queueOptions.getInsertedEndDate();
		List<LRSQueueFile> aFiles = new ArrayList<>();
		setRespInfoInitialData(request);
		
		if ((cloudProvider.isEmpty()) || (status.isEmpty()) || (insStartDate == null) || insEndDate == null) {
			finalHttpStatus = HttpStatus.BAD_REQUEST;
			cMsg = "The body fields 'cloudProvider', 'insertedStartDate', 'insertedEndDate' and 'status' are mandatory! Please, check it and try again!";
		} else {
			//Search based in body parameters
			//aFiles = queueFileRepository.findByFilter(cloudProvider,status,insStartDate,insEndDate);	
			aFiles = queueFileRepository.findByFilter(cloudProvider,status,insStartDate,insEndDate);	
			finalHttpStatus = HttpStatus.OK;
			cMsg = "";
		}
		setRespInfoFootData(finalHttpStatus);
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,cMsg);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}

	@RequestMapping(value ="/queue/insertnew", method = RequestMethod.POST)
    public ResponseEntity insertNew(HttpServletRequest request, @RequestBody LRSQueueFileForm queueForm) {
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel();
		List<LRSQueueFile> files = new ArrayList<>();
		
		setRespInfoInitialData(request);
		requestConsoleOut.println(request,queueForm);
		
		//Check if the file already added before
		LRSQueueFile fileExists =  queueFileRepository.findExists(queueForm.getCloudProvider(), queueForm.getOriginalfullname());	
		
		
		if (fileExists == null)  {
		
			//Insert new Parameter
			LRSQueueFile newFile = queueForm.convertToNew();
			queueFileRepository.save(newFile);
			
			//Create a Response
			files.add(newFile);
			String cMsg = "File ".concat(newFile.getOriginalfullname()).concat(" successfully added to queue list. It will upload in '".concat(newFile.getCloudProvider()).concat("' public cloud "));
			finalHttpStatus = HttpStatus.OK;
			setRespInfoFootData(finalHttpStatus);
						
			response = new LRSQueueFileServiceModel(responseInfo,files,cMsg);
			
			
		} else {	
			//Just create a response using a stored parameter data
			files.add(fileExists);
			String cMsg = "The file ".concat(fileExists.getOriginalfullname()).concat(" already exists in Queue List to will upload in ".concat(fileExists.getCloudProvider()).concat(" public cloud"));
			
			finalHttpStatus = HttpStatus.CONFLICT;
			setRespInfoFootData(finalHttpStatus);
			
			response = new LRSQueueFileServiceModel(responseInfo,files,cMsg);
			
			
		}		
		requestConsoleOut.println(request,response);
			
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }
}
