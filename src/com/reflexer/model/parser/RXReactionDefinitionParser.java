package com.reflexer.model.parser;

import android.util.Xml;

import com.reflexer.model.RXPropertyDefinition;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReactionDefinition;
import com.reflexer.util.RXTypes;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RXReactionDefinitionParser {

	/**
	 * XML namespaces are not used.
	 */
	private static final String ns = null;

	private static final String REACTION_TAG = "reaction";

	private static final String PROPERTIES_TAG = "properties";

	private static final String PROPERTY_TAG = "property";

	private static final String NAME_ATTRIBUTE = "name";

	private static final String CLASS_ATTRIBUTE = "class";

	private static final String TYPE_ATTRIBUTE = "type";

	private static final String REQUIRED_ATTRIBUTE = "required";

	public RXReactionDefinition parse(InputStream in) throws XmlPullParserException, IOException,
			ClassNotFoundException, InstantiationException, IllegalAccessException {
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(in, null);
		parser.nextTag();
		return readReaction(parser);
	}

	private RXReactionDefinition readReaction(XmlPullParser parser) throws XmlPullParserException, IOException,
			ClassNotFoundException, InstantiationException, IllegalAccessException {
		RXReactionDefinition reaction = new RXReactionDefinition();

		parser.require(XmlPullParser.START_TAG, ns, REACTION_TAG);

		int attributeCount = parser.getAttributeCount();

		String className = null;

		for (int i = 0; i < attributeCount; i++) {
			if (parser.getAttributeName(i).equals(CLASS_ATTRIBUTE)) {
				className = parser.getAttributeValue(i);
			}
		}

		@SuppressWarnings("unchecked")
		Class<? extends RXReaction> reactionClass = (Class<? extends RXReaction>) Class.forName(className);

		reaction.setReactionClass(reactionClass);
		reaction.setPropertyDefinitions(readProperties(parser));

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, REACTION_TAG);

		return reaction;
	}

	private ArrayList<RXPropertyDefinition> readProperties(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		ArrayList<RXPropertyDefinition> properties = new ArrayList<RXPropertyDefinition>();

		parser.nextTag();
		parser.require(XmlPullParser.START_TAG, ns, PROPERTIES_TAG);

		while (parser.nextTag() == XmlPullParser.START_TAG) {
			properties.add(readProperty(parser));
		}

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, PROPERTIES_TAG);
		return properties;
	}

	private RXPropertyDefinition readProperty(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, PROPERTY_TAG);

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

		parser.next();
		parser.require(XmlPullParser.END_TAG, ns, PROPERTY_TAG);

		return new RXPropertyDefinition(required, name, type);

	}
}
