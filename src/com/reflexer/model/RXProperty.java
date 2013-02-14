package com.reflexer.model;

public class RXProperty {

	private boolean required;
	private String name;
	private String type;
	
	public RXProperty(boolean required, String name, String type) {
		super();
		this.setRequired(required);
		this.setName(name);
		this.setType(type);
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
