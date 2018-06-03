package com.itranswarp.springcloud.data.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds datasource context in thread-local.
 * 
 * @author liaoxuefeng
 */
public final class RoutingDataSourceContext implements AutoCloseable {

	/**
	 * Select master data source (default).
	 */
	public static final String MASTER = "masterDataSource";

	/**
	 * Select slave data source.
	 */
	public static final String SLAVE = "slaveDataSource";

	static final Logger logger = LoggerFactory.getLogger(RoutingDataSourceContext.class);

	// holds data source key in thread local:
	static final ThreadLocal<String> threadLocalDataSourceKey = new ThreadLocal<>();

	/**
	 * Get current data source key.
	 * 
	 * @return String data source key.
	 */
	public static String getDataSourceRoutingKey() {
		String key = threadLocalDataSourceKey.get();
		return key == null ? MASTER : key;
	}

	public RoutingDataSourceContext(String key) {
		logger.info("set data source key: {}", key);
		threadLocalDataSourceKey.set(key);
	}

	@Override
	public void close() {
		threadLocalDataSourceKey.remove();
	}

}
