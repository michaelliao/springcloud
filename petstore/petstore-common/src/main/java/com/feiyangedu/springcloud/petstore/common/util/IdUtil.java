package com.feiyangedu.springcloud.petstore.common.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Generate a 20-char String id that composed by: "timestamp-hostId-incr".
 * 
 * @author michael
 */
public class IdUtil {

	static final char[] BASE32_CHARS = "0123456789abcdefghijklmnopqrstuv".toCharArray();

	static final Log log = LogFactory.getLog(IdUtil.class);

	static final AtomicLong sequence = new AtomicLong(0);

	static final String IP = getIpAsString();

	/**
	 * Generate a 20-char String id that composed by:
	 * 
	 * timestamp: 9-chars; seq: 5-chars; ip: 6-chars
	 * 
	 * @return 32-char String.
	 */
	public static String next() {
		String timestamp = longToBase32(System.currentTimeMillis());
		String seq = longToBase32(sequence.getAndIncrement() & 0x1ffffff);
		StringBuilder builder = new StringBuilder("00000000000000000000");
		setStringAt(builder, 0, timestamp, 9);
		setStringAt(builder, 9, IP, 6);
		setStringAt(builder, 15, seq, 5);
		return builder.toString();
	}

	static String getIpAsString() {
		int ip = IpUtil.ipAddrToInt(IpUtil.getIpAddress());
		log.info("Get IP address: " + Integer.toHexString(ip));
		return intToBase32(ip & 0x3fffffff);
	}

	static void setStringAt(StringBuilder buffer, int bufferStart, String str, int length) {
		int strStart = 0;
		int strEnd = str.length();
		if (str.length() < length) {
			// str="ff", length=5:
			bufferStart = bufferStart + (length - str.length());
			strEnd = str.length();
		} else {
			// str="f1c2d3", length=3:
			strStart = str.length() - length;
		}
		for (int i = bufferStart, n = strStart; n < strEnd; i++, n++) {
			buffer.setCharAt(i, str.charAt(n));
		}
	}

	static String longToBase32(long n) {
		char[] buffer = new char[13];
		for (int i = 12; i >= 0; i--) {
			int x = (int) (n & 0x1f);
			buffer[i] = BASE32_CHARS[x];
			n = n >> 5;
			if (n == 0) {
				return new String(buffer, i, 13 - i);
			}
		}
		return new String(buffer);
	}

	static String intToBase32(int n) {
		char[] buffer = new char[7];
		for (int i = 6; i >= 0; i--) {
			int x = n & 0x1f;
			buffer[i] = BASE32_CHARS[x];
			n = n >> 5;
			if (n == 0) {
				return new String(buffer, i, 7 - i);
			}
		}
		return new String(buffer);
	}

	// long id ////////////////////////////////////////////////////////////////

	static AtomicLong currentTimestamp = new AtomicLong(0);
	static AtomicReference<IdSequence> idSequenceHolder = new AtomicReference<>(new IdSequence(epochSecond()));

	public static long nextLong() {
		for (;;) {
			long n = _nextLong();
			if (n > 0) {
				return n;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
	}

	static long _nextLong() {
		long ts = epochSecond();
		IdSequence idSequence = idSequenceHolder.get();
		if (idSequence.epochSecond >= ts) {
			return idSequence.next();
		}
		// set new IdSequence:
		IdSequence newIdSequence = new IdSequence(ts);
		if (idSequenceHolder.compareAndSet(idSequence, newIdSequence)) {
			// set ok:
			// System.out.println(Thread.currentThread().getName() + " " + (ts &
			// 0xffff) + " Set >>> ok: "
			// + idSequence.toString().substring(45) + " -> " +
			// newIdSequence.toString().substring(45));
			return newIdSequence.next();
		} else {
			// not set ok:
			IdSequence existIdSequence = idSequenceHolder.get();
			// System.out.println(Thread.currentThread().getName() + " " + (ts &
			// 0xffff) + " Set NOT ok: "
			// + idSequence.toString().substring(45) + " -> " +
			// newIdSequence.toString().substring(45)
			// + ", but use " + existIdSequence.toString().substring(45));
			return existIdSequence.next();
		}
	}

	static long epochSecond() {
		return Instant.now().getEpochSecond();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("2016 -> " + longToBase32(new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01").getTime()));
		System.out.println("2099 -> " + longToBase32(new SimpleDateFormat("yyyy-MM-dd").parse("2099-01-01").getTime()));
		for (int i = 0; i < 100; i++) {
			System.out.println(next());
		}
		Thread.sleep(1);
		for (int i = 0; i < 100; i++) {
			System.out.println(next());
		}
	}
}

class IdSequence {

	static final long MAX_SEQUENCE = 0xffffff;

	final long epochSecond;
	final long prefix;
	final AtomicLong sequence;

	IdSequence(long epochSecond) {
		this.epochSecond = epochSecond;
		this.prefix = this.epochSecond << 24;
		this.sequence = new AtomicLong(0);
	}

	long next() {
		long seq = this.sequence.getAndIncrement();
		if (seq > MAX_SEQUENCE) {
			// sequence exhausted:
			return 0;
		}
		return prefix | seq;
	}

}
