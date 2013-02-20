package com.reflexer.model.parser;

import android.util.Xml;

import com.reflexer.handler.RXHandler;
import com.reflexer.model.RXStimuli;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class RXStimuliParser {

	/**
	 * XML namespaces are not used.
	 */
	private static final String ns = null;

	private static final String STIMULI_TAG = "stimuli";

	private static final String HANDLER_TAG = "handler";

	private static final String INTERESTED_IN_TAG = "interested-in";

	private static final String NAME_ATTRIBUTE = "name";

	private static final String CLASS_ATTRIBUTE = "class";

	private static final String ALIAS_ATTRIBUTE = "alias";

	public RXStimuli parse(InputStream in) throws XmlPullParserException, IOException {
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(in, null);
		parser.nextTag();
		return readStimuli(parser);
	}

	private RXStimuli readStimuli(XmlPullParser parser) throws XmlPullParserException, IOException {
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

		return handler;
	}

	private HashMap<String, String> readInterestedin(XmlPullParser parser) throws XmlPullParserException, IOException {
		HashMap<String, String> interestingActions = new HashMap<String, String>();

		while (parser.next() == XmlPullParser.START_TAG) {

		}

		return interestingActions;
	}
}
