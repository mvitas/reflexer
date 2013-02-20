package com.reflexer.model;

public class RXProperty {

	private boolean required;
	private String name;
	private int type;

	public RXProperty(boolean required, String name, int type) {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
