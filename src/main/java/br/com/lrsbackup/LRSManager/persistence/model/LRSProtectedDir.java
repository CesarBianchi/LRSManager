package br.com.lrsbackup.LRSManager.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LRSProtectedDir {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String originPath = new String();
	private String destinationPath_AWS = new String();
	private String destinationPath_Azure = new String();
	private String destinationPath_Oracle = new String();
	
	public LRSProtectedDir() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LRSProtectedDir(long id, String originPath, String destinationPath_AWS, String destinationPath_Azure,
			String destinationPath_Oracle) {
		super();
		this.id = id;
		this.originPath = originPath;
		this.destinationPath_AWS = destinationPath_AWS;
		this.destinationPath_Azure = destinationPath_Azure;
		this.destinationPath_Oracle = destinationPath_Oracle;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOriginPath() {
		return originPath;
	}

	public void setOriginPath(String originPath) {
		this.originPath = originPath;
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
	
	
	
}
