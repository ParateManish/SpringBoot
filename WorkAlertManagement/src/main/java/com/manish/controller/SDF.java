package com.manish.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SDF {
	public static void main(String[] args) throws ParseException {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);
		System.out.println("String date :: "+strDate);
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(strDate);  
		System.out.println("Date date :: "+date1);
	}
}
