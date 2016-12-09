package com.feiyangedu.springcloud.petstore.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.feiyangedu.springcloud.petstore.common.context.UserContext;

@MappedSuperclass
public class AbstractEntity implements Serializable {

	protected static final int ID_LENGTH = 20;

	protected static final int VARCHAR_100 = 100;

	@Id
	@GeneratedValue(generator = "stringIdGenerator")
	@GenericGenerator(name = "stringIdGenerator", strategy = "com.feiyangedu.springcloud.petstore.common.domain.StringIdGenerator")
	@Column(length = ID_LENGTH, nullable = false, updatable = false)
	public String id;

	@Column(nullable = false, updatable = false)
	public long createdAt;

	@Column(nullable = false)
	public long updatedAt;

	@Column(length = ID_LENGTH, nullable = false, updatable = false)
	public String createdBy;

	@Column(length = ID_LENGTH, nullable = false)
	public String updatedBy;

	@Version
	@Column(nullable = false)
	public long version;

	@PrePersist
	public void prePersist() {
		this.createdAt = this.updatedAt = System.currentTimeMillis();
		this.createdBy = this.updatedBy = UserContext.getRequiredCurrentUser().id;
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = System.currentTimeMillis();
		this.updatedBy = UserContext.getRequiredCurrentUser().id;
	}
}
