package com.reflexer.database.mapper;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.database.Cursor;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReactionProperty;
import com.reflexer.model.RXReflex;
import com.reflexer.model.RXStimuli;
import com.reflexer.model.RXStimuliCondition;
import com.reflexer.util.RXTypeCaster;

public class RXMapper {

	private static int indexOfCOLUMN_RX_REFLEX_ID;
	private static int indexOfCOLUMN_RX_STIUMLI_ID;
	private static int indexOfCOLUMN_RX_REACTION_ID;
	private static int indexOfCOLUMN_RX_REFLEX_NAME;
	private static int indexOfCOLUMN_RX_STIMULI_PROPERTY_ID;
	private static int indexOfCOLUMN_RX_STIMULI_PROPERTY_NAME;
	private static int indexOfCOLUMN_RX_STIMULI_PROPERTY_VALUE;
	private static int indexOfCOLUMN_SIMULUS_ACTION_NAME;
	private static int indexOfCOLUMN_RXPROPERTY_ID;
	private static int indexOfCOLUMN_RXPROPERTY_NAME;
	private static int indexOfCOLUMN_RXPROPERTY_VALUE;
	private static int indexOfCOLUMN_REACTION_NAME;

	public static ArrayList<RXReflex> getAllReflexes(Context context, Cursor c) throws NumberFormatException, JSONException {

		ArrayList<RXReflex> reflexes = new ArrayList<RXReflex>();

		if (c == null) {
			return reflexes;
		}

		indexOfCOLUMN_RX_REFLEX_ID = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_RX_REFLEX_ID);

		indexOfCOLUMN_RX_STIUMLI_ID = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_RX_STIUMLI_ID);

		indexOfCOLUMN_RX_REACTION_ID = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_RX_REACTION_ID);

		indexOfCOLUMN_RX_REFLEX_NAME = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_RX_REFLEX_NAME);

		indexOfCOLUMN_RX_STIMULI_PROPERTY_ID = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_ID);

		indexOfCOLUMN_RX_STIMULI_PROPERTY_NAME = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_NAME);

		indexOfCOLUMN_RX_STIMULI_PROPERTY_VALUE = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_VALUE);

		indexOfCOLUMN_SIMULUS_ACTION_NAME = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_SIMULUS_ACTION_NAME);

		indexOfCOLUMN_RXPROPERTY_ID = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_RXPROPERTY_ID);

		indexOfCOLUMN_RXPROPERTY_NAME = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_RXPROPERTY_NAME);

		indexOfCOLUMN_RXPROPERTY_VALUE = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_RXPROPERTY_VALUE);

		indexOfCOLUMN_REACTION_NAME = c
				.getColumnIndex(RXDatabaseHelper.COLUMN_REACTION_NAME);

		RXReflex ref = new RXReflex("temp name",null, null);

		RXStimuli sti = null;
		RXReaction rea = null;

		while (c.moveToNext()) {
			if (nextReflex(c, ref)) {
				sti = getStimuli(context, c);
				rea = getReaction(context, c);
				ref = getReflex(c, sti, rea);
				sti.addRXParam(getStimuliProperty(context,c));
				rea.addParam(getReactionProperty(context,c));
				
				reflexes.add(ref);
			} else { // get all the remaining properties
				sti.addRXParam(getStimuliProperty(context, c));
				rea.addParam(getReactionProperty(context, c));
			}
		}

		return reflexes;
	}

	//tu jos dodat Stimuli name da dohvatim conditionType pa da mogu typecastat value van iz JSonArraya
	private static RXStimuliCondition getStimuliProperty(Context context, Cursor c) throws NumberFormatException, JSONException {
		int type = RXStimuli.getStimuliConditionType(context, c.getString(indexOfCOLUMN_SIMULUS_ACTION_NAME), c.getString(indexOfCOLUMN_RX_STIMULI_PROPERTY_NAME));
		
		RXStimuliCondition stiProp = new RXStimuliCondition(Integer.valueOf(c.getString(indexOfCOLUMN_RX_STIMULI_PROPERTY_ID))
				, c.getString(indexOfCOLUMN_RX_STIMULI_PROPERTY_NAME)
				, RXTypeCaster.getInstance().initializeType(type, c.getString(indexOfCOLUMN_RX_STIMULI_PROPERTY_VALUE)));
		
		return stiProp;
	}

	//tu jos dodat Reaction name da dohvatim conditionType pa da mogu typecastat value van iz JSonArraya
	private static RXReactionProperty getReactionProperty(Context context, Cursor c) {
		RXReactionProperty reactionProp = new RXReactionProperty(Integer.valueOf(c.getString(indexOfCOLUMN_RXPROPERTY_ID)),
				c.getString(indexOfCOLUMN_RXPROPERTY_NAME), c.getString(indexOfCOLUMN_RXPROPERTY_VALUE));
		return reactionProp;
	}

	private static RXReflex getReflex(Cursor c, RXStimuli sti, RXReaction rea) {
		RXReflex reflex = new RXReflex(Integer.valueOf(c.getString(indexOfCOLUMN_RX_REFLEX_ID))
				, c.getString(indexOfCOLUMN_RX_REFLEX_NAME)
				, sti
				, rea);

		return reflex;
	}

	//TODO: Get reaction definitions by name
	private static RXReaction getReaction(Context context, Cursor c) {
		RXReaction reaction = RXReaction.createReaction(context
				, Integer.valueOf(c.getString(indexOfCOLUMN_RX_REACTION_ID)) 
				, c.getString(indexOfCOLUMN_REACTION_NAME));
		
		return reaction;
	}

	private static RXStimuli getStimuli(Context context, Cursor c) {
		RXStimuli stimuli = new RXStimuli(context
				, Integer.valueOf(c.getString(indexOfCOLUMN_RX_STIUMLI_ID)) 
				, c.getString(indexOfCOLUMN_SIMULUS_ACTION_NAME));

		return stimuli;
	}

	private static boolean nextReflex(Cursor c, RXReflex ref) {
		if (ref.getId() == Integer.valueOf(c
				.getString(indexOfCOLUMN_RX_REFLEX_ID))) {
			return false;
		} else {
			return true;
		}
	}
}
