package br.com.lrsbackup.LRSPersistence.Util;

public class LRSBackupPreLoad {
	
	public void preLoadDatabaseRecords() {
		
		new LRSParametersPreLoad().loadInitialRecords();
		
	}
}
