package br.com.lrsbackup.LRSPersistence.Util;


public class LRSBackupConsoleOut {

	public LRSBackupConsoleOut(String msg) {
			
		System.out.println(java.time.LocalTime.now().toString().substring(0,12).concat(" [APP] ").concat(msg) );
	}
	
	
	
}
