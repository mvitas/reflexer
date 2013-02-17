package com.reflexer.service;

import android.os.Binder;

import com.reflexer.model.RXReflex;

import java.util.ArrayList;

public class RXBinder extends Binder {

	private final RXService rxService;

	public RXBinder(RXService rxService) {
		this.rxService = rxService;
	}

	public void addReflex(RXReflex reflex) {
		rxService.addReflex(reflex);
	}

	public void removeReflex(RXReflex reflex) {
		rxService.removeReflex(reflex);
	}

	public void activateReflex(RXReflex reflex) {
		rxService.activateReflex(reflex);
	}

	public void deactivateReflex(RXReflex reflex) {
		rxService.deactivateReflex(reflex);
	}

	public ArrayList<RXReflex> getReflexes() {
		return rxService.getReflexes();
	}

}
