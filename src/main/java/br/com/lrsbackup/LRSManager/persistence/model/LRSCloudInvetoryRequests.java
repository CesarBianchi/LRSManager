package br.com.lrsbackup.LRSManager.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LRSCloudInvetoryRequests {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cloudProvider = new String();
	private LocalDateTime requestedDate = LocalDateTime.now();
	private String protocolID = new String();
	private String status = new String();
	
	public LRSCloudInvetoryRequests() {
		super();
	}

	public LRSCloudInvetoryRequests(Long id, String cloudProvider, LocalDateTime requestedDate, String protocolID, String status) {
		this.id = id;
		this.cloudProvider = cloudProvider;
		this.requestedDate = requestedDate;
		this.protocolID = protocolID;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCloudProvider() {
		return cloudProvider;
	}

	public void setCloudProvider(String cloudProvider) {
		this.cloudProvider = cloudProvider;
	}

	public LocalDateTime getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(LocalDateTime requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getProtocolID() {
		return protocolID;
	}

	public void setProtocolID(String protocolID) {
		this.protocolID = protocolID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
