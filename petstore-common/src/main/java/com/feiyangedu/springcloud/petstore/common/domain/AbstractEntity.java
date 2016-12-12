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

/**
 * Base entity for inheritance.
 * 
 * @author Michael Liao
 */
@MappedSuperclass
public class AbstractEntity implements Serializable {

	/**
	 * Length of ID field.
	 */
	protected static final int ID_LENGTH = 20;

	/**
	 * Length of VARCHAR(100).
	 */
	protected static final int VARCHAR_100 = 100;

	/**
	 * Primary key. Type: VARCHAR(20).
	 */
	@Id
	@GeneratedValue(generator = "stringIdGenerator")
	@GenericGenerator(name = "stringIdGenerator", strategy = "com.feiyangedu.springcloud.petstore.common.domain.StringIdGenerator")
	@Column(length = ID_LENGTH, nullable = false, updatable = false)
	public String id;

	/**
	 * Timestamp as milliseconds when entity created.
	 */
	@Column(nullable = false, updatable = false)
	public long createdAt;

	/**
	 * Timestamp as milliseconds when entity updated.
	 */
	@Column(nullable = false)
	public long updatedAt;

	/**
	 * Id of user who created this entity.
	 */
	@Column(length = ID_LENGTH, nullable = false, updatable = false)
	public String createdBy;

	/**
	 * Id of user who updated this entity.
	 */
	@Column(length = ID_LENGTH, nullable = false)
	public String updatedBy;

	/**
	 * Version field for optimism lock.
	 */
	@Version
	@Column(nullable = false)
	public long version;

	/**
	 * Hook when pre-persist.
	 */
	@PrePersist
	public void prePersist() {
		this.createdAt = this.updatedAt = System.currentTimeMillis();
		this.createdBy = this.updatedBy = UserContext.getRequiredCurrentUserInfo().id;
	}

	/**
	 * Hook when pre-update.
	 */
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = System.currentTimeMillis();
		this.updatedBy = UserContext.getRequiredCurrentUserInfo().id;
	}
}
