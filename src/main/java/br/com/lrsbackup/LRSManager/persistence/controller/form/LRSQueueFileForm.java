package br.com.lrsbackup.LRSManager.persistence.controller.form;

import java.time.LocalDateTime;

import br.com.lrsbackup.LRSManager.enums.LRSOptionsFileStatus;
import br.com.lrsbackup.LRSManager.persistence.model.LRSQueueFile;

public class LRSQueueFileForm {

	private String originalfullname = new String();
	private String destinationFileName = new String();
	private String cloudProvider = new String();
	private String status;
	private int percentUploaded;
	private int tentatives;
	private LocalDateTime insertedDate = LocalDateTime.now();
	private LocalDateTime processedDate = LocalDateTime.now();
	
	
	
	public LRSQueueFileForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOriginalfullname() {
		return originalfullname;
	}
	
	public void setOriginalfullname(String originalfullname) {
		this.originalfullname = originalfullname;
	}
	public String getDestinationFileName() {
		return destinationFileName;
	}
	public void setDestinationFileName(String destinationFileName) {
		this.destinationFileName = destinationFileName;
	}
	public String getCloudProvider() {
		return cloudProvider;
	}
	public void setCloudProvider(String cloudProvider) {
		this.cloudProvider = cloudProvider;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPercentUploaded() {
		return percentUploaded;
	}
	public void setPercentUploaded(int percentUploaded) {
		this.percentUploaded = percentUploaded;
	}
	public int getTentatives() {
		return tentatives;
	}
	public void setTentatives(int tentatives) {
		this.tentatives = tentatives;
	}
	public LocalDateTime getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(LocalDateTime insertedDate) {
		this.insertedDate = insertedDate;
	}
	public LocalDateTime getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(LocalDateTime processedDate) {
		this.processedDate = processedDate;
	}

	public LRSQueueFile convertToNew() {
		LRSQueueFile newFile = new LRSQueueFile();
		
		newFile.setOriginalfullname(this.originalfullname);
		newFile.setDestinationFileName(this.destinationFileName);
		newFile.setCloudProvider(this.cloudProvider);
		
		newFile.setStatus(LRSOptionsFileStatus.READY_TO_UP.toString());
		newFile.setPercentUploaded(0);
		newFile.setTentatives(0);
		newFile.setInsertedDate(LocalDateTime.now());
		
		return newFile;
	}
	
	
}
