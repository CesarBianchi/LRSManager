package br.com.lrsbackup.LRSManager.persistence.controller.form;

import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;

public class LRSParameterForm {

	private String name = new String();
	private String value = new String();
	
	
	public LRSParameterForm() {
		super();
		// TODO Auto-generated constructor stub
	}


	public LRSParameterForm(String name, String value) {
		this.name = name;
		this.value = value;
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
	
	public LRSParameter convertToParameter() {
		LRSParameter LRSParam = new LRSParameter();
		
		LRSParam.setName(this.getName());
		LRSParam.setValue(this.getValue());
		
		return LRSParam;
	}
	
}
