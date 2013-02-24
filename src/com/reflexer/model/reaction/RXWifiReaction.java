package com.reflexer.model.reaction;

import com.reflexer.exception.RXNotImplementedException;
import com.reflexer.model.RXReaction;

//TODO: ovo cemo trebat pobrisat
// RXReaction terba isto biti 
public class RXWifiReaction extends RXReaction {

	@Override
	public void execute() {
		throw new RXNotImplementedException("Wifi reaction \"execute\"  not implemented");
	}

}
