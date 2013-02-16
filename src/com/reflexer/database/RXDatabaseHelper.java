package com.reflexer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RXDatabaseHelper extends SQLiteOpenHelper {

	//TODO: izvuc ovu inicijalizaciju u assets
	
	private static String DATABASE_NAME = "reflexer.db";
	private static String DATABASE_VERSION = "1";

	private static String TABLE_RXPROPERTY = "rx_property";
	private static String COLUMN_ID = "id";
	private static String COLUMN_NAME = "name";
	private static String COLUMN_VALUE = "value";

	/*
	 * RXREACTION  
	 */
	
	private static final String TABLE_RXREACTION = "rx_reaction";
	private static final String COLUMN_ACTION_NAME = "action_name";
	private static final String COLUMN_ACTION_ID = "action_id";

	private static final String CREATE_RXREACTION = "CREATE TABLE " 
			+ TABLE_RXREACTION + " (" + COLUMN_ACTION_ID 
			+ " integer primary key autoincrement, " + COLUMN_ACTION_NAME 
			+ " text not null);"; 
	
	/*
	 * RXPROPERTY
	 */
	
	private static final String CREATE_RXPROPERTY = "create table "
			+ TABLE_RXPROPERTY + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " text not null, " + COLUMN_VALUE + " text);";
	
	private static final String TABLE_RX_ACTION_PROPERTY = "rx_action_property";
	private static final String COLUMN_REACTION_ID = "rx_reaction_id";
	private static final String COLUMN_PROPERTY_ID = "rx_property_id";
	
	/*
	 * RX_REACTION_PROPERTY
	 */
	
	private static final String CREATE_RX_REACTION_PROPERTY = "create table " 
			+ TABLE_RX_ACTION_PROPERTY + " (" + COLUMN_REACTION_ID 
			+ " integer, " + COLUMN_PROPERTY_ID 
			+ " integer, " 
			+ " primarykey(" + COLUMN_REACTION_ID + ", " + COLUMN_PROPERTY_ID +");";
	
	
	/*
	 * RX_STIMULUS
	 */
	private static final String TABLE_RX_STIMULUS = "rx_stimulus";
	private static final String COLUMN_STIMULUS_ID = "rx_stimulus_id";
	private static final String COLUMN_SIMULUS_ACTION_NAME = "rx_stimulus_action_name";
	private static final String COLUMN_ACTION_STATE_VALUE = "rx_stimulus_action_state_value";
	
	private static final String CREATE_RX_STIMULUS = "create table "
			+ TABLE_RX_STIMULUS + " (" + COLUMN_STIMULUS_ID 
			+ " integer primary key autoincrement, " + COLUMN_SIMULUS_ACTION_NAME 
			+ " text not null, " + COLUMN_ACTION_STATE_VALUE 
			+ " text not null );"; 
	//TODO: u stimulusu nedostaje jos dodatni uvjet koji se tice optional dijela. Recimo wifi network je preduzece. 
 	
	private static final String DATABASE_CREATE = null; 
	
	
	public RXDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DATABASE_NAME, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RXPROPERTY);
//	    onCreate(db);
	}

}
