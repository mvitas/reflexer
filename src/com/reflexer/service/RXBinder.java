package com.reflexer.service;

import android.app.Service;
import android.os.Binder;

public class RXBinder extends Binder {

	private final Service rxService;

	public RXBinder(Service rxService){
		this.rxService = rxService;
	}
	
	

}
