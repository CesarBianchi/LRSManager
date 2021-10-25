package br.com.lrsbackup.LRSPersistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Queue")
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
	
	public static final String AWS = new String("AWS");
	public static final String AZURE = new String("AZURE");
	public static final String ORACLE_CLOUD = new String("ORACLE_CLOUD");
	
	
	public static final int READY = 1;
	public static final int UPLOADING = 2;
	public static final int UPLOADED = 3;
	public static final int ERROR = 4;
	
	
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
