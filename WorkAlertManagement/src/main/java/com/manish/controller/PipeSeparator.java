package com.manish.controller;

import org.apache.commons.lang3.StringUtils;

public class PipeSeparator {
	public static void main(String[] args) {
		String status = "#BBB|Status|_Sat Sep 04 01:1T 2021"
						+ "#HHHH|Status|_Sat Sep 04 15:4 IST 2021"
						+ "#YYY|Status|_Sat Sep 04 15: IST 2021"
						+ "#III|Status|_Sat Sep 04 15 IST 2021"
						+ "#BBBBB|Status|_Sat Sep 04 01: IST 2021"
						+ "#B|Status|_Sat Sep 04 0 IST 2021"
						+ "#BB|Status|_Sat Sep 04 2021";
		
		String statusRequest = "B";

		if (status.contains("#")) {
			String concat = StringUtils.EMPTY;
			for (String retval : status.split("#")) {
				if (StringUtils.isNotBlank(retval)) {
					retval = "#" + retval;
					if (retval.startsWith("#" + statusRequest+"|Status|")) {
						System.out.println(retval);
//						retval = retval.replace(retval, "");
						System.err.println(retval+" is deleted");
						retval = StringUtils.EMPTY;
					}
					concat = concat + retval;
				}
			}
			System.out.println(concat);
		}

	}
}
