package com.itranswarp.springcloud.api.bean;

import io.swagger.annotations.ApiModelProperty;

public class UserReq {

	private String email;

	private String name;

	private boolean gender;

	@ApiModelProperty(value = "the email address", required = true, example = "me@example.com")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ApiModelProperty(value = "the name of the user", required = true, example = "Walter White")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ApiModelProperty(value = "the gender of the user, false=male, true=female", required = true)
	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

}
