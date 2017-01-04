package com.feiyangedu.springcloud.es.util;

public class Word extends Span {

	public Word(String text) {
		super(text);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Word) {
			Word w = (Word) o;
			return this.text.equals(w.text);
		}
		return false;
	}
}
