package com.manish.controller;

import java.util.Date;

public class PipeSeparator {
	public static void main(String[] args) {
		String status = "status1!status2!status3!";

		if(status.contains("!")) {
			for (String retval: status.split("!")) {
				System.out.println(retval);
//				retval =  retval+"_"+new Date();
//				String[] split = retval.split("_");
//				int length = split.length;
//				System.out.println(length);
//				for(int i = 0 ; i<length;i++) {
//					System.out.println("split :: "+split[i]);
//				}
		      }
		}
		
	}
}
