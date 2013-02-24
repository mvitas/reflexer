package com.reflexer.database.mapper;

import java.util.ArrayList;

import android.database.Cursor;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReflex;
import com.reflexer.model.RXStimuli;

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

	public static ArrayList<RXReflex> getAllReflexes(Cursor c){
		ArrayList<RXReflex> reflexes = new ArrayList<RXReflex>();
		
		if (c == null){
			return reflexes;
		}
		
		indexOfCOLUMN_RX_REFLEX_ID = c.getColumnIndex(RXDatabaseHelper.COLUMN_RX_REFLEX_ID);
		
		indexOfCOLUMN_RX_STIUMLI_ID = c.getColumnIndex(RXDatabaseHelper.COLUMN_RX_STIUMLI_ID);
		
		indexOfCOLUMN_RX_REACTION_ID= c.getColumnIndex(RXDatabaseHelper.COLUMN_RX_REACTION_ID);
		
		indexOfCOLUMN_RX_REFLEX_NAME = c.getColumnIndex(RXDatabaseHelper.COLUMN_RX_REFLEX_NAME);
		
		indexOfCOLUMN_RX_STIMULI_PROPERTY_ID = c.getColumnIndex(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_ID);
		
		indexOfCOLUMN_RX_STIMULI_PROPERTY_NAME = c.getColumnIndex(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_NAME);
		
		indexOfCOLUMN_RX_STIMULI_PROPERTY_VALUE = c.getColumnIndex(RXDatabaseHelper.COLUMN_RX_STIMULI_PROPERTY_VALUE);
		
		indexOfCOLUMN_SIMULUS_ACTION_NAME = c.getColumnIndex(RXDatabaseHelper.COLUMN_SIMULUS_ACTION_NAME);
		
		indexOfCOLUMN_RXPROPERTY_ID = c.getColumnIndex(RXDatabaseHelper.COLUMN_RXPROPERTY_ID);
		
		indexOfCOLUMN_RXPROPERTY_NAME = c.getColumnIndex(RXDatabaseHelper.COLUMN_RXPROPERTY_NAME);
		
		indexOfCOLUMN_RXPROPERTY_VALUE = c.getColumnIndex(RXDatabaseHelper.COLUMN_RXPROPERTY_VALUE);
		
		indexOfCOLUMN_REACTION_NAME = c.getColumnIndex(RXDatabaseHelper.COLUMN_REACTION_NAME);
		
		RXReflex ref = null;
		RXReflex oldRef = null;
		
		RXStimuli sti = null;
		RXReaction rea = null;
		
		
		while (c.moveToNext()){
			if (ref == null || nextReflex(c, ref)){
				sti = getStimuli(c);
				rea = getReaction(c);
				ref = getReflex(c, sti, rea);
			}
		}
		
		return reflexes;
	}

	private static RXReflex getReflex(Cursor c, RXStimuli sti, RXReaction rea) {
		return null;
	}

	private static RXReaction getReaction(Cursor c) {
//		RXReaction reaction = RXReaction 
		return null;
	}

	private static RXStimuli getStimuli(Cursor c) {
		RXStimuli stimuli = new RXStimuli();
		
		stimuli.setId(Integer.valueOf(c.getString(indexOfCOLUMN_RX_STIUMLI_ID)));
		stimuli.setName(c.getString(indexOfCOLUMN_SIMULUS_ACTION_NAME));
		
		return stimuli;
	}

	private static boolean nextReflex(Cursor c, RXReflex ref) {
		if (ref.getId() == Integer.valueOf(c.getString(indexOfCOLUMN_RX_REFLEX_ID))){
			return false;
		} else {
			return true;
		}
	}
}

















