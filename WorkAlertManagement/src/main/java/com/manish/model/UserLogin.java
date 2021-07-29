package com.manish.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
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
	@Column(name = "user_name",length = 15,unique = true)
	private String userName;
	@Column(name = "pass",length = 15)
	private String password;
	
}
