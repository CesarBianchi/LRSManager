package br.com.lrsbackup.LRSManager.persistence.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class LRSQueueMessage {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String originalFileName = new String();
	private String destinationFileName = new String();
	private String cloudProvider = new String();
	private int status;
	private int percentUploaded;
	private int tentatives;
	
	public LRSQueueMessage() {
		super();

	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getOriginalFileName() {
		return originalFileName;
	}


	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
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


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
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
	
}
