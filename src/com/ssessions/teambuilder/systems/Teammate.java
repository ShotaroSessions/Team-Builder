package com.ssessions.teambuilder.systems;

import java.util.ArrayList;

import com.ssessions.teambuilder.exceptions.NoNameException;

/**
 * Class representing each student.
 * 
 * @author Shotaro Sessions
 *
 */
public class Teammate {
	
	//String array for names
	private final String[] name;
	
	//Array for student data
	private int[] data;
	
	public Teammate(ArrayList<String> names, int[] data) throws NoNameException{
		super();
		
		if(names.isEmpty()) throw new NoNameException("No names within arraylist");
		
		String[] tempArr = new String[names.size()];
		
		for(int i = 0; i < names.size(); i++) {
			if(names.get(i).isBlank()) break;
			tempArr[i] = names.get(i);
		}
		
		this.name = tempArr;
		this.data = data;
	}

	public String[] name() {
		String[] returnStr = new String[name.length];
		System.arraycopy(name, 0, returnStr, 0, name.length);
		return returnStr;
	}

	public int[] data(){
		return data;
	}

	public int data(int index){
		return data[index];
	}
	
	public String display() {
		String returnStr = "";
		for(String str : name) {
			returnStr = returnStr + str + " ";
		}
		for(int n : data) {
			returnStr = returnStr + n + " ";
		}
		return returnStr;
	}
}

