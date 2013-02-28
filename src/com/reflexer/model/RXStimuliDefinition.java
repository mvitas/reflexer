package com.reflexer.model;

import com.reflexer.handler.RXHandler;

import java.util.ArrayList;

/**
 * Contains meta data that defines the structure of a concrete RXStimuli.
 * 
 * 
 * @author ivan
 * 
 */
public class RXStimuliDefinition {

	/**
	 * List of definitions of all the conditions for the concrete stimuli.
	 */
	private ArrayList<RXConditionDefinition> conditionDefinitons;

	/**
	 * RXHandler instance that is associated with the concrete stimuli.
	 * 
	 */
	private RXHandler handler;

	/**
	 * Name of this stimuli.
	 */
	private String name;

	public RXStimuliDefinition() {

	}

	public void setHandler(RXHandler handler) {
		this.handler = handler;
	}

	public RXHandler getHandler() {
		return handler;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setConditionDefinitions(ArrayList<RXConditionDefinition> conditionDefinitons) {
		this.conditionDefinitons = conditionDefinitons;
	}

	public ArrayList<RXConditionDefinition> getConditionDefinitons() {
		return conditionDefinitons;
	}

	public RXConditionDefinition getConditionDefinitionByName(String name) {
		for (RXConditionDefinition definition : conditionDefinitons) {
			if (definition.getName().equals(name)) {
				return definition;
			}
		}

		return null;
	}

}
