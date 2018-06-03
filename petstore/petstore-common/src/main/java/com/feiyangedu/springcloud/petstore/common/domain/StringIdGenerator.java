package com.feiyangedu.springcloud.petstore.common.domain;

import java.io.Serializable;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.feiyangedu.springcloud.petstore.common.util.IdUtil;

public class StringIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor s, Object obj) {
		return IdUtil.next();
	}

}
