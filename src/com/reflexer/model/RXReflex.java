package com.reflexer.model;

public class RXReflex {

	private RXStimuli rxThis;
	private RXReaction rxThat;
	
	public RXReflex(RXStimuli rxThis, RXReaction rxThat) {
		super();
		this.setRxThis(rxThis);
		this.setRxThat(rxThat);
	}

	public RXReaction getRxThat() {
		return rxThat;
	}

	public void setRxThat(RXReaction rxThat) {
		this.rxThat = rxThat;
	}

	public RXStimuli getRxThis() {
		return rxThis;
	}

	public void setRxThis(RXStimuli rxThis) {
		this.rxThis = rxThis;
	}
	
	
}
