package com.manish.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class UserLogin {
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Integer id;
	
	@Column(name = "user_name",length = 40,unique = true)
	private String userName;
	
	@Column(name = "pass",length = 70)
	private String password;
	
	@Column(name = "email",length = 40)
	private String email;
	
	@Column(name = "mobile",length = 10)
	private long mobile;
	
	@Column(name = "gender",length = 6)
	private String gender;
	
	@Column(name = "admin")
	private Boolean admin;
	
	@Column
	private boolean enabled;
	
}
