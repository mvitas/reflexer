package com.reflexer.model;

import android.content.ContentValues;
import android.content.Context;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.model.parser.RXStimuliDefinitionParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RXStimuli {

	/**
	 * Path of the stimuli XML definitions in assets folder.
	 */
	private static final String STIMULI_PATH = "stimuli";

	/**
	 * ID of this stimuli in the database.
	 */
	private int id;

	/**
	 * Values for the conditions of this stimuli, based on the Conditions
	 * definitions.
	 */
	protected HashMap<String, Object> currentConditionState = new HashMap<String, Object>();

	/**
	 * State of conditions defined by user.
	 */
	private ArrayList<RXStimuliCondition> conditionList = new ArrayList<RXStimuliCondition>();

	private IRXReflexListener reflexListener;

	/**
	 * Meta data that contains definitions of all the conditions for this
	 * stimuli and the associated RXHandler for this stimuli.
	 */
	private final RXStimuliDefinition definition;

	/**
	 * Holds array of all the RXStimuli definitions read from XML files in
	 * assets.
	 */
	private static ArrayList<RXStimuliDefinition> stimuliDefinitions;

	public static ArrayList<RXStimuliDefinition> getStimuliDefinitions(Context context) throws IOException {
		if (stimuliDefinitions == null) {
			loadStimuli(context);
		}

		return stimuliDefinitions;
	}

	private static RXStimuliDefinition getStimuliDefinitionByName(Context context, String name) {
		try {
			ArrayList<RXStimuliDefinition> definitions = getStimuliDefinitions(context);

			for (int i = 0; i < definitions.size(); i++) {
				if (definitions.get(i).getName().equals(name)) {
					return definitions.get(i);
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException("Error reading stimuli definitions", e);
		}

		throw new IllegalArgumentException("There is no definiton for stimuli with name: " + name);
	}

	/**
	 * Loads available stimuli list.
	 * 
	 * @throws IOException
	 */
	private static void loadStimuli(Context context) throws IOException {
		String[] stimuliFileList = context.getAssets().list(STIMULI_PATH);

		RXStimuliDefinitionParser parser = new RXStimuliDefinitionParser();
		stimuliDefinitions = new ArrayList<RXStimuliDefinition>();

		for (int i = 0; i < stimuliFileList.length; i++) {
			try {
				stimuliDefinitions.add(parser.parse(context.getAssets().open(STIMULI_PATH + "/" + stimuliFileList[i])));
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

	public RXStimuli(Context context, String name) {
		this(context, RXDatabaseHelper.NEW_ITEM, name);
	}

	public RXStimuli(Context context, int id, String name) {
		this(context, id, name, new ArrayList<RXStimuliCondition>());
	}

	public RXStimuli(Context context, int id, String name, ArrayList<RXStimuliCondition> conditionList) {
		this.id = id;
		this.definition = getStimuliDefinitionByName(context, name);
		this.conditionList = conditionList;
	}

	/**
	 * Checks if all the conditions that must be set in order to be able to set
	 * the given condition (by name) are set.
	 * 
	 * @param conditionName
	 *            name of the condition to check
	 * @return true if all the preconditions are set
	 */
	protected boolean arePreconditionsMet(String conditionName) {
		RXConditionDefinition condition = RXConditionDefinition.getConditionDefinitionByName(conditionName,
				definition.getConditionDefinitons());

		ArrayList<RXConditionDefinition> preconditions = condition.getDependsOn();

		for (int i = 0; i < preconditions.size(); i++) {
			if (!currentConditionState.containsKey(preconditions.get(i).getName())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns true if this condition is fulfilled.
	 * 
	 * @return
	 */
	public boolean isFulfilled() {

		for (int i = 0; i < conditionList.size(); i++) {
			RXStimuliCondition condition = conditionList.get(i);

			Object currentState = currentConditionState.get(condition.getName());

			if (!condition.getValue().equals(currentState)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Sets the condition with the given name.
	 * <p>
	 * If the given condition cannot be set because all the conditions that it
	 * depends on haven't been set, IllegalStateException is thrown.
	 * <p>
	 * If an non existing name of the condition is given, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param conditionName
	 * @param value
	 */
	public void setConditionCurrentState(String conditionName, Object value) {
		if (!arePreconditionsMet(conditionName)) {
			throw new IllegalStateException("Preconditions for " + conditionName + " are not all set");
		}

		RXStimuliCondition condition = getConditionByName(conditionName);

		if (condition == null) {
			return; // condition has not been set so ignore it
		}

		Object lastValue = currentConditionState.get(conditionName);

		if (value.equals(lastValue)) {
			return; // condition has not changed, make sure it doesn't trigger
					// isFulfilled
		}

		currentConditionState.put(conditionName, value);

		boolean isFulfilled = isFulfilled();

		if (isFulfilled && reflexListener != null) {
			reflexListener.onStimulate();
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
	public boolean setCondition(RXStimuliCondition condition) {
		boolean shouldAdd = true;

		for (RXStimuliCondition c : getConditionList()) {
			if (c.getName().equals(condition.getName())) {
				c.setValue(condition.getValue());
				shouldAdd = false;
			}
		}
		if (shouldAdd) {
			getConditionList().add(condition);
		}

		return shouldAdd;
	}

	public void setReflexListener(IRXReflexListener reflexListener) {
		this.reflexListener = reflexListener;
	}

	public RXStimuliDefinition getDefinition() {
		return definition;
	}

	/**
	 * Returns the property for the selected id
	 * 
	 * @param id
	 * @return
	 */
	public RXStimuliCondition getRXParamById(int id) {
		RXStimuliCondition property = null;

		for (RXStimuliCondition param : getConditionList()) {
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
	public Object getConditionCurrentState(String name) {

		RXProperty property = null;

		for (RXProperty param : getConditionList()) {
			if (param.getName() == name) {
				property = param;
			}
		}
		return property;
	}

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
			cv.put(RXDatabaseHelper.COLUMN_STIMULUS_ID, getId());
		}
		cv.put(RXDatabaseHelper.COLUMN_SIMULUS_ACTION_NAME, definition.getName());

		return cv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RXStimuliCondition getConditionByName(String name) {
		for (RXStimuliCondition condition : conditionList) {
			if (condition.getName().equals(name)) {
				return condition;
			}
		}

		return null;
	}

	public ArrayList<RXStimuliCondition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(ArrayList<RXStimuliCondition> conditionList) {
		this.conditionList = conditionList;
	}

	public void addRXParam(RXStimuliCondition param) {
		boolean shouldAdd = true;

		for (RXStimuliCondition p : conditionList) {
			if (p.getName().equals(param.getName())) {
				p.setValue(param.getValue());
				shouldAdd = false;
				break;
			}
		}
		if (shouldAdd) {
			conditionList.add(param);
		}

	}

	/**
	 * Gets the object type for the specified definition name
	 * 
	 * Throws IllegalStateException if definitions can't be loaded or type doesn't match any of the specified types in the definition
	 * 
	 * @param context
	 * @param stimuliName
	 * @param conditionDefinitionName
	 * @return
	 */
	public static int getStimuliConditionType(Context context,
			String stimuliName, String conditionDefinitionName) {
		
		try {
			getStimuliDefinitions(context);
		} catch (IOException e) {
			throw new IllegalStateException("Unable to load Stimuli Definition data");
		}
		
		ArrayList<RXConditionDefinition> conditionDefinitionList = new ArrayList<RXConditionDefinition>(); 
		
		for (RXStimuliDefinition def : stimuliDefinitions){
			if (def.getName().equals(stimuliName)){
				conditionDefinitionList = def.getConditionDefinitons();
				break;
			}
		}
		
		int type = -1;
		
		for (RXConditionDefinition condDef : conditionDefinitionList){
			if(condDef.getName().equals(conditionDefinitionName)){
				type = condDef.getType();
				break;
			}
		}
		
		if (type == -1){
			throw new IllegalStateException("type doesn't match ConditionDefinition type"); 
		}
		
		return type;
	}
}