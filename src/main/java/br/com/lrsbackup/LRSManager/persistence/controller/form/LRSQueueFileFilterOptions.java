package br.com.lrsbackup.LRSManager.persistence.controller.form;

import java.time.LocalDateTime;

public class LRSQueueFileFilterOptions {
	
	private String cloudProvider = new String();
	private LocalDateTime insertedStartDate = LocalDateTime.now();
	private LocalDateTime insertedEndDate = LocalDateTime.now();
	private String status = new String();
	
	public LRSQueueFileFilterOptions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LRSQueueFileFilterOptions(String cloudProvider, LocalDateTime insertedStartDate,
			LocalDateTime insertedEndDate, String status) {
		super();
		this.cloudProvider = cloudProvider;
		this.insertedStartDate = insertedStartDate;
		this.insertedEndDate = insertedEndDate;
		this.status = status;
	}

	public String getCloudProvider() {
		return cloudProvider;
	}

	public void setCloudProvider(String cloudProvider) {
		this.cloudProvider = cloudProvider;
	}

	public LocalDateTime getInsertedStartDate() {
		return insertedStartDate;
	}

	public void setInsertedStartDate(LocalDateTime insertedStartDate) {
		this.insertedStartDate = insertedStartDate;
	}

	public LocalDateTime getInsertedEndDate() {
		return insertedEndDate;
	}

	public void setInsertedEndDate(LocalDateTime insertedEndDate) {
		this.insertedEndDate = insertedEndDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
