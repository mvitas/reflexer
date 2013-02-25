package com.reflexer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReactionProperty;
import com.reflexer.model.RXReflex;
import com.reflexer.model.RXStimuli;
import com.reflexer.model.RXStimuliCondition;
import com.reflexer.util.RXUtil;

public class RXDatabaseHelper extends SQLiteOpenHelper {

	// TODO: izvuc ovu inicijalizaciju u assets

	private static String DATABASE_NAME = "reflexer.db";
	private static String DATABASE_VERSION = "1";

	public static final int NEW_ITEM = -1;

	/*
	 * RXREACTION
	 */

	private static final String TABLE_RXREACTION = "rx_reaction";
	public static final String COLUMN_REACTION_NAME = "rx_reaction_name";
	public static final String COLUMN_REACTION_ID = "rx_reaction_id";

	private static final String CREATE_RXREACTION = "CREATE TABLE IF NOT EXISTS " + TABLE_RXREACTION + " ("
			+ COLUMN_REACTION_ID + " integer primary key autoincrement, " + COLUMN_REACTION_NAME + " text not null)";

	/*
	 * RX_REACTION_PROPERTY
	 */
	private static String TABLE_RXPROPERTY = "rx_property";
	public static String COLUMN_RXPROPERTY_ID = "rx_property_id";
	public static String COLUMN_RXPROPERTY_NAME = "rx_property_name";
	public static String COLUMN_RXPROPERTY_VALUE = "rx_property_value";
	public static final String COLUMN_RX_REACTION_ID = "fk_rx_reaction";

	private static final String CREATE_RX_REACTION_PROPERTY = "create table IF NOT EXISTS " + TABLE_RXPROPERTY + " ("
			+ COLUMN_RXPROPERTY_ID + " integer primary key autoincrement, " + COLUMN_RX_REACTION_ID + " integer, "
			+ COLUMN_RXPROPERTY_NAME + " text not null, " + COLUMN_RXPROPERTY_VALUE + " text, " + "FOREIGN KEY ("
			+ COLUMN_RX_REACTION_ID + ") REFERENCES " + TABLE_RXREACTION + "( " + COLUMN_REACTION_ID
			+ " ) ON DELETE CASCADE)";

	/*
	 * RX_STIMULI
	 */
	private static final String TABLE_RX_STIMULI = "rx_stimuli";
	public static final String COLUMN_STIMULUS_ID = "rx_stimulus_id";
	public static final String COLUMN_SIMULUS_ACTION_NAME = "rx_stimulus_action_name";
	private static final String COLUMN_ACTION_STATE_VALUE = "rx_stimulus_action_state_value";

	private static final String CREATE_RX_STIMULI = "create table IF NOT EXISTS " + TABLE_RX_STIMULI + " ("
			+ COLUMN_STIMULUS_ID + " integer primary key autoincrement, " + COLUMN_SIMULUS_ACTION_NAME
			+ " text not null)";

	/*
	 * RX_STIMULI_PROPERTY
	 */
	private static String TABLE_RX_STIMULI_PROPERTY = "rx_stimuli_property";
	public static String COLUMN_RX_STIMULI_PROPERTY_ID = "rx_stimuli_property_id";
	public static String COLUMN_RX_STIMULI_PROPERTY_NAME = "rx_stimuli_property_name";
	public static String COLUMN_RX_STIMULI_PROPERTY_VALUE = "rx_stimuli_property_value";
	public static final String COLUMN_RX_STIUMLI_ID = "fk_rx_stimuli";

	private static final String CREATE_RX_STIMULI_PROPERTY = "create table IF NOT EXISTS " + TABLE_RX_STIMULI_PROPERTY
			+ " (" + COLUMN_RX_STIMULI_PROPERTY_ID + " integer primary key autoincrement, " + COLUMN_RX_STIUMLI_ID
			+ " integer, " + COLUMN_RX_STIMULI_PROPERTY_NAME + " text not null, " + COLUMN_RX_STIMULI_PROPERTY_VALUE
			+ " text, " + "FOREIGN KEY (" + COLUMN_RX_STIUMLI_ID + ") REFERENCES " + TABLE_RX_STIMULI + "( "
			+ COLUMN_STIMULUS_ID + " ) ON DELETE CASCADE)";

	/*
	 * RX_REFLEX
	 */
	private static final String TABLE_RX_REFLEX = "rx_reflex";
	public static final String COLUMN_RX_REFLEX_ID = "rx_reflex_id";
	public static final String COLUMN_RX_REFLEX_NAME = "rx_reflex_name";

	private static final String CREATE_RX_REFLEX = "create table IF NOT EXISTS " + TABLE_RX_REFLEX + " ("
			+ COLUMN_RX_REFLEX_ID + " integer primary key autoincrement, " + COLUMN_RX_STIUMLI_ID
			+ " integer not null, " + COLUMN_RX_REACTION_ID + " integer not null, " + COLUMN_RX_REFLEX_NAME
			+ " text not null, " + "FOREIGN KEY (" + COLUMN_RX_STIUMLI_ID + ") REFERENCES " + TABLE_RX_STIMULI + "( "
			+ COLUMN_STIMULUS_ID + " ) ON DELETE CASCADE, " + "FOREIGN KEY (" + COLUMN_RX_REACTION_ID + ") REFERENCES "
			+ TABLE_RXREACTION + "( " + COLUMN_REACTION_ID + " ) ON DELETE CASCADE);";

	public RXDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, DATABASE_NAME, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_RX_STIMULI_PROPERTY);
		db.execSQL(CREATE_RX_REACTION_PROPERTY);
		db.execSQL(CREATE_RX_STIMULI);
		db.execSQL(CREATE_RXREACTION);
		db.execSQL(CREATE_RX_REFLEX);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
			// db.setForeignKeyConstraintsEnabled(true);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_RXPROPERTY);
		// onCreate(db);
	}

	// **************INSERT METHODS***************
	public int insertRxReactionProperty(SQLiteDatabase db, RXReactionProperty property, int reactionId) {
		ContentValues cv = property.toContentValues();
		cv.put(COLUMN_RX_REACTION_ID, reactionId);

		return (int) db.insertOrThrow(TABLE_RXPROPERTY, null, cv);
	}

	public int insertRxStimuliProperty(SQLiteDatabase db, RXStimuliCondition property, int stimuliId) {
		ContentValues cv = property.toContentValues();
		cv.put(COLUMN_RX_STIUMLI_ID, stimuliId);

		return (int) db.insertOrThrow(TABLE_RX_STIMULI_PROPERTY, null, cv);
	}

	public int insertRxStimuli(SQLiteDatabase db, RXStimuli stimuli) {
		return (int) db.insertOrThrow(TABLE_RX_STIMULI, null, stimuli.toContentValues());
	}

	public int insertRxReaction(SQLiteDatabase db, RXReaction reaction) {
		return (int) db.insertOrThrow(TABLE_RXREACTION, null, reaction.toContentValues());
	}

	public int insertRxReflex(SQLiteDatabase db, RXReflex reflex) {
		int reflexId = -1;

		RXStimuli stimuli = reflex.getStimuli();
		RXReaction reaction = reflex.getReaction();

		try {
			db.beginTransaction();

			// insert stimuli and then when we have the ID, insert the stimuli
			// properties which depend on stimuli
			int stimuliId = insertRxStimuli(db, stimuli);

			for (RXStimuliCondition sp : stimuli.getConditionList()) {
				insertRxStimuliProperty(db, sp, stimuliId);
			}

			// insert reaction and then when we have the ID, insert the reaction
			// properties which depend on reaction
			int reactionId = insertRxReaction(db, reaction);

			for (RXReactionProperty rp : reaction.getParamList()) {
				insertRxReactionProperty(db, rp, reactionId);
			}

			// insert reflex with stimuli and reaction id
			ContentValues reflexCV = reflex.toContentValues();
			reflexCV.put(COLUMN_RX_STIUMLI_ID, stimuliId);
			reflexCV.put(COLUMN_RX_REACTION_ID, reactionId);

			reflexId = (int) db.insertOrThrow(TABLE_RX_REFLEX, null, reflexCV);

			db.setTransactionSuccessful();
		} catch (SQLException ex) {
			if (RXUtil.isDebugMode) {
				Log.d(RXUtil.APP_TAG, "****insert reflex failed****");
			}
		} finally {
			db.endTransaction();
		}

		return reflexId;
	}

	// **************DELETE METHOD***************

	// tu bi trebalo sve lijepo se brisati sa foreign key constraintima - izvuc
	// bazu iz emulatora i probat
	public int deleteRxReactionProperty(SQLiteDatabase db, int id) {
		return db.delete(TABLE_RXPROPERTY, COLUMN_RXPROPERTY_ID + " = ?", new String[] { String.valueOf(id) });
	}

	public int deleteRxStimuliProperty(SQLiteDatabase db, int id) {
		return db.delete(TABLE_RX_STIMULI_PROPERTY, COLUMN_RX_STIMULI_PROPERTY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	public int deleteRxReaction(SQLiteDatabase db, int id) {
		return db.delete(TABLE_RXREACTION, COLUMN_REACTION_ID + " = ?", new String[] { String.valueOf(id) });
	}

	public int deleteRxStimuli(SQLiteDatabase db, int id) {
		return db.delete(TABLE_RX_STIMULI, COLUMN_STIMULUS_ID + " = ?", new String[] { String.valueOf(id) });
	}

	public int deleteRxReflex(SQLiteDatabase db, int id) {
		return db.delete(TABLE_RX_REFLEX, COLUMN_RXPROPERTY_ID + " = ?", new String[] { String.valueOf(id) });
	}

	// **************UPDATE METHOD***************
	// tu ce mozda smetati sto u content values ima i id, treba testirati
	public int updateRxReactionProperty(SQLiteDatabase db, RXReactionProperty property) {
		return db.update(TABLE_RXPROPERTY, property.toContentValues(), COLUMN_RXPROPERTY_ID + " = ?",
				new String[] { String.valueOf(property.getId()) });
	}

	// ako je id -1 onda insertaj -- znaci treba mi jos id Stimulija --> ovu
	// logiku cu raditi u updateStimuli
	// ako nije -1 onda samo updateaj vrijednosti
	public int updateRxStimuliProperty(SQLiteDatabase db, RXStimuliCondition property) {
		return db.update(TABLE_RX_STIMULI_PROPERTY, property.toContentValues(), COLUMN_RX_STIMULI_PROPERTY_ID + " = ?",
				new String[] { String.valueOf(property.getId()) });
	}

	public int updateRxReaction(SQLiteDatabase db, RXReaction reaction) {
		return db.update(TABLE_RXREACTION, reaction.toContentValues(), COLUMN_REACTION_ID + " = ?",
				new String[] { String.valueOf(reaction.getId()) });
	}

	public int updateRxStimuli(SQLiteDatabase db, RXStimuli stimuli) {
		return db.update(TABLE_RX_STIMULI, stimuli.toContentValues(), COLUMN_STIMULUS_ID + " = ?",
				new String[] { String.valueOf(stimuli.getId()) });
	}

	public boolean updateRxReflex(SQLiteDatabase db, RXReflex reflex) {
		boolean reflexUpdated = false;

		RXStimuli stimuli = reflex.getStimuli();
		RXReaction reaction = reflex.getReaction();

		try {
			db.beginTransaction();

			for (RXStimuliCondition sp : stimuli.getConditionList()) {
				if (sp.getId() == NEW_ITEM) { // this is a newly added property
												// and therefore it has to be
												// inserted
					insertRxStimuliProperty(db, sp, stimuli.getId());
				} else { // item exists in the database and it should be updated
					updateRxStimuliProperty(db, sp);
				}
			}

			for (RXReactionProperty rp : reaction.getParamList()) {
				if (rp.getId() == NEW_ITEM) {
					insertRxReactionProperty(db, rp, reaction.getId());
				} else {
					updateRxReactionProperty(db, rp);
				}

			}

			updateRxStimuli(db, stimuli);

			updateRxReaction(db, reaction);

			if (db.update(TABLE_RX_REFLEX, reflex.toContentValues(), COLUMN_RX_REFLEX_ID + " = ?",
					new String[] { String.valueOf(reflex.getId()) }) == 1) {
				reflexUpdated = true;
			}

			db.setTransactionSuccessful();
		} catch (SQLException ex) {
			if (RXUtil.isDebugMode) {
				Log.d(RXUtil.APP_TAG, "****update reflex failed****");
			}
		} finally {
			db.endTransaction();
		}
		return reflexUpdated;
	}

	public Cursor queryALLReflexs(SQLiteDatabase db) {
		try {
			Cursor c = db.rawQuery(" SELECT " + COLUMN_RX_REFLEX_ID + ", " + "TRF." + COLUMN_RX_STIUMLI_ID + ", "
					+ "TRF." + COLUMN_RX_REACTION_ID + ", " + COLUMN_RX_REFLEX_NAME + ", "
					+ COLUMN_RX_STIMULI_PROPERTY_ID + ", " + COLUMN_RX_STIMULI_PROPERTY_NAME + ", "
					+ COLUMN_RX_STIMULI_PROPERTY_VALUE + ", " + COLUMN_SIMULUS_ACTION_NAME + ", "
					+ COLUMN_RXPROPERTY_ID + ", " + COLUMN_RXPROPERTY_NAME + ", " + COLUMN_RXPROPERTY_VALUE + ", "
					+ COLUMN_REACTION_NAME + " FROM " + TABLE_RX_REFLEX + " TRF " + " LEFT OUTER JOIN "
					+ TABLE_RX_STIMULI + " ON " + "TRF." + COLUMN_RX_STIUMLI_ID + " = " + COLUMN_STIMULUS_ID
					+ " LEFT OUTER JOIN " + TABLE_RXREACTION + " TRA " + " ON " + "TRF." + COLUMN_RX_REACTION_ID
					+ " = " + COLUMN_REACTION_ID + " LEFT OUTER JOIN " + TABLE_RX_STIMULI_PROPERTY + " SP " + " ON "
					+ COLUMN_STIMULUS_ID + "=" + "SP." + COLUMN_RX_STIUMLI_ID + " LEFT OUTER JOIN " + TABLE_RXPROPERTY
					+ " TRP " + " ON " + COLUMN_REACTION_ID + "=" + "TRP." + COLUMN_RX_REACTION_ID, null);
			return c;
		} catch (Exception e) {
			return null;
		}
	}

}
