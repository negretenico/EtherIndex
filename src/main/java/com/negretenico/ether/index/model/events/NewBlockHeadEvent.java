package com.negretenico.ether.index.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class NewBlockHeadEvent extends ApplicationEvent {
	private final String blockHex;
	private NewBlockHeadEvent(Object source, String blockHex) {
		super(source);
		this.blockHex=blockHex;
	}
	public static NewBlockHeadEvent of(Object obj, String blockHex){
		return new NewBlockHeadEvent(obj,blockHex);
	}
}
