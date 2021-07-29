package com.manish.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
	@Id
	@GeneratedValue
	private Integer id;
	@Column
	private String task1;
	@Column
	private String task2;
	@Column
	private String task3;
	@Column
	private String task4;
	@Temporal(TemporalType.DATE)
	private Date taskDate;
}
