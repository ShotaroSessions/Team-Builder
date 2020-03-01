package com.ssessions.teambuilder.fileio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.ssessions.teambuilder.exceptions.InvalidFileException;

/**
 * Class for writing to a text file
 * 
 * @author Shotaro Sessions
 *
 */
public class WriteFile extends ReadWriteFile
{
	
	public WriteFile() 
	{
		super();
	}

	/**
	 * Method for writing a string to a text file
	 * 
	 * @param groupResult
	 * @param writeLocation
	 * @throws InvalidFileException
	 */
	public void writeStrToNewFile(String groupResult, String writeLocation) throws InvalidFileException 
	{
		try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter(getFileName(writeLocation)));
		    writer.write(groupResult);
		    writer.close();
		} catch (IOException e) {
			throw new InvalidFileException("Invalid file. File not found at: " + writeLocation, e);
		}
	}

	/**
	 * Method to generate a file name for writing to
	 * 
	 * @param writeLocation
	 * @return
	 * @throws InvalidFileException
	 */
	private String getFileName(String writeLocation) throws InvalidFileException
	{
		
		String name = null;
		File directory = new File(writeLocation);
		File[] directoryFiles;
		ArrayList<String> fileNames = new ArrayList<String>();
		int n = 0;
		
		/*
		 * Checks to see if file is a directory, if so,
		 * 	inserts the files into a file array
		 */
		if(directory.isDirectory()) 
			directoryFiles = directory.listFiles(); 
		
		//Throws an error if not a directory
		else throw new InvalidFileException("Invalid File: Expected Directory");
		
		//Inserts all file names into an array of strings
		for(int i = 0; i < directoryFiles.length; i++) 
			fileNames.add(directoryFiles[i].getName());
		
		/*
		 * Increases n until n matches the largest n in
		 * 		"result_list_n" filename where n is an integer						
		 */
		while(fileNames.contains("result_list_" + Integer.toString(n + 1)))
			n++;
		
		//constructs the file name string
		name = writeLocation + "/result_list_" + ++n;
				
		return name;
	}

	
}
