package com.manish.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class Document {

	@Id
	@GeneratedValue
	private Integer id;

	@Column
	private String documentName;

	@Lob
	private Lob document;
}
