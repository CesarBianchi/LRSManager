package br.com.lrsbackup.LRSManager.util;

public class LRSDatabaseCredentials {
	private String ip = new String();
	private String port = new String();
	private String dbName = new String();
	private String username = new String();
	private String password = new String();
	
	public LRSDatabaseCredentials() {
		super();
		this.loadAllParams();
	}
	
	public String getIp() {
		return ip;
	}
	public String getPort() {
		return port;
	}
	
	public String getDbName() {
		return dbName;
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	private void loadAllParams() {
		boolean lerror = false;
		
		
		this.loadIp();
		this.loadPort();
		this.loadDbName();
		this.loadUsername();
		this.loadPassword();
		
		if (this.ip.isEmpty()) {
			lerror = true;
		} else if (this.port.isEmpty()) {
			lerror = true;
		} else if (this.dbName.isEmpty()) {
			lerror = true;	
		} else if (this.username.isEmpty()) {
			lerror = true;		
		} else if (this.password.isEmpty()) {
			lerror = true;
		}
		
		if (lerror) {
			new LRSConsoleOut("Mandatory Environment Variables Not Found or Empty");
			new LRSConsoleOut("Please, check 'LRSManager_DBIP' / 'LRSManager_DBPORT' / 'LRSManager_DBNAME' / 'LRSManager_DBUSER' / 'LRSManager_DBPSW' Environment Variables ");
		}
		
	}
	
	private void loadIp() {
		this.ip =  System.getenv("LRSManager_DBIP");
	}

	private void loadPort() {
		this.port =  System.getenv("LRSManager_DBPORT");
	}

	private void loadDbName() {
		this.dbName = System.getenv("LRSManager_DBNAME");
	}
	
	private void loadUsername() {
		this.username = System.getenv("LRSManager_DBUSER");
	}

	private void loadPassword() {
		this.password =  System.getenv("LRSManager_DBPSW");
	}
	
}
