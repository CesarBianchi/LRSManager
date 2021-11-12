package br.com.lrsbackup.LRSManager.persistence.model;

public enum LRSOptionsFileStatus {
	READY_TO_UP,
	UPLOADING,
	UPLOADED_STANDARD,
	CONVERTING_ARCHIEVE,
	DONE,
	ERROR;
}
