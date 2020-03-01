package com.ssessions.teambuilder.exceptions;

/**
 * Exception for when a file is invalid for reading or writing
 * 
 * @author Shotaro Sessions
 *
 */
public class InvalidFileException extends Exception {

	public InvalidFileException(String message,Throwable e){
		super(message,e);
	}

	public InvalidFileException(String message) {
		super(message);
	}
}
