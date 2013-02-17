package com.reflexer.model;

import java.util.ArrayList;

public class RXCondition {

	/**
	 * Does this condition have to be set.
	 */
	private boolean required;

	/**
	 * Name of the condition.
	 */
	private String name;

	/**
	 * Type of the condition. Possible types defined in RXTypes.
	 */
	private int type;

	/**
	 * List of conditions that must be set in order for this condition to be
	 * available.
	 */
	private ArrayList<RXCondition> dependsOn;

	public RXCondition(boolean required, String name, int type, ArrayList<RXCondition> dependsOn) {
		super();
		this.required = required;
		this.name = name;
		this.type = type;
		this.dependsOn = dependsOn;
	}

	public void addDependency(RXCondition condition) {
		this.dependsOn.add(condition);
	}

	public ArrayList<RXCondition> getDependsOn() {
		return dependsOn;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setDependsOn(ArrayList<RXCondition> dependsOn) {
		this.dependsOn = dependsOn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setType(int type) {
		this.type = type;
	}

}
