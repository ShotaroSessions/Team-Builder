package com.ssessions.teambuilder.exceptions;

/**
 * Exception for when a text file is in the incorrect format for reading
 * 
 * @author Shotaro Sessions
 *
 */
public class FormatException extends Exception {
	
	public FormatException(String message) {
		super(message);
	}
}
