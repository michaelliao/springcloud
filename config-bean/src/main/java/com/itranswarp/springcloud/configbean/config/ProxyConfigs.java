package com.itranswarp.springcloud.configbean.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("fetch")
public class ProxyConfigs {

	private Map<String, ProxyConfig> proxies;

	public Map<String, ProxyConfig> getProxies() {
		return proxies;
	}

	public void setProxies(Map<String, ProxyConfig> proxies) {
		this.proxies = proxies;
	}

}
