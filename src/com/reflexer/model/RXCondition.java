package com.reflexer.model;

public class RXCondition {

	private RXThis rxThis;
	private RXThat rxThat;
	
	public RXCondition(RXThis rxThis, RXThat rxThat) {
		super();
		this.setRxThis(rxThis);
		this.setRxThat(rxThat);
	}

	public RXThat getRxThat() {
		return rxThat;
	}

	public void setRxThat(RXThat rxThat) {
		this.rxThat = rxThat;
	}

	public RXThis getRxThis() {
		return rxThis;
	}

	public void setRxThis(RXThis rxThis) {
		this.rxThis = rxThis;
	}
	
	
}
