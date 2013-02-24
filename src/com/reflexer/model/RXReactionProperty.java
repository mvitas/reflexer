package com.reflexer.model;

import android.content.ContentValues;

import com.reflexer.database.RXDatabaseHelper;

public class RXReactionProperty extends RXProperty {

	public RXReactionProperty(String name, Object value) {
		super(name, value);
	}
	
	public RXReactionProperty(int id, String name, Object value) {
		super(id, name, value);
	}

	@Override
	public ContentValues toContentValues() {
		ContentValues cv = new ContentValues();
		if (getId() != -1){
			cv.put(RXDatabaseHelper.COLUMN_RXPROPERTY_ID, getId());
		}
		cv.put(RXDatabaseHelper.COLUMN_RXPROPERTY_NAME, getName());
		cv.put(RXDatabaseHelper.COLUMN_RXPROPERTY_VALUE, getValue().toString());
		
		return cv;
	}

}
