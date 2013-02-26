package com.reflexer.model;

import org.json.JSONException;

import android.content.ContentValues;
import android.content.Context;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.util.RXTypeCaster;

public class RXReactionProperty extends RXProperty {

	public RXReactionProperty(String name, Object value) {
		super(name, value);
	}
	
	public RXReactionProperty(int id, String name, Object value) {
		super(id, name, value);
	}

	@Override
	public ContentValues toContentValues(Context context, String reactionName) {
		ContentValues cv = new ContentValues();
		if (getId() != -1){
			cv.put(RXDatabaseHelper.COLUMN_RXPROPERTY_ID, getId());
		}
		
		cv.put(RXDatabaseHelper.COLUMN_RXPROPERTY_NAME, getName());
		
		int type = RXReaction.getReactionPropertyType(context, reactionName, getName());
		
		try {
			cv.put(RXDatabaseHelper.COLUMN_RXPROPERTY_VALUE
					, RXTypeCaster.getInstance().prepareObjectForStorage(type, getValue()).toString());
		} catch (JSONException e) {
			throw new IllegalStateException("Unable to set property value for reaction");
		}
		
		return cv;
	}

}
