package br.com.lrsbackup.LRSManager.persistence.controller.form;

import java.time.LocalDateTime;

import br.com.lrsbackup.LRSManager.persistence.model.LRSProtectedDir;

public class LRSProtectedDirForm {

		private String name = new String();
		private String description = new String();
		private String originalPath = new String();
		private String pathaws = new String();
		private String pathazure = new String();
		private String pathoracle = new String();

		public LRSProtectedDirForm() {
			super();
			// TODO Auto-generated constructor stub
		}

		public LRSProtectedDirForm(String name, String description, String originalPath, String pathaws,
				String pathazure, String pathoracle) {
			this.name = name;
			this.description = description;
			this.originalPath = originalPath;
			this.pathaws = pathaws;
			this.pathazure = pathazure;
			this.pathoracle = pathoracle;
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

		public LRSProtectedDir convertToProtectedDir() {
			LRSProtectedDir LRSProtDir = new LRSProtectedDir();
			
			LRSProtDir.setName(this.name);
			LRSProtDir.setContent(this.description);
			LRSProtDir.setOriginPath(this.originalPath.trim());
			LRSProtDir.setDestinationPath_AWS(this.pathaws.trim());
			LRSProtDir.setDestinationPath_Azure(this.pathaws.trim());
			LRSProtDir.setDestinationPath_Oracle(this.pathoracle.trim());
			LRSProtDir.setCreatedDate(LocalDateTime.now());
			
			return LRSProtDir;
		}
	
}
