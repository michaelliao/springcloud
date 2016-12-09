package com.feiyangedu.springcloud.petstore.account.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.feiyangedu.springcloud.petstore.common.domain.AbstractEntity;

@Entity
public class UserAuth extends AbstractEntity {

	@Column(length = ID_LENGTH, nullable = false, updatable = false, unique = true)
	private String userId;

	@Column(length = VARCHAR_100, nullable = false)
	private String passwd;

	@Column(length = VARCHAR_100, nullable = false)
	private String salt;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
