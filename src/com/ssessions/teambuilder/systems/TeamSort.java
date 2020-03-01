package com.ssessions.teambuilder.systems;

import com.ssessions.teambuilder.systems.Teammate;

/**
 * Super class for sorting engines
 * 
 * @author Shotaro Sessions
 *
 */
public class TeamSort {
	
	/**
	 * Class for making groups out of a student roster
	 * 
	 * @param groups	Number of groups
	 * @param roster	Roster to group
	 * 
	 * @return	Completed grouping as a Teammate[][]
	 */
	public Teammate[][] groupMaker(int groups, Teammate[] roster) {
		
		Teammate[][] groupsArrays = new Teammate[groups][];
		int remainder = 0;
		int groupSizeA = 0;
		int groupSizeB = 0;
		int groupNumA = 0;
		int groupNumB = 0;
		int rosterIndex = 0;
		
		
		remainder = roster.length % groups;
		groupSizeA = (roster.length - remainder) / groups;
		
		if(remainder == 0) groupNumA = groups;
		else if(remainder == groupSizeA - 1) {
			groupNumA = groups - 1; 
			groupSizeB = groupSizeA - 1;
		}
		else {
			groupNumA = groups - remainder;
			groupSizeB = groupSizeA +1;
		}
		groupNumB = groups - groupNumA;
		
		//Make sure A is the bigger group (Needed for sorting methods to work)
		if(groupSizeA < groupSizeB) {
			int temp = groupSizeA;
			groupSizeA = groupSizeB;
			groupSizeB = temp;
			
			temp = groupNumA;
			groupNumA = groupNumB;
			groupNumB = temp;
		}
		
		//Loop for loading groups of size A
		for(int i = 0; i < groupNumA; i++) {
			Teammate[] tempArr = new Teammate[groupSizeA];
			for(int j = 0; j < groupSizeA; j++) tempArr[j] = roster[rosterIndex++];
			groupsArrays[i] = tempArr;
			
		}
		
		//Loop for loading groups of size B
		for(int i = groupNumA; i < groupNumA + groupNumB; i++) {
			Teammate[] tempArr = new Teammate[groupSizeB];
			for(int j = 0; j < groupSizeB; j++) tempArr[j] = roster[rosterIndex++];
			groupsArrays[i] = tempArr;
		}
		
		return groupsArrays;
	}
	
}
