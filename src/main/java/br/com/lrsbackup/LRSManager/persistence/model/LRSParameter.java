package br.com.lrsbackup.LRSManager.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
public class LRSParameter {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name = new String();
	private String value = new String();
	
	public LRSParameter() {
		super();
	}

	public LRSParameter(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	
}
