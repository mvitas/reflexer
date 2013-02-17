package com.reflexer.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class RXStimuli {

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
	 * Checks if all the conditions that must be set in order to be able to set
	 * the given condition (by name) are set.
	 * 
	 * @param conditionName
	 *            name of the condition to check
	 * @return true if all the preconditions are set
	 */
	protected boolean arePreconditionsMet(String conditionName) {
		RXCondition condition = getConditionDefinitionByName(conditionName);

		ArrayList<RXCondition> preconditions = condition.getDependsOn();

		for (int i = 0; i < preconditions.size(); i++) {
			if (!conditionsMap.containsKey(preconditions.get(i).getName())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns RXCondition that defines a condition by given name.
	 * <p>
	 * Throws IllegalArgumentException if there is no condition defined with the
	 * given name.
	 * 
	 * @param conditionName
	 *            name of the condition
	 * @return
	 */
	protected RXCondition getConditionDefinitionByName(String conditionName) {
		ArrayList<RXCondition> conditionDefinitions = getConditionList();

		for (int i = 0; i < conditionDefinitions.size(); i++) {
			if (conditionDefinitions.get(i).getName().equals(conditionName)) {
				return conditionDefinitions.get(i);
			}
		}

		throw new IllegalArgumentException("Condition with the name " + conditionName + " id not defined");
	}

	/**
	 * List of existing condition fields required to generate the UI and fill
	 * this stimuli data. This is the metadata that defines the structure of
	 * this stimuli.
	 * 
	 * @return
	 */
	public abstract ArrayList<RXCondition> getConditionList();

	/**
	 * Returns true if this condition is fulfilled.
	 * 
	 * @return
	 */
	public boolean isFulfilled() {

		for (String conditionName : conditionsMap.keySet()) {
			Object conditionValue = conditionsMap.get(conditionName);
			Object currentState = stateMap.get(conditionName);

			if (!conditionValue.equals(currentState)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Sets the state of the condition with the given name, and checks if all
	 * set conditions are met. If conditions are met, returns true.
	 * <p>
	 * If the given condition cannot be set because all the conditions that it
	 * depends on haven't been set, IllegalStateException is thrown.
	 * <p>
	 * If an non existing name of the condition is given, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param conditionName
	 * @param state
	 * @return
	 */
	public boolean setConditionState(String conditionName, Object state) {
		if (!arePreconditionsMet(conditionName)) {
			throw new IllegalStateException("Preconditions for " + conditionName + " are not all set");
		}

		conditionsMap.put(conditionName, state);

		return isFulfilled();
	}
}