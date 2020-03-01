package com.ssessions.teambuilder.fileio;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.ssessions.teambuilder.exceptions.FormatException;
import com.ssessions.teambuilder.exceptions.InvalidFileException;
import com.ssessions.teambuilder.exceptions.NoNameException;
import com.ssessions.teambuilder.systems.Teammate;

/**
 * Class to retrieve a student roster from a text file
 * 
 * @author Shotaro Sessions
 *
 */
public class ReadFile extends ReadWriteFile{
	
	public ReadFile(){
		super();
	}
	
	/**
	 * Method for reading class roster from a text file at link
	 * 
	 * 
	 * @param link	Location of text file to read from
	 * @return		Teammate[] roster from text file
	 * @throws InvalidFileException
	 */
	public Teammate[] readList(String link) throws InvalidFileException{
		
		File file = new File(link);
		int lines;
		Teammate[] tempArr;
		
		try {
			lines = formatCheck(file); 
			tempArr = fillArr(file, lines);
		} catch(FormatException e){
			throw new InvalidFileException("Invalid file. Format error: " + e.getMessage(), e);
		} catch(FileNotFoundException e) {
			throw new InvalidFileException("Invalid file. File not found at: " + link, e);
		} catch(NoNameException e) {
			throw new InvalidFileException("Invalid file. Name error: " + e.getMessage(), e);
		} 
		
		return tempArr;
	}
	
	/**
	 * Method to check if a file is in the correct format
	 * 
	 * @param file	file to check
	 * @return		lines in file
	 * @throws FormatException
	 * @throws FileNotFoundException
	 */
	private int formatCheck(File file) throws FormatException, FileNotFoundException{
		int lines = 0;
		int components= 0;
		Scanner read = new Scanner(file);
		
		while(read.hasNextLine()) {	
			lines++;
			String lineStr = read.nextLine();
			String[] data = lineStr.split(DELIMITER);
			if(lines == 1) components = data.length;
			else if(components != data.length) {
				read.close();
				throw new FormatException("Inconsistent number of components");
			}
		}
		
		if(lines == 0) {
			read.close();
			throw new FormatException("Empty File");
		}
		
		read.close();
		return lines;
	}

	/**
	 * Class to generate a student roster from the text file passed in
	 * 
	 * @param file	File to create the roster from
	 * @param lines	Number of lines in file
	 * 
	 * @return	Teammate[] generated from the file
	 * 
	 * @throws FileNotFoundException
	 * @throws FormatException
	 * @throws NoNameException
	 */
	private Teammate[] fillArr(File file, int lines) throws FileNotFoundException, FormatException, NoNameException{
		Teammate[] returnArr = new Teammate[lines]; 
		Scanner read = new Scanner(file);
		for(int i = 0; i < returnArr.length; i++) {	
			ArrayList<String> name = new ArrayList<String>();
			String lineStr = read.nextLine();
			String[] data = lineStr.split(DELIMITER);
			int[] intData;
			int j;
			
			try {
				
				//name loop
				for(j = 0; !isNumeric(data[j]); j++) name.add(data[j]);
				
				//rest of the data
				intData = new int[data.length - j];
				for(int k = j; k < data.length; k++) intData[k-j] = Integer.valueOf(data[k]);
				
				returnArr[i] = new Teammate(name, intData);
				
			} catch(ArrayIndexOutOfBoundsException e) {
				read.close();
				throw new FormatException("Name related FormatException: " + e);
			} catch(NumberFormatException e) {
				read.close();
				throw new FormatException("Number related FormatException: " + e);
			} 
			
			
		}
		read.close();
		return returnArr;
	}
}
