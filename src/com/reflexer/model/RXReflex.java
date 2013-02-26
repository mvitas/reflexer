package com.reflexer.model;

import org.json.JSONException;

import android.content.ContentValues;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.util.RXTypeCaster;
import com.reflexer.util.RXTypes;

public class RXReflex implements IRXReflexListener {

	private int id;
	private String name;
	private RXStimuli stimuli;
	private RXReaction reaction;

	/**
	 * Name of the group in which the reflex is stored.
	 */
	private String groupName;

	/**
	 * Is the reflex currently active. If so, it is responding to stimuli.
	 */
	private boolean active;

	public RXReflex(String name, RXStimuli stimuli, RXReaction rection) {
		this.name = name;
		this.setId(RXDatabaseHelper.NEW_ITEM);
		this.setRxThis(stimuli);
		this.setRxThat(rection);
	}

	public RXReflex(int id, String name, RXStimuli stimuli, RXReaction reaction) {
		this.name = name;
		this.setId(id);
		this.setRxThis(stimuli);
		this.setRxThat(reaction);
	}

	public String getGroupName() {
		return groupName;
	}

	public RXReaction getReaction() {
		return reaction;
	}

	public RXStimuli getStimuli() {
		return stimuli;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setRxThat(RXReaction rxThat) {
		this.reaction = rxThat;
	}

	public void setRxThis(RXStimuli rxThis) {
		this.stimuli = rxThis;
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
	 * 
	 * @return
	 */
	public ContentValues toContentValues() {
		ContentValues cv = new ContentValues();
		if (id != -1) {
			cv.put(RXDatabaseHelper.COLUMN_RX_REFLEX_ID, id);
		}
		try {
			cv.put(RXDatabaseHelper.COLUMN_RX_REFLEX_NAME, RXTypeCaster.getInstance()
					.prepareObjectForStorage(RXTypes.RX_STRING, getName()).toString());
		} catch (JSONException e) {
			throw new IllegalStateException("Database reflex name error");
		}

		return cv;
	}

	@Override
	public void onStimulate() {
		reaction.execute();
	}

}
