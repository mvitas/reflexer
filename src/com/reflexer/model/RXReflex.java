package com.reflexer.model;

import android.content.ContentValues;

import com.reflexer.database.RXDatabaseHelper;

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

	public RXReflex(RXStimuli stimuli, RXReaction rection) {
		this.setId(RXDatabaseHelper.NEW_ITEM);
		this.setRxThis(stimuli);
		this.setRxThat(rection);
	}

	public RXReflex(int id, RXStimuli stimuli, RXReaction reaction) {
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
		cv.put(RXDatabaseHelper.COLUMN_RX_REFLEX_NAME, getName());

		return cv;
	}

	@Override
	public void onStimulate() {
		reaction.execute();
	}

}
