package com.manish.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Transient;

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
//
//	private String task2;
//
//	private String task3;
//
//	private String task4;

	@Column
	private String status;

	@Column
	private String userName;

	@Column
	private String statusStage;

	@Column(unique = true)
	private String customUniqueKey;

	@Temporal(TemporalType.DATE)
	private Date taskDate;

	@Temporal(TemporalType.DATE)
	private Date taskModifiedDate;

	@Temporal(TemporalType.DATE)
	private Date taskStatusDate;
	
	@Temporal(TemporalType.DATE)
	private Date finishedTaskDate;
}
