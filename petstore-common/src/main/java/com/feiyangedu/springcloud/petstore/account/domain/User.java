package com.feiyangedu.springcloud.petstore.account.domain;

import java.time.LocalDate;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.feiyangedu.springcloud.petstore.common.constant.Gender;
import com.feiyangedu.springcloud.petstore.common.constant.Role;
import com.feiyangedu.springcloud.petstore.common.domain.AbstractEntity;

@Entity
public class User extends AbstractEntity {

	public static final User SYSTEM;

	static {
		User sys = new User();
		sys.id = "00000000000000000000";
		sys.role = Role.ADMIN;
		sys.email = "system@sample.io";
		sys.name = "SYSTEM";
		sys.gender = Gender.SECRET;
		sys.birth = LocalDate.of(2000, 1, 1);
		sys.createdAt = sys.updatedAt = sys.birth.atStartOfDay().atZone(ZoneId.of("Z")).toEpochSecond() * 1000L;
		SYSTEM = sys;
	}

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
