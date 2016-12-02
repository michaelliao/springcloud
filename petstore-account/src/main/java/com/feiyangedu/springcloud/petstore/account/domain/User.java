package com.feiyangedu.springcloud.petstore.account.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.feiyangedu.springcloud.petstore.common.constant.Gender;
import com.feiyangedu.springcloud.petstore.common.domain.AbstractEntity;

@Entity
public class User extends AbstractEntity {

	@Column(length = VARCHAR_100, nullable = false, updatable = false, unique = true)
	private String email;

	@Column(length = VARCHAR_100, nullable = false)
	private String name;

	@Column(length = VARCHAR_100, nullable = false)
	private Gender gender;

	@Column(columnDefinition = "DATE", nullable = false)
	private LocalDate birth;

}
