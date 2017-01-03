package com.feiyangedu.springcloud.messaging;

public class Vote {

	public static enum Name {
		Bob, Tim, Alice, Lisa
	}

	private Name name;

	public Vote() {
	}

	public Vote(String name) {
		this.name = Name.valueOf(name);
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

}
