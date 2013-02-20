package com.reflexer.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class RXReaction {

	/**
	 * Parameters for the action, based on the Property definition
	 */
	protected HashMap<String, Object> paramsMap = new HashMap<String, Object>();

	protected ArrayList<RXProperty> propertyList = new ArrayList<RXProperty>();

	/**
	 * List of existing property fields required to generate the UI and fill
	 * data
	 * 
	 * @return
	 */
	public ArrayList<RXProperty> getRXPropertyList() {
		return propertyList;
	}

	/**
	 * Add another property to the list of fields required to generate the UI
	 * and fill the data
	 * 
	 * @param property
	 */
	public void setRXPropertyList(ArrayList<RXProperty> propertyList) {
		this.propertyList = propertyList;
	}

	public void setRXParam(String name, Object value) {
		paramsMap.put(name, value);
	}

	public Object getRXParam(String name) {
		return paramsMap.get(name);
	}

	public abstract void execute();

}
