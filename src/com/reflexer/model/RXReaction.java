package com.reflexer.model;

import java.util.ArrayList;

import android.content.ContentValues;

import com.reflexer.database.RXDatabaseHelper;

public abstract class RXReaction {

	/** 
	 * Id from database
	 */
	private int id;  
	
	/**
	 * RxReaction name
	 */
	protected String name; 
	
	protected ArrayList<RXReactionProperty> paramsList;

	/**
	 * Parameters for the action, based on the Property definition
	 */
	public abstract ArrayList<RXPropertyDefinition> getRXPropertyDefinitionList();
	
	public abstract void setRXPropertyDefinitionList(ArrayList<RXPropertyDefinition> readProperties);
	
	public RXReaction(String name){
		this.setId(-1);
		this.name = name;
		this.paramsList = new ArrayList<RXReactionProperty>();
	}

	public RXReaction(int id, String name){
		this.setId(id);
		this.name = name;
		this.paramsList = new ArrayList<RXReactionProperty>();
	}
	
	public RXReaction(int id, String name, ArrayList<RXReactionProperty> paramsList){
		this.setId(id);
		this.name = name;
		this.paramsList = paramsList;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Adds the property to the list of properties
	 * 
	 * Checks if the param already exists - then it updates it
	 * If it doesn't exist it adds it to the list
	 * @param param
	 */
	public void addRXParam(RXReactionProperty param){
		boolean shouldAdd = true;
		
		for (RXReactionProperty p : paramsList){
			if (p.getName().equals(param.getName())){
				p.setValue(param.getValue());
				shouldAdd = false;
			}
		}
		if (shouldAdd){
			paramsList.add(param);
		}
	}
	
	/**
	 * Returns the property for the seleced id
	 * @param id
	 * @return
	 */
	public RXProperty getRXParamById(int id){
		RXProperty property = null;
		
		for (RXProperty param : paramsList){
			if (param.getId() == id){
				property = param;
			}
		}
		return property;
	}
	
	/**
	 * Returns the property for name
	 * @param name
	 * @return
	 */
	public RXProperty getRXParamByName(String name){
		RXProperty property = null;
		
		for (RXProperty param : paramsList){
			if (param.getName() == name){
				property = param;
			}
		}
		return property;
	}
	
	/**
	 * Get all params
	 * @return
	 */
	public ArrayList<RXReactionProperty> getParamList(){
		return paramsList;
	}
	
	public abstract void execute();

	/**
	 * Creates CVs for "id" and "name"
	 * 
	 * Params are created from the 
	 * @return
	 */
	public ContentValues toContentValues(){
		ContentValues cv = new ContentValues();
		if (getId() != -1){
			cv.put(RXDatabaseHelper.COLUMN_REACTION_ID, getId());
		}
		cv.put(RXDatabaseHelper.COLUMN_REACTION_NAME, name);
		
		return cv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
