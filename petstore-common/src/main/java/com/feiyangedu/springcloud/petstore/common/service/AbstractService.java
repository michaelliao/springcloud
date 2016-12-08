package com.feiyangedu.springcloud.petstore.common.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.feiyangedu.springcloud.petstore.common.exception.APIEntityNotFoundException;

public abstract class AbstractService {

	protected final Log log = LogFactory.getLog(getClass());

	protected <T> T checkNonNull(T entity) {
		if (entity == null) {
			throw new APIEntityNotFoundException("Entity is null");
		}
		return entity;
	}
}
