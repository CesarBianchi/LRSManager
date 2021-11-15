package br.com.lrsbackup.LRSManager.persistence.model;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LRSQueueFile {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String originalfullname = new String();
	private String destinationFileName = new String();
	private String cloudProvider = new String();
	private String status;
	private int percentUploaded;
	private int tentatives;
	private LocalDateTime insertedDate = LocalDateTime.now();
	private LocalDateTime processedDate = LocalDateTime.now();
	
	public LRSQueueFile() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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


	public void setStatus(String pStatus) {
		this.status = pStatus;
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
	
	
	
}
