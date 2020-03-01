package com.ssessions.teambuilder.fileio;

/**
 * Super class for read and write file classes, sets delimiter and has a method for checking if a string is numeric
 * 
 * @author Shotaro Sessions
 *
 */
public class ReadWriteFile {
	
	protected final static String DELIMITER = ",";

	protected boolean isNumeric(String str) {
	    try {
	        Double.parseDouble(str);
	    } catch (NumberFormatException | NullPointerException e) {
	        return false;
	    }
	    return true;
	}
}
