package com.reflexer.model;

import com.reflexer.database.RXDatabaseHelper;

import android.content.ContentValues;

public class RXProperty {

	/**
	 * id from database
	 */
	private int id;
	
	private String name;
	
	/**
	 * Is stored as "text" in the database
	 */
	private Object value;

	protected RXProperty(String name, Object value){
		this.setId(-1);
		this.setName(name);
		this.setValue(value);
	}
	
	protected RXProperty(int id, String name, Object value) {
		this.setId(id);
		this.setName(name);
		this.setValue(value);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * Creates CVs for "id", "name" and "value"
	 * @return
	 */
	protected ContentValues toContentValues(){
		ContentValues cv = new ContentValues();
		if (getId() != -1){
			cv.put(RXDatabaseHelper.COLUMN_RXPROPERTY_ID, getId());
		}
		cv.put(RXDatabaseHelper.COLUMN_RXPROPERTY_NAME, getName());
		cv.put(RXDatabaseHelper.COLUMN_RXPROPERTY_VALUE, getValue().toString());
		
		return cv;
	}

}
