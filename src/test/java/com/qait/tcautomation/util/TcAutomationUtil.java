package com.qait.tcautomation.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TcAutomationUtil {

	/*
	 * Returns string repersentation of e.printStackTrace()  
	 */
	public static String getExceptionDescriptionString(Exception e) {
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return stringWriter.toString();
		
	}
	
	public static int getIntFormString(String string) {
		return new Integer(string);
	}
}
