package com.ssessions.teambuilder.systems;

import com.ssessions.teambuilder.exceptions.InvalidFileException;
import com.ssessions.teambuilder.systems.TryDisperse;
import com.ssessions.teambuilder.systems.TeamSort;
import com.ssessions.teambuilder.fileio.ReadFile;
import com.ssessions.teambuilder.systems.Teammate;
import com.ssessions.teambuilder.fileio.WriteFile;

/**
 * 
 * The interface from which the GUI interacts with all of the other
 * classes in the application.
 * 
 * @author Shotaro Sessions
 *
 */
public class SystemInterface {

	//int array holding the highest value possible for a data element
	private static int[] dataMax;
	
	//array holding the student roster
	private Teammate[] roster; 
	
	//file reader and writer
	private ReadFile reader; 
	private WriteFile writer;
	
	/**
	 * Interface constructor. created when app is run and window opens
	 * 
	 * @param readLink
	 * @throws InvalidFileException
	 */
	public SystemInterface(String readLink) throws InvalidFileException {
		reader = new ReadFile();
		writer = new WriteFile();
		
		roster = reader.readList(readLink);
		dataMax = findMax();
	}

	private int[] findMax() {
		
		int[] max = new int[roster[0].data().length];
		
		for(int n = 0; n < roster[0].data().length; n++) 
			for(Teammate x : roster) 
				if(x.data(n) > max[n]) max[n] = x.data(n);
				
		return max;
	}

	/**
	 * Method to write strings holding student groupings to a text file
	 * 
	 * @param groupResult	string representing a grouping of students
	 * @param writeLocation
	 * @throws InvalidFileException
	 */
	public void writeString(String groupResult, String writeLocation) throws InvalidFileException{
		writer.writeStrToNewFile(groupResult, writeLocation);
	}

	/**
	 * Method for grouping students without sorting
	 * 
	 * @param groups
	 * @return
	 */
	public String groupRoster(int groups) {
		String returnStr = "";
		Teammate[][] tempArr = null;
		TeamSort sort = new TeamSort();
		tempArr = sort.groupMaker(groups, roster);
		
		return generateStr(tempArr);
	}

	/**
	 * Method for calling sorting method without priority elements
	 * 
	 * @param groups
	 * @return	String with student groupings to write to file
	 */
	public String sortRoster(int groups) {
		
		String returnStr = "";
		Teammate[][] tempArr = null;
		
		TryDisperse trySort = new TryDisperse(dataMax);
		tempArr = trySort.sort(groups,roster);
				
		return generateStr(tempArr);
	}
	
	/**
	 * Method for calling sorting method with one or more priority elements
	 * 
	 * @param groups
	 * @param elements	int[] with priority elements from highest priority to lowest
	 * @return	String with student groupings to write to file
	 */
	public String sortRoster(int groups, int[] elements) {
		
		String returnStr = "";
		Teammate[][] tempArr = null;
		
		PrioritySort pSort = new PrioritySort(dataMax);
		
		tempArr = pSort.sort(groups, roster, elements);
		
		return generateStr(tempArr);
	}
	
	private String generateStr(Teammate[][] arr) {
		String str = "";
		
		for(int i = 0; i < arr.length; i++) {
			str = str + "Group " + (i + 1);
			for(Teammate t : arr[i]) {
				str = str + "\n" + t.display();
			}
			str += "\n\n";
		}
		
		return str;
	}
	
	public int studentNum() {
		return roster.length;
	}
	
	public int elemNum() {
		return dataMax.length;
	}
}
