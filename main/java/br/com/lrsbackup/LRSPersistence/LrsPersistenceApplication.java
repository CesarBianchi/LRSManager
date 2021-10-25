package br.com.lrsbackup.LRSPersistence;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.lrsbackup.LRSPersistence.Util.LRSBackupPreLoad;
import br.com.lrsbackup.LRSPersistence.controller.LRSParameterManager;
import br.com.lrsbackup.LRSPersistence.model.LRSParameter;

@SpringBootApplication
public class LrsPersistenceApplication {

	public static void main(String[] args) {
		//SpringApplication.run(LrsPersistenceApplication.class, args);
		
		new LRSBackupPreLoad().preLoadDatabaseRecords();
		
		
	}

}
