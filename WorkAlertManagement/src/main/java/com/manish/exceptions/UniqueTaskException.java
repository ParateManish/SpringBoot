package com.manish.exceptions;

import org.apache.commons.lang3.StringUtils;

public class UniqueTaskException extends RuntimeException {

	private String msg = StringUtils.EMPTY;

	public UniqueTaskException(String msg) {
		super();
		this.msg = msg;
	}

}
