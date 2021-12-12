package br.com.lrsbackup.LRSManager.persistence.controller.form;

import java.time.LocalDateTime;

import br.com.lrsbackup.LRSManager.persistence.model.LRSProtectedDir;

public class LRSProtectedDirForm {

		private String name = new String();
		private String description = new String();
		private String originalPath = new String();
		private String storageRepoName = new String();
		private String pathaws = new String();
		private String pathazure = new String();
		private String pathoracle = new String();
		private String uri_Aws = new String();
		private String uri_Azure = new String();
		private String uri_Oracle = new String();
		

		public LRSProtectedDirForm() {
			super();
			// TODO Auto-generated constructor stub
		}

		public LRSProtectedDirForm(String name, String description, String originalPath, String pathaws,
				String pathazure, String pathoracle, String storageName,
				String pURIAWS, String pURIAzure, String pURIOracle) {
			this.name = name;
			this.description = description;
			this.originalPath = originalPath;
			this.pathaws = pathaws;
			this.pathazure = pathazure;
			this.pathoracle = pathoracle;
			this.storageRepoName = storageName;
			this.uri_Aws = pURIAWS;
			this.uri_Azure = pURIAzure;
			this.uri_Oracle = pURIOracle;
		}

		public String getOriginalPath() {
			return originalPath;
		}

		public void setOriginalPath(String originalPath) {
			this.originalPath = originalPath;
		}
				
		public String getPathaws() {
			return pathaws;
		}

		public void setPathaws(String pathaws) {
			this.pathaws = pathaws;
		}

		public String getPathazure() {
			return pathazure;
		}

		public void setPathazure(String pathazure) {
			this.pathazure = pathazure;
		}

		public String getPathoracle() {
			return pathoracle;
		}

		public void setPathoracle(String pathoracle) {
			this.pathoracle = pathoracle;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		public String getStorageRepoName() {
			return storageRepoName;
		}

		public void setStorageRepoName(String storageRepoName) {
			this.storageRepoName = storageRepoName;
		}
		
		public String getUri_Aws() {
			return uri_Aws;
		}

		public void setUri_Aws(String uri_Aws) {
			this.uri_Aws = uri_Aws;
		}

		public String getUri_Azure() {
			return uri_Azure;
		}

		public void setUri_Azure(String uri_Azure) {
			this.uri_Azure = uri_Azure;
		}

		public String getUri_Oracle() {
			return uri_Oracle;
		}

		public void setUri_Oracle(String uri_Oracle) {
			this.uri_Oracle = uri_Oracle;
		}

		public LRSProtectedDir convertToProtectedDir() {
			LRSProtectedDir LRSProtDir = new LRSProtectedDir();
			
			LRSProtDir.setName(this.name);
			LRSProtDir.setContent(this.description);
			LRSProtDir.setOriginPath(this.originalPath.trim());
			LRSProtDir.setDestinationPath_AWS(this.pathaws.trim());
			LRSProtDir.setDestinationPath_Azure(this.pathazure.trim());
			LRSProtDir.setDestinationPath_Oracle(this.pathoracle.trim());
			LRSProtDir.setStorageRepositoryName(this.storageRepoName.trim());
			LRSProtDir.setURIPath_AWS(this.uri_Aws);
			LRSProtDir.setURIPath_Azure(this.uri_Azure);
			LRSProtDir.setURIPath_Oracle(this.uri_Oracle);
			LRSProtDir.setCreatedDate(LocalDateTime.now());
			
			return LRSProtDir;
		}
	
}
