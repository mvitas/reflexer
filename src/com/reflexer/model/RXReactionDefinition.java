
package com.reflexer.model;

import java.util.ArrayList;

public class RXReactionDefinition {

    /**
     * List of definitions of all the properties for the concrete reaction.
     */
    private ArrayList<RXPropertyDefinition> propertyDefinitions;

    /**
     * Name of this reaction.
     */
    private String name;

    private Class<? extends RXReaction> reactionClass;

    public RXReactionDefinition() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setReactionClass(Class<? extends RXReaction> reactionClass) {
        this.reactionClass = reactionClass;
    }

    public Class<? extends RXReaction> getReactionClass() {
        return reactionClass;
    }

    public void setPropertyDefinitions(ArrayList<RXPropertyDefinition> propertyDefinitions) {
        this.propertyDefinitions = propertyDefinitions;
    }

    public ArrayList<RXPropertyDefinition> getPropertyDefinitions() {
        return propertyDefinitions;
    }

    public RXPropertyDefinition getPropertyDefinitionByName(String name) {
        for (RXPropertyDefinition definition : propertyDefinitions) {
            if (definition.getName().equals(name)) {
                return definition;
            }
        }

        return null;
    }
}
