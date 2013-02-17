package com.reflexer.model;

public class RXReflex {

	private RXStimuli rxThis;
	private RXReaction rxThat;

	/**
	 * Name of the group in which the reflex is stored.
	 */
	private String groupName;

	/**
	 * Is the reflex currently active. If so, it is responding to stimuli.
	 */
	private boolean active;

	public RXReflex(RXStimuli rxThis, RXReaction rxThat) {
		super();
		this.setRxThis(rxThis);
		this.setRxThat(rxThat);
	}

	public String getGroupName() {
		return groupName;
	}

	public RXReaction getRxThat() {
		return rxThat;
	}

	public RXStimuli getRxThis() {
		return rxThis;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setRxThat(RXReaction rxThat) {
		this.rxThat = rxThat;
		this.rxThis.setReaction(rxThat);
	}

	public void setRxThis(RXStimuli rxThis) {
		this.rxThis = rxThis;
	}

}
