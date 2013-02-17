package com.reflexer.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class RXReaction {

	/**
	 * Parameters for the action, based on the Property definition
	 */
	protected HashMap<String, Object> paramsMap = new HashMap<String, Object>();
	
	/**
	 * List of existing property fields required to generate the UI and fill data
	 * @return
	 */
	public abstract ArrayList<RXProperty> getRXPropertyList();
	
	public void setRXParam(String name, Object value){
		paramsMap.put(name, value);
	}
	
	public Object getRXParam(String name){
		return paramsMap.get(name);
	}
	
	public abstract void execute();
	
}
