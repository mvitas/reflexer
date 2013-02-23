package com.reflexer.model;

import java.util.ArrayList;

import android.content.ContentValues;

import com.reflexer.database.RXDatabaseHelper;

public abstract class RXReaction {

	/** 
	 * Id from database
	 */
	protected int id;  
	
	/**
	 * RxReaction name
	 */
	protected String name; 
	
	protected ArrayList<RXProperty> paramsList;

	/**
	 * Parameters for the action, based on the Property definition
	 */
	public abstract ArrayList<RXPropertyDefinition> getRXPropertyDefinitionList();
	
	public abstract void setRXPropertyDefinitionList(ArrayList<RXPropertyDefinition> readProperties);
	
	public RXReaction(String name){
		this.id = -1;
		this.name = name;
		this.paramsList = new ArrayList<RXProperty>();
	}

	public RXReaction(int id, String name){
		this.id = id;
		this.name = name;
		this.paramsList = new ArrayList<RXProperty>();
	}
	
	public RXReaction(int id, String name, ArrayList<RXProperty> paramsList){
		this.id = id;
		this.name = name;
		this.paramsList = paramsList;
	}
	
	public String getName() {
		return name;
	}
	
	public void addRXParam(RXProperty param){
		paramsList.add(param);
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
	 * Get all params
	 * @return
	 */
	public ArrayList<RXProperty> getParamList(){
		return paramsList;
	}
	
	public abstract void execute();

	/**
	 * Creates CVs for "id" and "name"
	 * 
	 * Params are created from the 
	 * @return
	 */
	protected ContentValues toContentValues(){
		ContentValues cv = new ContentValues();
		if (id != -1){
			cv.put(RXDatabaseHelper.COLUMN_REACTION_ID, id);
		}
		cv.put(RXDatabaseHelper.COLUMN_REACTION_NAME, name);
		
		return cv;
	}

}
