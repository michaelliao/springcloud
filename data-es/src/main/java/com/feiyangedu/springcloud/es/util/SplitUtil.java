package com.feiyangedu.springcloud.es.util;

import java.util.ArrayList;
import java.util.List;

public class SplitUtil {

	static final int START = 0;
	static final int ENGLISH = 1;
	static final int UNICODE = 2;
	static final int SEPARATOR = 3;

	static final String UNICODE_SEPARATOR = "，。？！：；、·～…（）《》【】「」／“”　";
	static final Span[] EMPTY_SPANS = new Span[0];

	public static Span[] split(String text) {
		text = text.trim();
		if (text.isEmpty()) {
			return EMPTY_SPANS;
		}
		List<Span> list = new ArrayList<>();
		StringBuilder buffer = new StringBuilder(10);
		int lastType = START;
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			int type = getType(ch);
			switch (type) {
			case ENGLISH:
				if (lastType == UNICODE) {
					addAndClearBuffer(list, buffer, lastType);
				}
				buffer.append(ch);
				break;
			case UNICODE:
				if (lastType == ENGLISH) {
					addAndClearBuffer(list, buffer, lastType);
				}
				buffer.append(ch);
				break;
			case SEPARATOR:
				if (buffer.length() > 0) {
					addAndClearBuffer(list, buffer, lastType);
				}
				break;
			default:
				throw new RuntimeException("Should not fall to default.");
			}
			lastType = type;
		}
		// add last term:
		if (buffer.length() > 0) {
			addAndClearBuffer(list, buffer, lastType);
		}
		return list.toArray(new Span[list.size()]);
	}

	static void addAndClearBuffer(List<Span> list, StringBuilder buffer, int lastType) {
		String s = buffer.toString();
		if (lastType == UNICODE && s.length() > 7) {
			s = s.substring(0, 7);
		}
		list.add(lastType == ENGLISH || s.length() == 1 ? new Word(s) : new Phrase(s));
		buffer.delete(0, buffer.length());
	}

	static int getType(char ch) {
		if (isEnglish(ch)) {
			return ENGLISH;
		}
		if (isUnicode(ch)) {
			return UNICODE;
		}
		return SEPARATOR;
	}

	static boolean isEnglish(char ch) {
		if (ch >= 'a' && ch <= 'z') {
			return true;
		}
		if (ch >= 'A' && ch <= 'Z') {
			return true;
		}
		if (ch >= '0' && ch <= '9') {
			return true;
		}
		if (ch == '_' || ch == '-') {
			return true;
		}
		return false;
	}

	static boolean isUnicode(char ch) {
		if (UNICODE_SEPARATOR.indexOf(ch) >= 0) {
			return false;
		}
		return ch >= '\u0400';
	}
}
