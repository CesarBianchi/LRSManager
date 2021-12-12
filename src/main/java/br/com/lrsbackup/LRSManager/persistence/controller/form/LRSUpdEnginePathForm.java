package br.com.lrsbackup.LRSManager.persistence.controller.form;

public class LRSUpdEnginePathForm {
	
	private String host = new String();
	private String port = new String();
	private String fullAdress = new String();
	
	public LRSUpdEnginePathForm() {
		super();
	}

	public LRSUpdEnginePathForm(String host, String port) {
		super();
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}
	
	public void setHost(String host) {
		this.host = host;
		this.createFullAddress();
	}

	public void setPort(String port) {
		this.port = port;
		this.createFullAddress();
	}

	public String getFullAdress() {
		
		String address = new String();
		boolean inEclipse = !(System.getenv("eclipse42") == null);
		
		if (inEclipse) {
			address = "http://127.0.0.1:8081";
		} else {
			address = fullAdress;
		}
		
		
		return address;
	}

	public void setFullAdress(String fullAdress) {
		this.fullAdress = fullAdress;
		return;
	}
	
	private void setFullAdress(String path, String port) {
		this.fullAdress = path.trim();
		
		if (!port.trim().isEmpty()) {
			this.fullAdress = this.fullAdress.concat(":").concat(port.trim());
		}
		
		return;
	}
	
	public void createFullAddress() {
		this.setFullAdress(this.getHost(),this.getPort());
		
		return;
	}
	
	
}
