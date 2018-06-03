package com.feiyangedu.springcloud.petstore.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * IpUtil to get IP address.
 * 
 * @author michael
 */
public class IpUtil {

	static final Log log = LogFactory.getLog(IpUtil.class);

	static final String IP = getIpAddress();

	public static String getIpAddress() {
		String ip = _getIpAddress();
		log.info("Get IP address: " + ip);
		return ip;
	}

	static String _getIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			log.warn("Get IP address by InetAddress.getLocalHost() failed.");
		}
		try {
			Enumeration<NetworkInterface> iterInterface = NetworkInterface.getNetworkInterfaces();
			while (iterInterface.hasMoreElements()) {
				NetworkInterface network = iterInterface.nextElement();
				if (network.isLoopback() || !network.isUp()) {
					continue;
				}
				Enumeration<InetAddress> itAddr = network.getInetAddresses();
				while (itAddr.hasMoreElements()) {
					return itAddr.nextElement().getHostAddress();
				}
			}
		} catch (Exception e) {
			log.warn("Get IP address by NetworkInterface.getNetworkInterfaces() failed.", e);
		}
		throw new RuntimeException("Failed to get IP address.");
	}

	public static int[] ipAddrToIntArray(String ipAddr) {
		String[] arr = ipAddr.split("\\.");
		if (arr.length != 4) {
			throw new IllegalArgumentException("Bad IP address: " + ipAddr);
		}
		int[] results = new int[4];
		for (int i = 0; i < 4; i++) {
			results[i] = Integer.parseInt(arr[i]);
		}
		return results;
	}

	public static int ipAddrToInt(String ipAddr) {
		int[] results = ipAddrToIntArray(ipAddr);
		return (results[0] << 24) | (results[1] << 16) | (results[2] << 8) | results[3];
	}

}
