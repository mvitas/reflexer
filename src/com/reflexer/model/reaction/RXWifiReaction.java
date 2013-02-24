package com.reflexer.model.reaction;

import java.util.ArrayList;

import com.reflexer.exception.RXNotImplementedException;
import com.reflexer.model.RXPropertyDefinition;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReactionProperty;

//TODO: ovo cemo trebat pobrisat
// RXReaction terba isto biti 
public class RXWifiReaction extends RXReaction {
	
	private ArrayList<RXPropertyDefinition> propertyDefinitions;

	public RXWifiReaction(String name) {
		super(name);
	}
	
	public RXWifiReaction(int id, String name) {
		super(id, name);
	}
	
	public RXWifiReaction(int id, String name,
			ArrayList<RXReactionProperty> paramsList) {
		super(id, name, paramsList);
	}

	@Override
	public ArrayList<RXPropertyDefinition> getRXPropertyDefinitionList() {

		//da ovdje odmah zove parser i povadi sve van ili da se negdje radi neka inicijalizaija svega 
		
		throw new RXNotImplementedException("not implemented");
	}

	@Override
	public void setRXPropertyDefinitionList(
			ArrayList<RXPropertyDefinition> readProperties) {
		propertyDefinitions = readProperties;

	}

	@Override
	public void execute() {
		throw new RXNotImplementedException("Wifi reaction \"execute\"  not implemented");
	}

}
