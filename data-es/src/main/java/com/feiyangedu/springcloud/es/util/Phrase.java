package com.feiyangedu.springcloud.es.util;

public class Phrase extends Span {

	public Phrase(String text) {
		super(text);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Phrase) {
			Phrase p = (Phrase) o;
			return this.text.equals(p.text);
		}
		return false;
	}
}
