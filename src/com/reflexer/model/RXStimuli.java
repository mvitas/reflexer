package com.reflexer.model;

import com.reflexer.handler.RXHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class RXStimuli {

	/**
	 * List of all the conditions for this stimuli.
	 */
	protected ArrayList<RXCondition> conditionList;

	protected RXHandler handler;

	/**
	 * Values for the conditions of this stimuli, based on the Conditions
	 * definitions.
	 */
	protected HashMap<String, Object> conditionsMap = new HashMap<String, Object>();

	/**
	 * Current state of the conditions defined by this stimuli.
	 */
	protected HashMap<String, Object> stateMap = new HashMap<String, Object>();

	/**
	 * Reaction to this stimuli.
	 */
	protected RXReaction reaction;

	/**
	 * Name of this stimuli.
	 */
	protected String name;

	public void setReaction(RXReaction reaction) {
		this.reaction = reaction;
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
		RXCondition condition = RXCondition.getConditionDefinitionByName(conditionName, conditionList);

		ArrayList<RXCondition> preconditions = condition.getDependsOn();

		for (int i = 0; i < preconditions.size(); i++) {
			if (!conditionsMap.containsKey(preconditions.get(i).getName())) {
				return false;
			}
		}

		return true;
	}

	public void setConditionList(ArrayList<RXCondition> conditionList) {
		this.conditionList = conditionList;
	}

	/**
	 * List of existing condition fields required to generate the UI and fill
	 * this stimuli data. This is the metadata that defines the structure of
	 * this stimuli.
	 * 
	 * @return
	 */
	public ArrayList<RXCondition> getConditionList() {
		return conditionList;
	}

	/**
	 * Sets RXHandler extending class that handles this stimuli.
	 * 
	 * @param handler
	 */
	public void setRXHandler(RXHandler handler) {
		this.handler = handler;
	}

	/**
	 * Returns RXHandler extending class that handles this stimuli.
	 * 
	 * @return
	 */
	public RXHandler getRXHandler() {
		return handler;
	}

	/**
	 * Returns true if this condition is fulfilled.
	 * 
	 * @return
	 */
	public boolean isFulfilled() {

		for (String conditionName : conditionsMap.keySet()) {
			Object conditionValue = conditionsMap.get(conditionName);
			Object currentState = stateMap.get(conditionName);

			RXCondition condition = RXCondition.getConditionDefinitionByName(conditionName, conditionList);

			if (currentState == null && !condition.isRequired()) {
				continue;
			}

			if (!conditionValue.equals(currentState)) {
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
	public void setCondition(String conditionName, Object value) {
		if (!arePreconditionsMet(conditionName)) {
			throw new IllegalStateException("Preconditions for " + conditionName + " are not all set");
		}

		conditionsMap.put(conditionName, value);
	}

	/**
	 * Sets the state of the condition with the given name, and checks if all
	 * set conditions are met. If conditions are met, returns true.
	 * 
	 * @param conditionName
	 * @param state
	 * @return
	 */
	public boolean setConditionState(String conditionName, Object state) {
		stateMap.put(conditionName, state);

		boolean isFulfilled = isFulfilled();

		if (isFulfilled) {

		}

		return isFulfilled;
	}

	/**
	 * Sets the name of this stimuli.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of this stimuli.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
}