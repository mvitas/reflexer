package com.reflexer.model;

import android.content.ContentValues;
import android.content.Context;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.model.parser.RXReactionDefinitionParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public abstract class RXReaction {

	/**
	 * Path of the reaction XML definitions in assets folder.
	 */
	private static final String REACTIONS_PATH = "reactions";

	/**
	 * ID of this reaction in the database.
	 */
	private int id;

	/**
	 * Meta data that contains definitions of all the properties for this
	 * reaction.
	 */
	private RXReactionDefinition definition;

	/**
	 * State of properties defined by user.
	 */
	private ArrayList<RXReactionProperty> paramList = new ArrayList<RXReactionProperty>();

	/**
	 * Holds array of all the RXReaction definitions read from XML files in
	 * assets.
	 */
	private static ArrayList<RXReactionDefinition> reactionDefinitions;

	public static ArrayList<RXReactionDefinition> getReactionDefinitions(Context context) throws IOException {
		if (reactionDefinitions == null) {
			loadReactions(context);
		}

		return reactionDefinitions;
	}

	private static RXReactionDefinition getReactionDefinitionByName(Context context, String name) {
		try {
			ArrayList<RXReactionDefinition> definitions = getReactionDefinitions(context);

			for (int i = 0; i < definitions.size(); i++) {
				if (definitions.get(i).getName().equals(name)) {
					return definitions.get(i);
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException("Error reading reaction definitions", e);
		}

		throw new IllegalArgumentException("There is no definition for reaction with name: " + name);
	}

	protected RXReactionProperty getParamByName(String name) {
		for (int i = 0; i < paramList.size(); i++) {
			if (paramList.get(i).getName().equals(name)) {
				return paramList.get(i);
			}
		}

		return null;
	}

	private static void loadReactions(Context context) throws IOException {
		String[] reactionFileList = context.getAssets().list(REACTIONS_PATH);

		RXReactionDefinitionParser parser = new RXReactionDefinitionParser();
		reactionDefinitions = new ArrayList<RXReactionDefinition>();

		for (int i = 0; i < reactionFileList.length; i++) {
			try {
				reactionDefinitions.add(parser.parse(context.getAssets().open(
						REACTIONS_PATH + "/" + reactionFileList[i])));
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static RXReaction createReaction(Context context, String name) {
		return createReaction(context, RXDatabaseHelper.NEW_ITEM, name);
	}

	public static RXReaction createReaction(Context context, int id, String name) {
		return createReaction(context, id, name, new ArrayList<RXReactionProperty>());
	}

	public static RXReaction createReaction(Context context, int id, String name,
			ArrayList<RXReactionProperty> propertyList) {
		RXReactionDefinition definition = getReactionDefinitionByName(context, name);
		Class<? extends RXReaction> reactionClass = definition.getReactionClass();

		RXReaction reaction = null;
		try {
			reaction = reactionClass.newInstance();
			reaction.id = id;
			reaction.definition = definition;
			reaction.paramList = propertyList;
			return reaction;
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}

	public static RXReaction createReaction(RXReactionDefinition definition) {
		Class<? extends RXReaction> reactionClass = definition.getReactionClass();

		RXReaction reaction = null;

		try {
			reaction = reactionClass.newInstance();
			reaction.id = RXDatabaseHelper.NEW_ITEM;
			reaction.definition = definition;
			reaction.paramList = new ArrayList<RXReactionProperty>();
			return reaction;
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}

	/**
	 * Adds the property to the list of properties
	 * 
	 * Checks if the param already exists - then it updates it If it doesn't
	 * exist it adds it to the list
	 * 
	 * @param param
	 */
	public void addParam(RXReactionProperty param) {
		boolean shouldAdd = true;

		for (RXReactionProperty p : paramList) {
			if (p.getName().equals(param.getName())) {
				p.setValue(param.getValue());
				shouldAdd = false;
				break;
			}
		}
		if (shouldAdd) {
			paramList.add(param);
		}
	}

	/**
	 * Sets the state of the condition with the given name, and checks if all
	 * set conditions are met. If conditions are met, returns true.
	 * 
	 * @param conditionName
	 * @param state
	 * @return
	 */
	public boolean setParam(RXReactionProperty param) {
		boolean shouldAdd = true;

		for (RXReactionProperty p : getParamList()) {
			if (p.getName().equals(param.getName())) {
				p.setValue(param.getValue());
				shouldAdd = false;
			}
		}
		if (shouldAdd) {
			getParamList().add(param);
		}

		return shouldAdd;
	}

	public RXReactionDefinition getDefinition() {
		return definition;
	}

	/**
	 * Returns the property for the seleced id
	 * 
	 * @param id
	 * @return
	 */
	public RXProperty getRXParamById(int id) {
		RXProperty property = null;

		for (RXProperty param : paramList) {
			if (param.getId() == id) {
				property = param;
			}
		}
		return property;
	}

	/**
	 * Returns the property for name
	 * 
	 * @param name
	 * @return
	 */
	public RXProperty getRXParamByName(String name) {
		RXProperty property = null;

		for (RXProperty param : paramList) {
			if (param.getName() == name) {
				property = param;
			}
		}
		return property;
	}

	/**
	 * Get all params
	 * 
	 * @return
	 */
	public ArrayList<RXReactionProperty> getParamList() {
		return paramList;
	}

	public abstract void execute();

	/**
	 * Creates CVs for "id" and "name"
	 * 
	 * Params are created from the
	 * 
	 * @return
	 */
	public ContentValues toContentValues() {
		ContentValues cv = new ContentValues();
		if (getId() != -1) {
			cv.put(RXDatabaseHelper.COLUMN_REACTION_ID, getId());
		}
		cv.put(RXDatabaseHelper.COLUMN_REACTION_NAME, definition.getName());

		return cv;
	}

	public static int getReactionPropertyType(Context context, String reactionName, String propertyDefinitionName) {

		RXReactionDefinition reactionDefinition = getReactionDefinitionByName(context, reactionName);

		ArrayList<RXPropertyDefinition> reactionPropertyDefinitions = reactionDefinition.getPropertyDefinitions();

		int type = -1;

		for (RXPropertyDefinition def : reactionPropertyDefinitions) {
			if (def.getName().equals(propertyDefinitionName)) {
				type = def.getType();
				break;
			}
		}

		if (type == -1) {
			throw new IllegalStateException("type doesn't match ConditionDefinition type");
		}

		return type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
