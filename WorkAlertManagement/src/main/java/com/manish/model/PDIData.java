package com.manish.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class PDIData {
	@Id
	@GeneratedValue
	private Integer pdiDataId;

	@Column
	private String parameters;

	@Column
	private String sizes;

	@ManyToOne(targetEntity = PDI.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "pdiId", referencedColumnName = "pdiId")
	private PDI pdi;

}
