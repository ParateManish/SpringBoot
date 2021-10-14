package com.manish.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class PDI {

	@Id
	@GeneratedValue
	private Integer pdiId;

	@Column
	private String partName;

	@Column
	private String partNumber;

	@OneToMany(targetEntity = PDIData.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "pdiId", referencedColumnName = "pdiId")
	private Set<PDIData> pdiData;

	@Column
	private String parameters;

	@Column
	private String sizes;
}
