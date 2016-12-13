package com.feiyangedu.springcloud.petstore.account.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.feiyangedu.springcloud.petstore.common.constant.Gender;
import com.feiyangedu.springcloud.petstore.common.constant.Role;
import com.feiyangedu.springcloud.petstore.common.domain.AbstractEntity;

@Entity
public class User extends AbstractEntity {

	@Column(nullable = false, updatable = false)
	public int role = Role.USER;

	@Column(length = VARCHAR_100, nullable = false, updatable = false, unique = true)
	public String email;

	@Column(length = VARCHAR_100, nullable = false)
	public String name;

	@Column(length = VARCHAR_100, nullable = false)
	public Gender gender;

	@Column(columnDefinition = "DATE", nullable = false)
	public LocalDate birth;

}
