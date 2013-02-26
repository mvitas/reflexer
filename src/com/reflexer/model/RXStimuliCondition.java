package com.reflexer.model;

import org.json.JSONException;

import android.content.ContentValues;
import android.content.Context;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.util.RXTypeCaster;

public class RXStimuliCondition extends RXProperty {

	public RXStimuliCondition(String name, Object value) {
		super(name, value);
	}

	public RXStimuliCondition(int id, String name, Object value) {
		super(id, name, value);
	}

	// TODO: property value -- prepareForStorage
	@Override
	public ContentValues toContentValues(Context context, String stimulusName) {
		ContentValues cv = new ContentValues();
		if (getId() != -1) {
			cv.put(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_ID, getId());
		}
		cv.put(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_NAME, getName());
		
		int type = RXStimuli.getStimuliConditionType(context
				, stimulusName
				, getName());
		
		try {
			cv.put(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_VALUE, 
					RXTypeCaster.getInstance().prepareObjectForStorage(
							type, getValue()).toString());
		} catch (JSONException e) {
			throw new IllegalStateException("Database stimuli-condition value error");
		}

		return cv;
	}
}
