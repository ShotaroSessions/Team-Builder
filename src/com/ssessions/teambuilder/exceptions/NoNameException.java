package com.ssessions.teambuilder.exceptions;

/**
 * Exception for when no name is provided in text file
 * 
 * @author Shotaro Sessions
 *
 */
public class NoNameException extends Exception {
	
	public NoNameException(String message) {
		super(message);
	}
}
