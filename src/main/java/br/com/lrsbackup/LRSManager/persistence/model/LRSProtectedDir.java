package br.com.lrsbackup.LRSManager.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LRSProtectedDir {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name = new String();
	private String content = new String();
	private String dirname = new String();
	private String storageRepositoryName = new String();
	private String destinationPath_AWS = new String();
	private String destinationPath_Azure = new String();
	private String destinationPath_Oracle = new String();
	private LocalDateTime createdDate = LocalDateTime.now();
	
	public LRSProtectedDir() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public LRSProtectedDir(long id, String name, String content, String dirname, String destinationPath_AWS,
			String destinationPath_Azure, String destinationPath_Oracle, String storageName) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.dirname = dirname;
		this.destinationPath_AWS = destinationPath_AWS;
		this.destinationPath_Azure = destinationPath_Azure;
		this.destinationPath_Oracle = destinationPath_Oracle;
		this.storageRepositoryName = storageName;
		this.createdDate = LocalDateTime.now();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOriginPath() {
		return dirname;
	}

	public void setOriginPath(String originPath) {
		this.dirname = originPath;
	}

	public String getDestinationPath_AWS() {
		return destinationPath_AWS;
	}

	public void setDestinationPath_AWS(String destinationPath_AWS) {
		this.destinationPath_AWS = destinationPath_AWS;
	}

	public String getDestinationPath_Azure() {
		return destinationPath_Azure;
	}

	public void setDestinationPath_Azure(String destinationPath_Azure) {
		this.destinationPath_Azure = destinationPath_Azure;
	}

	public String getDestinationPath_Oracle() {
		return destinationPath_Oracle;
	}

	public void setDestinationPath_Oracle(String destinationPath_Oracle) {
		this.destinationPath_Oracle = destinationPath_Oracle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDirname() {
		return dirname;
	}

	public void setDirname(String dirname) {
		this.dirname = dirname;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getStorageRepositoryName() {
		return storageRepositoryName;
	}

	public void setStorageRepositoryName(String storageRepositoryName) {
		this.storageRepositoryName = storageRepositoryName;
	}
	
	
	
	
}
