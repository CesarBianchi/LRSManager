package br.com.lrsbackup.LRSPersistence.Util;

import br.com.lrsbackup.LRSPersistence.controller.LRSParameterManager;
import br.com.lrsbackup.LRSPersistence.model.LRSParameter;

import java.util.ArrayList;
import java.util.List;

public class LRSParametersPreLoad {
	
	
	public void loadInitialRecords() {
		
		this.regNewParameter("CloudAWSEnabled"			, "True"); 				//Nuvem AWS Ativa
		this.regNewParameter("CloudAzureEnabled"		, "False");				//Nuvem Azure Ativa
		this.regNewParameter("CloudOracleEnabled"		, "False");				//Nuvem Oracle Ativa
		this.regNewParameter("UserCloudAWS" 			, "TBD");				//User Nuvem AWS
		this.regNewParameter("UserCloudAzure"			, "null");	  	  		//User Nuvem Azure
		this.regNewParameter("UserCloudOracle"			, "null");	   	 		//User Nuvem Oracle
		this.regNewParameter("KeyCloudAWS"				, "TBD");	    		//Key Nuvem AWS
		this.regNewParameter("KeyCloudAzure"			, "null");	    		//Key Nuvem AWS
		this.regNewParameter("KeyCloudOracle"			, "null");	    		//Key Nuvem AWS
		this.regNewParameter("TimetoWaitToUpload"		, "48");	    		//Time to Wait to Upload.
		this.regNewParameter("ScheduledStartTime"		, "23:00:00");			//Scheduled Start Time
		this.regNewParameter("ServicePath_LRSAgent"		, "http://127.0.0.1");	
		this.regNewParameter("ServicePort_LRSAgent"		, "8080");
	}
	
	private void regNewParameter(String pName, String pValue) {
		
		LRSParameterManager parameterManager = new LRSParameterManager();
		LRSParameter parameter = new LRSParameter();
		List<LRSParameter> listFind = new ArrayList<>();
		listFind = parameterManager.searchByName(pName);
		boolean lFound = false;
		
		
		for (int i = 0; i < listFind.size(); i++ ) {
			parameter = listFind.get(i);
			if (parameter.getName().toUpperCase().trim().equals(pName.toUpperCase().trim())) {
				lFound = true;
				new LRSBackupConsoleOut("### Parameter: " + pName + " found in database. Skiping it! ....");
				break;
			}
		}
		
		
		if (!lFound) {
			LRSParameter newParameter = new LRSParameter();
			newParameter.setName(pName.trim());
			newParameter.setValue(pValue.trim());
		
			LRSParameterManager newParameterManager = new LRSParameterManager();
			newParameterManager.newParameter(newParameter);
			
			new LRSBackupConsoleOut("### Parameter: " + pName + " CREATED in database using default values");
		}
	}
	
}
