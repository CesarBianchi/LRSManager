package br.com.lrsbackup.LRSManager.services.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.lrsbackup.LRSManager.enums.LRSOptionsFileStatus;
import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSQueueFileFilterOptions;
import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSQueueFileForm;
import br.com.lrsbackup.LRSManager.persistence.model.LRSQueueFile;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSQueueFileRepository;
import br.com.lrsbackup.LRSManager.services.model.LRSConfigServiceModelEng;
import br.com.lrsbackup.LRSManager.services.model.LRSParameterServiceModel;
import br.com.lrsbackup.LRSManager.services.model.LRSQueueFileServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSManagerAddress;
import br.com.lrsbackup.LRSManager.util.LRSRequestConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSRequestIDGenerator;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;
import br.com.lrsbackup.LRSManager.services.model.LRSUploadFileForm;
import br.com.lrsbackup.LRSManager.services.model.LRSUploadFileServiceModel;


@RestController	
public class LRSServiceQueueFile {

	@Autowired
	private LRSQueueFileRepository queueFileRepository;
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	private LRSApplicationVersion appDetails = new LRSApplicationVersion();
	private LRSRequestConsoleOut requestConsoleOut = new LRSRequestConsoleOut();
	private HttpStatus finalHttpStatus;
	private String cBaseURILRSManager = new LRSManagerAddress().getLRSManagerURI();
	private String cBaseURILRSUploadEngine = new String("");
	
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
	
	@RequestMapping(value = "LRSManager/queue/v1/getbyid", method = RequestMethod.GET)
	public ResponseEntity getbyid(Long id, HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();
		List<LRSQueueFile> aFiles = new ArrayList<>();
		
		
		setRespInfoInitialData(request);
		if (id == null)  {
			finalHttpStatus = HttpStatus.BAD_REQUEST;
			messages.addMessage("The parameter 'id' is mandatory! Please, check it and try again!");
		} else {
			aFiles = queueFileRepository.findByid(id);	
			
			if (aFiles.size() > 0) {
				finalHttpStatus = HttpStatus.OK;
				messages.addMessage("Transaction Ok!");
			} else {
				finalHttpStatus = HttpStatus.BAD_REQUEST;
				messages.addMessage("ID Not Found");
			}
		}
		
		setRespInfoFootData(finalHttpStatus);
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "LRSManager/queue/v1/getbyfullfilename", method = RequestMethod.GET)
	public ResponseEntity getbyfilename(String fullfilename, HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		List<LRSQueueFile> aFiles = new ArrayList<>();
		
		
		setRespInfoInitialData(request);
		if (fullfilename.isEmpty())  {
			finalHttpStatus = HttpStatus.BAD_REQUEST;
			messages.addMessage("The parameter 'fullfilename' is mandatory! Please, check it and try again!");
		} else {
			aFiles = queueFileRepository.findByoriginalfullname(fullfilename);	
			if (aFiles.size() > 0) {
				finalHttpStatus = HttpStatus.OK;
				messages.addMessage("Transaction Ok. File already exists in database!");
			} else {
				finalHttpStatus = HttpStatus.OK;
				messages.addMessage("File Not Found");
			}
		}
		
		setRespInfoFootData(finalHttpStatus);
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "LRSManager/queue/v1/getreadytoup", method = RequestMethod.GET)
	public ResponseEntity getready(HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();			
		messages.addMessage("WARNING!!! This resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.READY_TO_UP.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "LRSManager/queue/v1/getsomereadytoup", method = RequestMethod.GET)
	public ResponseEntity getsomeready(HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();			
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.queryFirst100findByStatus(LRSOptionsFileStatus.READY_TO_UP.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "LRSManager/queue/v1/getuploading", method = RequestMethod.GET)
	public ResponseEntity getuploading(HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		messages.addMessage("WARNING!!! This resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.UPLOADING.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "LRSManager/queue/v1/getuploadedstandard", method = RequestMethod.GET)
	public ResponseEntity getupstd(HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		messages.addMessage("WARNING!!! This resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.UPLOADED_STANDARD.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "LRSManager/queue/v1/getconvertingprocess", method = RequestMethod.GET)
	public ResponseEntity getconvprocess(HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		messages.addMessage("WARNING!!! This resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.CONVERTING_ARCHIEVE.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "LRSManager/queue/v1/getfinished", method = RequestMethod.GET)
	public ResponseEntity getdone(HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		messages.addMessage("WARNING!!! This resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.DONE.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "LRSManager/queue/v1/getfailed", method = RequestMethod.GET)
	public ResponseEntity getwitherr(HttpServletRequest request) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		messages.addMessage("WARNING!!! This resource can be slow and return many items! Please, use 'queue/get/byparams' resource");
		
		setRespInfoInitialData(request);
		
		List<LRSQueueFile> aFiles = queueFileRepository.findByStatus(LRSOptionsFileStatus.ERROR.toString());	
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}
	
	@RequestMapping(value = "LRSManager/queue/v1/getbyparams", method = RequestMethod.GET)
	public ResponseEntity getbyparams(HttpServletRequest request, @RequestBody LRSQueueFileFilterOptions queueOptions) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		String cloudProvider = queueOptions.getCloudProvider().trim();
		String status = queueOptions.getStatus().trim();
		LocalDateTime insStartDate = queueOptions.getInsertedStartDate();
		LocalDateTime insEndDate = queueOptions.getInsertedEndDate();
		List<LRSQueueFile> aFiles = new ArrayList<>();
		setRespInfoInitialData(request);
		
		if ((cloudProvider.isEmpty()) || (status.isEmpty()) || (insStartDate == null) || insEndDate == null) {
			finalHttpStatus = HttpStatus.BAD_REQUEST;
			messages.addMessage("The body fields 'cloudProvider', 'insertedStartDate', 'insertedEndDate' and 'status' are mandatory! Please, check it and try again!");
		} else {
			//Search based in body parameters
			//aFiles = queueFileRepository.findByFilter(cloudProvider,status,insStartDate,insEndDate);	
			aFiles = queueFileRepository.findByFilter(cloudProvider,status,insStartDate,insEndDate);	
			finalHttpStatus = HttpStatus.OK;
			messages.addMessage("Transaction OK!");
		}
		setRespInfoFootData(finalHttpStatus);
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel(responseInfo,aFiles,messages);
		requestConsoleOut.println(request,response);
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}

	@RequestMapping(value ="LRSManager/queue/v1/inserttolist", method = RequestMethod.POST)
    public ResponseEntity insertNew(HttpServletRequest request, @RequestBody LRSQueueFileForm queueForm) {
		boolean insert = false;
		boolean update = false;
		LRSResponseMessages messages = new LRSResponseMessages();	
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel();
		List<LRSQueueFile> files = new ArrayList<>();
		
		setRespInfoInitialData(request);
		requestConsoleOut.println(request,queueForm);
		
		//Check if the file already added before
		LRSQueueFile fileExists =  queueFileRepository.findExists(queueForm.getCloudProvider(), queueForm.getOriginalfullname());	
		
		//Check if the file was changed after the first sent. If yes, re-sent.
		if (fileExists == null) {
			insert = true;
		} else {
			if (!(fileExists.getCreationDateTime().toString().equals(queueForm.getCreationDateTime().toString())) || !(fileExists.getSize() == queueForm.getSize())) {
				
				if (!(fileExists.getStatus().equals(LRSOptionsFileStatus.READY_TO_UP.toString()))) {
					
					if (!(fileExists.getStatus().equals(LRSOptionsFileStatus.CONVERTING_ARCHIEVE.toString()))) {
					
						insert = true;
						update = true;
					}	
				}
			}	
		}	
		
		
		if (insert)  {
			LRSQueueFile newFile = new LRSQueueFile();
			
			if (update) {
				fileExists.setInsertedDate(LocalDateTime.now());
				fileExists.setProcessedDate(fileExists.getInsertedDate());
				fileExists.setStatus(LRSOptionsFileStatus.READY_TO_UP.toString());
				fileExists.setSize(queueForm.getSize());
				fileExists.setPercentUploaded(0);
				fileExists.setCreationDateTime(queueForm.getCreationDateTime());
				
				newFile = fileExists;
				queueFileRepository.saveAndFlush(fileExists);
			} else {
				//Insert new Parameter
				newFile = queueForm.convertToNew();
				queueFileRepository.save(newFile);	
			}
			
			
			//Create a Response
			files.add(newFile);
			messages.addMessage("THe file ".concat(newFile.getOriginalfullname()).concat(" successfully added to queue list. It will upload in '".concat(newFile.getCloudProvider()).concat("' public cloud soon as possible")));
			finalHttpStatus = HttpStatus.OK;			
		} else {	
			//Just create a response using a stored parameter data
			files.add(fileExists);
			messages.addMessage("The file ".concat(fileExists.getOriginalfullname()).concat(" already exists in Queue List to will upload in ".concat(fileExists.getCloudProvider()).concat(" public cloud")));
			finalHttpStatus = HttpStatus.CONFLICT;			
			
		}		
		
		setRespInfoFootData(finalHttpStatus);		
		response = new LRSQueueFileServiceModel(responseInfo,files,messages);
		requestConsoleOut.println(request,response);
			
		
		return ResponseEntity.status(finalHttpStatus).body(response);		
    }
	
	@RequestMapping(value ="LRSManager/queue/v1/removetolist", method = RequestMethod.DELETE)
    public ResponseEntity delete(HttpServletRequest request, @RequestBody LRSQueueFileForm queueForm) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel();
		List<LRSQueueFile> files = new ArrayList<>();
		
		setRespInfoInitialData(request);
		requestConsoleOut.println(request,queueForm);
		
		//Check if the file already added before
		LRSQueueFile fileExists =  queueFileRepository.findExists(queueForm.getCloudProvider(), queueForm.getOriginalfullname());	
		
		
		if (fileExists != null)  {
			
			//delete file to list
			queueFileRepository.delete(fileExists);
			messages.addMessage("File ".concat(fileExists.getOriginalfullname()).concat(" successfully deleted to queue list. It will not uploaded in '".concat(fileExists.getCloudProvider()).concat("' public cloud ")));
			finalHttpStatus = HttpStatus.OK;			
		} else {	
			//Just create a response using a stored parameter data
			fileExists = queueForm.convertToNew();
			messages.addMessage("The file ".concat(fileExists.getOriginalfullname()).concat(" does not found in Queue List to will upload in ".concat(fileExists.getCloudProvider()).concat(" public cloud")));
			finalHttpStatus = HttpStatus.CONFLICT;			
			
		}		
		
		//Create a Response
		files.add(fileExists);
		setRespInfoFootData(finalHttpStatus);		
		response = new LRSQueueFileServiceModel(responseInfo,files,messages);
		requestConsoleOut.println(request,response);
			
		
		return ResponseEntity.status(finalHttpStatus).body(response);		
    }
	
	@RequestMapping(value ="LRSManager/queue/v1/updatestatus", method = RequestMethod.PUT)
    public ResponseEntity updatestatus(HttpServletRequest request, @RequestBody LRSQueueFileForm queueForm) {
		LRSResponseMessages messages = new LRSResponseMessages();	
		LRSQueueFileServiceModel response = new LRSQueueFileServiceModel();
		List<LRSQueueFile> files = new ArrayList<>();
		
		setRespInfoInitialData(request);
		requestConsoleOut.println(request,queueForm);
		
		//Check if the file already added before
		LRSQueueFile fileExists =  queueFileRepository.findExists(queueForm.getCloudProvider(), queueForm.getOriginalfullname());	
		
		
		if (fileExists != null)  {
		
			//update status
			fileExists.setStatus(queueForm.getStatus());
			queueFileRepository.saveAndFlush(fileExists);
			
			
			messages.addMessage("The status of file ".concat(fileExists.getOriginalfullname()).concat(" successfully updated in queue list to '".concat(fileExists.getCloudProvider()).concat("' public cloud ")));
			finalHttpStatus = HttpStatus.OK;			
		} else {	
			fileExists = queueForm.convertToNew();
			messages.addMessage("The status of file ".concat(fileExists.getOriginalfullname()).concat(" does not found in Queue List to will upload in ".concat(fileExists.getCloudProvider()).concat(" public cloud")));
			finalHttpStatus = HttpStatus.CONFLICT;	
		}		
		
		//Create a Response
		files.add(fileExists);
		setRespInfoFootData(finalHttpStatus);		
		response = new LRSQueueFileServiceModel(responseInfo,files,messages);
		requestConsoleOut.println(request,response);
			
		
		return ResponseEntity.status(finalHttpStatus).body(response);		
    }
	
	@RequestMapping(value ="LRSManager/queue/v1/uploadpendings", method = RequestMethod.GET)
    public ResponseEntity uploadpendings(String caller, HttpServletRequest request) throws InterruptedException  {
		LRSUploadFileServiceModel response = new LRSUploadFileServiceModel();
		
		setRespInfoInitialData(request);
		requestConsoleOut.println(request,caller);
			
		this.newUploadFileThread(caller, request);
		
		finalHttpStatus = HttpStatus.OK;
		setRespInfoFootData(finalHttpStatus);
		requestConsoleOut.println(request,response);
			
		
		return ResponseEntity.status(finalHttpStatus).body(response);	
		
    }

	public void newUploadFileThread(String caller, HttpServletRequest request) {
        new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		startUploadProcess(caller,request);
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
            }
        }).start();
	}
	
	
	public void startUploadProcess(String caller, HttpServletRequest request) throws InterruptedException  {
		LRSUploadFileServiceModel response = new LRSUploadFileServiceModel();
		
		
		setRespInfoInitialData(request);
		requestConsoleOut.println(request,caller);
			
		//1* Verify if exists files pending to upload
		RestTemplate restTemplate = new RestTemplate();
		LRSQueueFileServiceModel filesPending = restTemplate.getForObject(cBaseURILRSManager.concat("/queue/v1/getsomereadytoup"), LRSQueueFileServiceModel.class);
		
		//2* Get the LRSUploadEngineAddress
		LRSConfigServiceModelEng engineAdd = this.getUploadEngineAddress();
		cBaseURILRSUploadEngine = engineAdd.getLRSUpdateEngineAddress().getFullAdress().concat("/LRSUploadEngine");
		
		
		if (filesPending != null) {			
			for (int nI = 0; nI < filesPending.directories.size(); nI++) {
				
				//3* Send the file to LRSUploadEngine
				LRSUploadFileForm fileToUpload = this.getFileToUpload(filesPending.directories.get(nI));
				
				try {
					response = restTemplate.postForObject(cBaseURILRSUploadEngine.concat("/upload/v1/uploadfile"), fileToUpload, LRSUploadFileServiceModel.class);
					finalHttpStatus = HttpStatus.OK;

				} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
					
					finalHttpStatus = httpClientOrServerExc.getStatusCode();
				    if(HttpStatus.BAD_REQUEST.equals(httpClientOrServerExc.getStatusCode())) {
				    	//TODO TODO
				    }
				    else {
				      //TODO TODO TODO
				    }
				}
				
				Thread.sleep(5000);
			}
		}
		
		//TODO TODO TODO - DONT RESPONSE TO AGENT THE SAME RESPONSE OF ENGINEUPLOAD - SECURITY REASONS! SOME DAY I'LL CHANGE IT!
		setRespInfoFootData(finalHttpStatus);
		requestConsoleOut.println(request,response);
			
		
		return;	
		
    }
	
	
	
	


	private LRSUploadFileForm getFileToUpload(LRSQueueFile lrsQueueFile) {
		
		LRSUploadFileForm fileToUp = new LRSUploadFileForm();
		fileToUp.setDestinationFileName(lrsQueueFile.getDestinationFileName());
		fileToUp.setOriginalFileName(lrsQueueFile.getOriginalfullname());
		fileToUp.setPublicCloud(lrsQueueFile.getCloudProvider());
		fileToUp.setStorageRepoName(lrsQueueFile.getStorageRepoName());
		
		RestTemplate restTemplate = new RestTemplate();
		String url = cBaseURILRSManager.concat("/configs/v1/getcloudcredentials");
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("cloudProviderName",lrsQueueFile.getCloudProvider() );
		
		LRSParameterServiceModel cloudCredentials = restTemplate.getForObject(builder.toUriString(), LRSParameterServiceModel.class);
		
		
		//TODO TODO TODO - IMPLEMENT DECRYPT MODE
		fileToUp.setCspUserName(cloudCredentials.parameters.get(0).getValue());
		fileToUp.setCspUserKey(cloudCredentials.parameters.get(1).getValue());
		
		return fileToUp;
	}

	private LRSConfigServiceModelEng getUploadEngineAddress() {
		
		RestTemplate restTemplate = new RestTemplate();
		LRSConfigServiceModelEng uploadEngineAdd = restTemplate.getForObject(cBaseURILRSManager.concat("/configs/v1/getupdengineaddress"), LRSConfigServiceModelEng.class);
		
		return uploadEngineAdd;
	}
	
	
}
