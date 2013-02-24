package com.reflexer.model;

import android.content.ContentValues;

import com.reflexer.database.RXDatabaseHelper;

public class RXStimuliCondition extends RXProperty {

	public RXStimuliCondition(String name, Object value) {
		super(name, value);
	}

	public RXStimuliCondition(int id, String name, Object value) {
		super(id, name, value);
	}

	@Override
	public ContentValues toContentValues() {
		ContentValues cv = new ContentValues();
		if (getId() != -1) {
			cv.put(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_ID, getId());
		}
		cv.put(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_NAME, getName());
		cv.put(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_VALUE, getValue().toString());

		return cv;
	}

}
