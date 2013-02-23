package com.reflexer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RXDatabaseHelper extends SQLiteOpenHelper {

	// TODO: izvuc ovu inicijalizaciju u assets

	private static String DATABASE_NAME = "reflexer.db";
	private static String DATABASE_VERSION = "1";

	/*
	 * RXREACTION
	 */

	private static final String TABLE_RXREACTION = "rx_reaction";
	public static final String COLUMN_REACTION_NAME = "rx_reaction_name";
	public static final String COLUMN_REACTION_ID = "rx_reaction_id";

	private static final String CREATE_RXREACTION = "CREATE TABLE IF NOT EXISTS"
			+ TABLE_RXREACTION + " (" + COLUMN_REACTION_ID
			+ " integer primary key autoincrement, " + COLUMN_REACTION_NAME
			+ " text not null)";

	/*
	 * RX_REACTION_PROPERTY
	 */
	private static String TABLE_RXPROPERTY = "rx_property";
	public static String COLUMN_RXPROPERTY_ID = "rx_property_id";
	public static String COLUMN_RXPROPERTY_NAME = "rx_property_name";
	public static String COLUMN_RXPROPERTY_VALUE = "rx_property_value";
	private static final String COLUMN_RX_REACTION_ID = "fk_rx_reaction";

	private static final String CREATE_RX_REACTION_PROPERTY = "create table IF NOT EXISTS "
			+ TABLE_RXPROPERTY + " (" + COLUMN_RXPROPERTY_ID
			+ " integer primary key autoincrement, " + COLUMN_RX_REACTION_ID
			+ " integer, " + COLUMN_RXPROPERTY_NAME + " text not null, "
			+ COLUMN_RXPROPERTY_VALUE + " text, " + "FOREIGN KEY ("
			+ COLUMN_RX_REACTION_ID + ") REFERENCES " + TABLE_RXREACTION + "( "
			+ COLUMN_REACTION_ID + " ) ON DELETE CASCADE)";

	/*
	 * RX_STIMULI
	 */
	private static final String TABLE_RX_STIMULI = "rx_stimuli";
	private static final String COLUMN_STIMULUS_ID = "rx_stimulus_id";
	private static final String COLUMN_SIMULUS_ACTION_NAME = "rx_stimulus_action_name";
	private static final String COLUMN_ACTION_STATE_VALUE = "rx_stimulus_action_state_value";

	private static final String CREATE_RX_STIMULI = "create table IF NOT EXISTS "
			+ TABLE_RX_STIMULI + " (" + COLUMN_STIMULUS_ID
			+ " integer primary key autoincrement, "
			+ COLUMN_SIMULUS_ACTION_NAME + " text not null)";

	/*
	 * RX_STIMULI_PROPERTY
	 */
	private static String TABLE_RX_STIMULI_PROPERTY = "rx_stimuli_property";
	private static String COLUMN_RX_STIMULI_PROPERTY_ID = "rx_stimuli_property_id";
	private static String COLUMN_RX_STIMULI_PROPERTY_NAME = "rx_stimuli_property_name";
	private static String COLUMN_RX_STIMULI_PROPERTY_VALUE = "rx_property_value";
	private static final String COLUMN_RX_STIUMLI_ID = "fk_rx_stimuli";

	private static final String CREATE_RX_STIMULI_PROPERTY = "create table IF NOT EXISTS "
			+ TABLE_RX_STIMULI_PROPERTY + " (" + COLUMN_RX_STIMULI_PROPERTY_ID
			+ " integer primary key autoincrement, " + COLUMN_RX_STIUMLI_ID
			+ " integer, " + COLUMN_RX_STIMULI_PROPERTY_NAME
			+ " text not null, " + COLUMN_RX_STIMULI_PROPERTY_VALUE + " text, "
			+ "FOREIGN KEY (" + COLUMN_RX_STIUMLI_ID + ") REFERENCES "
			+ TABLE_RX_STIMULI + "( " + COLUMN_STIMULUS_ID
			+ " ) ON DELETE CASCADE)";

	/*
	 * RX_REFLEX
	 */
	private static final String TABLE_RX_REFLEX = "rx_reflex";
	private static final String COLUMN_RX_REFLEX_ID = "rx_reflex_id";
	private static final String COLUMN_RX_REFLEX_NAME = "rx_reflex_name";

	private static final String CREATE_RX_REFLEX = "create table IF NOT EXISTS "
			+ TABLE_RX_REFLEX + " (" + COLUMN_RX_REFLEX_ID
			+ " integer primary key autoincrement, " + COLUMN_RX_STIUMLI_ID
			+ " integer not null, " + COLUMN_RX_REACTION_ID
			+ " integer not null, " + COLUMN_RX_REFLEX_NAME
			+ " text not null, " + "FOREIGN KEY (" + COLUMN_RX_STIUMLI_ID
			+ ") REFERENCES " + TABLE_RX_STIMULI + "( " + COLUMN_STIMULUS_ID
			+ " ) ON DELETE CASCADE, " + "FOREIGN KEY ("
			+ COLUMN_RX_REACTION_ID + ") REFERENCES " + TABLE_RXREACTION + "( "
			+ COLUMN_REACTION_ID + " ) ON DELETE CASCADE);";

	public RXDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
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
//			db.setForeignKeyConstraintsEnabled(true);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_RXPROPERTY);
		// onCreate(db);
	}
	
	//**************INSERT METHODS***************
//	public int insertRxReactionProperty(){
//		
//	}

}
