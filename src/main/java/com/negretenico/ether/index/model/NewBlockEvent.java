package com.negretenico.ether.index.model;

import org.springframework.context.ApplicationEvent;

public class NewBlockEvent extends ApplicationEvent {
	private final String blockHex;
	private NewBlockEvent(Object source, String blockHex) {
		super(source);
		this.blockHex=blockHex;
	}
	public static NewBlockEvent of(Object obj, String blockHex){
		return new NewBlockEvent(obj,blockHex);
	}
	public String getBlockHex(){
		return  blockHex;
	}
}
