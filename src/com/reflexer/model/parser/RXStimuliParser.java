package com.reflexer.model.parser;

import android.util.Xml;

import com.reflexer.handler.RXHandler;
import com.reflexer.model.RXCondition;
import com.reflexer.model.RXStimuli;
import com.reflexer.util.RXTypes;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class RXStimuliParser {

	/**
	 * XML namespaces are not used.
	 */
	private static final String ns = null;

	private static final String STIMULI_TAG = "stimuli";

	private static final String HANDLER_TAG = "handler";

	private static final String INTERESTED_IN_TAG = "interested-in";

	private static final String CONDITIONS_TAG = "conditions";

	private static final String CONDITION_TAG = "condition";

	private static final String DEPENDS_ON_TAG = "depends-on";

	private static final String NAME_ATTRIBUTE = "name";

	private static final String CLASS_ATTRIBUTE = "class";

	private static final String ALIAS_ATTRIBUTE = "alias";

	private static final String TYPE_ATTRIBUTE = "type";

	private static final String REQUIRED_ATTRIBUTE = "required";

	public RXStimuli parse(InputStream in) throws XmlPullParserException, IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(in, null);
		parser.nextTag();
		return readStimuli(parser);
	}

	private RXStimuli readStimuli(XmlPullParser parser) throws XmlPullParserException, IOException,
			InstantiationException, IllegalAccessException, ClassNotFoundException {
		RXStimuli stimuli = new RXStimuli();

		parser.require(XmlPullParser.START_TAG, ns, STIMULI_TAG);

		int attributeCount = parser.getAttributeCount();

		String name = null;

		for (int i = 0; i < attributeCount; i++) {
			if (parser.getAttributeName(i).equals(NAME_ATTRIBUTE)) {
				name = parser.getAttributeValue(i);
			}
		}

		stimuli.setName(name);
		stimuli.setRXHandler(readHandler(parser));
		stimuli.setConditionList(readConditions(parser));

		return stimuli;
	}

	private RXHandler readHandler(XmlPullParser parser) throws XmlPullParserException, IOException,
			InstantiationException, IllegalAccessException, ClassNotFoundException {
		RXHandler handler;

		parser.require(XmlPullParser.START_TAG, ns, HANDLER_TAG);

		int attributeCount = parser.getAttributeCount();

		String className = null;

		for (int i = 0; i < attributeCount; i++) {
			if (parser.getAttributeName(i).equals(CLASS_ATTRIBUTE)) {
				className = parser.getAttributeValue(i);
			}
		}

		@SuppressWarnings("unchecked")
		Class<? extends RXHandler> handlerClass = (Class<? extends RXHandler>) Class.forName(className);
		handler = handlerClass.newInstance();
		handler.setInterestingActions(readInterestedIn(parser));

		return handler;
	}

	private HashMap<String, String> readInterestedIn(XmlPullParser parser) throws XmlPullParserException, IOException {
		HashMap<String, String> interestingActions = new HashMap<String, String>();

		while (parser.next() == XmlPullParser.START_TAG) {
			parser.require(XmlPullParser.START_TAG, ns, INTERESTED_IN_TAG);

			String alias = null;

			int attributeCount = parser.getAttributeCount();

			for (int i = 0; i < attributeCount; i++) {
				if (parser.getAttributeName(i).equals(ALIAS_ATTRIBUTE)) {
					alias = parser.getAttributeValue(i);
				}
			}

			parser.next();
			parser.require(XmlPullParser.TEXT, ns, null);

			String action = parser.getText();
			interestingActions.put(alias, action);

			parser.next();
			parser.require(XmlPullParser.END_TAG, ns, INTERESTED_IN_TAG);
		}

		return interestingActions;
	}

	private ArrayList<RXCondition> readConditions(XmlPullParser parser) throws XmlPullParserException, IOException {
		ArrayList<RXCondition> conditionList = new ArrayList<RXCondition>();
		HashMap<String, RXCondition> dependencyMap = new HashMap<String, RXCondition>();

		parser.next();
		parser.require(XmlPullParser.START_TAG, ns, CONDITIONS_TAG);

		while (parser.next() == XmlPullParser.START_TAG) {
			ConditionDependencyMap depMap = readCondition(parser);
			conditionList.add(depMap.condition);

			for (int i = 0; i < depMap.dependsOn.size(); i++) {
				dependencyMap.put(depMap.dependsOn.get(i), depMap.condition);
			}
		}

		parser.next();
		parser.require(XmlPullParser.END_TAG, ns, CONDITIONS_TAG);

		assignDependencies(conditionList, dependencyMap);

		return conditionList;
	}

	private void assignDependencies(ArrayList<RXCondition> conditionList, HashMap<String, RXCondition> dependencyMap) {
		for (String conditionName : dependencyMap.keySet()) {
			RXCondition condition = RXCondition.getConditionDefinitionByName(conditionName, conditionList);
			dependencyMap.get(conditionName).addDependency(condition);
		}
	}

	private ConditionDependencyMap readCondition(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, CONDITION_TAG);

		ConditionDependencyMap depMap = new ConditionDependencyMap();

		String name = null;
		String typeString = null;
		String requiredString = null;

		int attributeCount = parser.getAttributeCount();

		for (int i = 0; i < attributeCount; i++) {
			if (parser.getAttributeName(i).equals(NAME_ATTRIBUTE)) {
				name = parser.getAttributeValue(i);
			} else if (parser.getAttributeName(i).equals(TYPE_ATTRIBUTE)) {
				typeString = parser.getAttributeValue(i);
			} else if (parser.getAttributeName(i).equals(REQUIRED_ATTRIBUTE)) {
				requiredString = parser.getAttributeValue(i);
			}
		}

		int type = RXTypes.fromString(typeString);
		boolean required = Boolean.parseBoolean(requiredString);

		depMap.condition = new RXCondition(required, name, type);
		depMap.dependsOn = readDependencies(parser);

		parser.next();
		parser.require(XmlPullParser.END_TAG, ns, CONDITION_TAG);

		return depMap;
	}

	private ArrayList<String> readDependencies(XmlPullParser parser) throws XmlPullParserException, IOException {
		ArrayList<String> dependencies = new ArrayList<String>();

		while (parser.next() == XmlPullParser.START_TAG) {
			parser.require(XmlPullParser.START_TAG, ns, DEPENDS_ON_TAG);

			parser.next();
			parser.require(XmlPullParser.TEXT, ns, null);

			dependencies.add(parser.getText());

			parser.next();
			parser.require(XmlPullParser.END_TAG, ns, INTERESTED_IN_TAG);
		}

		return dependencies;
	}

	private static class ConditionDependencyMap {
		public RXCondition condition;
		public ArrayList<String> dependsOn;
	}
}
