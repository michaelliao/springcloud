package com.feiyangedu.springcloud.petstore.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class AbstractEntity implements Serializable {

	protected static final int ID_LENGTH = 36;

	protected static final int VARCHAR_100 = 100;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(length = ID_LENGTH, nullable = false, updatable = false)
	private String id;

	@Column(nullable = false, updatable = false)
	private long createdAt;

	@Column(nullable = false)
	private long updatedAt;

	@Column(length = ID_LENGTH, nullable = false, updatable = false)
	private String createdBy;

	@Column(length = ID_LENGTH, nullable = false)
	private String updatedBy;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@PrePersist
	public void prePersist() {
		long now = System.currentTimeMillis();
		setCreatedAt(now);
		setUpdatedAt(now);
	}

	@PreUpdate
	public void preUpdate() {
		long now = System.currentTimeMillis();
		setUpdatedAt(now);
	}
}
