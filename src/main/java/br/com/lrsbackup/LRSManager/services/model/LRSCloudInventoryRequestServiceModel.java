package br.com.lrsbackup.LRSManager.services.model;


import br.com.lrsbackup.LRSManager.persistence.model.LRSCloudInvetoryRequest;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;

	public class LRSCloudInventoryRequestServiceModel {
		private LRSResponseInfo responseInfo = new LRSResponseInfo();
		private LRSCloudInvetoryRequest cloudInventoryRequest = new LRSCloudInvetoryRequest();
		private LRSResponseMessages messages = new LRSResponseMessages();
		
		public LRSCloudInventoryRequestServiceModel() {
			super();
		}

		public LRSCloudInventoryRequestServiceModel(LRSResponseInfo responseInfo, LRSCloudInvetoryRequest cloudInventoryRequest, LRSResponseMessages messages) {
			super();
			this.responseInfo = responseInfo;
			this.cloudInventoryRequest = cloudInventoryRequest;
			this.messages = messages;
		}

		public LRSResponseInfo getResponseInfo() {
			return responseInfo;
		}

		public void setResponseInfo(LRSResponseInfo responseInfo) {
			this.responseInfo = responseInfo;
		}

		public LRSCloudInvetoryRequest getCloudInventoryRequests() {
			return cloudInventoryRequest;
		}

		public void setCloudInventoryRequests (LRSCloudInvetoryRequest cloudInventoryRequests) {
			this.cloudInventoryRequest = cloudInventoryRequests;
		}

		public LRSResponseMessages getMessages() {
			return messages;
		}

		public void setMessages(LRSResponseMessages messages) {
			this.messages = messages;
		}
		
		
	}

