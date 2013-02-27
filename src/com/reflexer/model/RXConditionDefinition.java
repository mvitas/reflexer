package com.reflexer.model;

import java.util.ArrayList;

public class RXConditionDefinition {

	/**
	 * Does this condition have to be set.
	 */
	private boolean required;

	/**
	 * Is the action triggered only when the condition state is changed or every
	 * time the condition set.
	 * <p>
	 * If set to true action will be triggered only when the condition state has
	 * changed.
	 */
	private boolean triggerOnChange;

	/**
	 * Name of the condition.
	 */
	private String name;

	/**
	 * Type of the condition. Possible types defined in RXTypes.
	 */
	private int type;

	/**
	 * List of conditions that must be set in order for this condition to be
	 * available.
	 */
	private ArrayList<RXConditionDefinition> dependsOn;

	public RXConditionDefinition(boolean required, String name, int type) {
		this(required, name, type, true);
	}

	public RXConditionDefinition(boolean required, String name, int type, boolean triggerOnChange) {
		super();
		this.required = required;
		this.name = name;
		this.type = type;
		this.dependsOn = new ArrayList<RXConditionDefinition>();
		this.triggerOnChange = triggerOnChange;
	}

	/**
	 * Returns RXCondition that defines a condition by given name. Utility
	 * method.
	 * <p>
	 * Throws IllegalArgumentException if there is no condition defined with the
	 * given name.
	 * 
	 * @param conditionName
	 *            name of the condition
	 * @return
	 */
	public static RXConditionDefinition getConditionDefinitionByName(String conditionName,
			ArrayList<RXConditionDefinition> conditionDefinitions) {
		for (int i = 0; i < conditionDefinitions.size(); i++) {
			if (conditionDefinitions.get(i).getName().equals(conditionName)) {
				return conditionDefinitions.get(i);
			}
		}

		throw new IllegalArgumentException("Condition with the name " + conditionName + " id not defined");
	}

	public void addDependency(RXConditionDefinition condition) {
		this.dependsOn.add(condition);
	}

	public ArrayList<RXConditionDefinition> getDependsOn() {
		return dependsOn;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isTriggeredOnChange() {
		return triggerOnChange;
	}

	public void setDependsOn(ArrayList<RXConditionDefinition> dependsOn) {
		this.dependsOn = dependsOn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setTriggerOnChange(boolean triggerOnChange) {
		this.triggerOnChange = triggerOnChange;
	}

}
