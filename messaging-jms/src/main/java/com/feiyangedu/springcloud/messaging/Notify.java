package com.feiyangedu.springcloud.messaging;

/**
 * Object message.
 */
public class Notify {

	public String from;
	public String to;
	public String message;

	public Notify() {
	}

	public Notify(String from, String to, String message) {
		super();
		this.from = from;
		this.to = to;
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("From: %s\nTo: %s\nMessage: %s\n", from, to, message);
	}
}
