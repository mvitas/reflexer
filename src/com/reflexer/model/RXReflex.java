package com.reflexer.model;

import android.content.ContentValues;

import com.reflexer.database.RXDatabaseHelper;

public class RXReflex {
 
	private int id;
	private String name;
	private RXStimuli rxThis;
	private RXReaction rxThat;

	/**
	 * Name of the group in which the reflex is stored.
	 */
	private String groupName;

	/**
	 * Is the reflex currently active. If so, it is responding to stimuli.
	 */
	private boolean active;

	public RXReflex(RXStimuli rxThis, RXReaction rxThat) {
		this.setId(-1);
		this.setRxThis(rxThis);
		this.setRxThat(rxThat);
	}
	
	public RXReflex(int id, RXStimuli rxThis, RXReaction rxThat) {
		this.setId(id);
		this.setRxThis(rxThis);
		this.setRxThat(rxThat);
	}

	public String getGroupName() {
		return groupName;
	}

	public RXReaction getRxThat() {
		return rxThat;
	}

	public RXStimuli getRxThis() {
		return rxThis;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setRxThat(RXReaction rxThat) {
		this.rxThat = rxThat;
		this.rxThis.setReaction(rxThat);
	}

	public void setRxThis(RXStimuli rxThis) {
		this.rxThis = rxThis;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
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
	/**
	 * Creates CVs for "id" 
	 * 
	 * Params are created from the 
	 * @return
	 */
	public ContentValues toContentValues(){
		ContentValues cv = new ContentValues();
		if (id != -1){
			cv.put(RXDatabaseHelper.COLUMN_RX_REFLEX_ID, id);
		}
		cv.put(RXDatabaseHelper.COLUMN_RX_REFLEX_NAME, getName());
		
		return cv;
	}


}
